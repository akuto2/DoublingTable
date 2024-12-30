package com.akuto2.doublingtable.blockentities;

import java.util.Optional;

import com.akuto2.akutolib.registration.register.BlockEntityRegistryObject;
import com.akuto2.doublingtable.menu.MenuDoublingFurnace;
import com.akuto2.doublingtable.registers.DTBlockEntities;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class BlockEntityDoublingFurnace extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
	private static final int[] SLOTS_UP = new int[] { 0 };
	private static final int[] SLOTS_DOWN = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDE = new int[] { 1 };
	
	private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
	private int litTime;
	private int litDuration;
	private int cookingProgrees;
	private int cookingTotalTime;
	public final ContainerData dataAccess = new ContainerData() {
		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				BlockEntityDoublingFurnace.this.litTime = value;
				break;
			case 1:
				BlockEntityDoublingFurnace.this.litDuration = value;
				break;
			case 2:
				BlockEntityDoublingFurnace.this.cookingProgrees = value;
				break;
			case 3:
				BlockEntityDoublingFurnace.this.cookingTotalTime = value;
			}
		}
		
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return BlockEntityDoublingFurnace.this.litTime;
			case 1:
				return BlockEntityDoublingFurnace.this.litDuration;
			case 2:
				return BlockEntityDoublingFurnace.this.cookingProgrees;
			case 3:
				return BlockEntityDoublingFurnace.this.cookingTotalTime;
			default:
				return 0;
			}
		}
		
		@Override
		public int getCount() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipeUsed = new Object2IntOpenHashMap<>();
	private final RecipeWrapper dummy = new RecipeWrapper(new ItemStackHandler());
	private final EnumFacilityType type;
	
	protected BlockEntityDoublingFurnace(BlockEntityRegistryObject<? extends BlockEntityDoublingFurnace> furnace, BlockPos pos, BlockState blockState, EnumFacilityType type) {
		super(furnace.get(), pos, blockState);
		this.type = type;
	}
	
	/**
	 * 燃焼中か
	 */
	private boolean isLit() {
		return litTime > 0;
	}
	
	/**
	 * タグ読み込み
	 */
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, items);
		litTime = tag.getInt("BurnTime");
		cookingProgrees = tag.getInt("CookTime");
		cookingTotalTime = tag.getInt("CookTimeTotal");
		litDuration = getBurnDuration(items.get(1));
		
		CompoundTag compound = tag.getCompound("RecipeUsed");
		for (String s : compound.getAllKeys()) {
			recipeUsed.put(new ResourceLocation(s), compound.getInt(s));
		}
	}
	
	/**
	 * タグ保存
	 */
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("BurnTime", litTime);
		tag.putInt("CookTime", cookingProgrees);
		tag.putInt("CookTimeTotal", cookingTotalTime);
		ContainerHelper.saveAllItems(tag, items);
		CompoundTag compound = new CompoundTag();
		recipeUsed.forEach((k, v) -> {
			compound.putInt(k.toString(), v);
		});
		tag.put("RecipeUsed", compound);
	}
	
	/**
	 * サーバー側で毎フレーム呼ばれる関数
	 */
	public static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntityDoublingFurnace blockEntity) {
		boolean isLit = blockEntity.isLit();
		boolean flag = false;
		// 既に燃料が燃えている場合は燃焼時間減少
		if (isLit) {
			--blockEntity.litTime;
		}
		
		ItemStack fuel = blockEntity.items.get(1);
		boolean hasInput = !blockEntity.items.get(0).isEmpty();
		boolean hasFuel = !fuel.isEmpty();
		
		if (blockEntity.isLit() || hasFuel && hasInput) {
			if (!blockEntity.isLit() && blockEntity.canSmelt()) {
				blockEntity.litTime = blockEntity.getBurnDuration(fuel);
				blockEntity.litDuration = blockEntity.litTime;
				
				if (blockEntity.isLit()) {
					flag = true;
					
					if (hasFuel) {
						ItemStack copy = fuel.copy();
						fuel.shrink(1);
						if (fuel.isEmpty()) {
							blockEntity.items.set(1, copy.getCraftingRemainingItem());
						}
					}
				}
			}
			
			if (blockEntity.isLit() && blockEntity.canSmelt()) {
				++blockEntity.cookingProgrees;
				if (blockEntity.cookingProgrees == blockEntity.cookingTotalTime) {
					blockEntity.cookingProgrees = 0;
					blockEntity.cookingTotalTime = blockEntity.getSmeltingTime();
					blockEntity.smeltItem();
					flag = true;
				}
			} else {
				blockEntity.cookingProgrees = 0;
			}
		} else if (!blockEntity.isLit() && blockEntity.cookingProgrees > 0) {
			blockEntity.cookingProgrees = Mth.clamp(blockEntity.cookingProgrees - 2, 0, blockEntity.cookingTotalTime);
		}
		
		// 今回かまどの状態が変わったかどうか
		if (isLit != blockEntity.isLit()) {
			flag = true;
			level.setBlockAndUpdate(pos, state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit()));
		}
		
		if (flag) {
			blockEntity.setChanged();
		}
	}
	
	/**
	 * 現在のインプットスロットに入っているアイテムを焼けるかチェック
	 */
	private boolean canSmelt() {
		ItemStack inputStack = items.get(0);
		if (inputStack.isEmpty()) {
			return false;
		}
		
		ItemStack resultStack = getSmeltingResult(inputStack);
		if (resultStack.isEmpty()) {
			return false;
		}
		
		ItemStack outputStack = items.get(2);
		if (outputStack.isEmpty())
			return true;
		
		if (!ItemHandlerHelper.canItemStacksStack(resultStack, outputStack)) {
			return false;
		}
		
		int resultSize = resultStack.getCount() + outputStack.getCount();
		return resultSize <= outputStack.getMaxStackSize();
	}
	
	/**
	 * 指定のアイテムを精錬後のアイテムを取得する
	 */
	private ItemStack getSmeltingResult(ItemStack stack) {
		dummy.setItem(0, stack);
		Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, dummy, level);
		dummy.setItem(0, ItemStack.EMPTY);
		return recipe.map(r -> r.getResultItem(level.registryAccess())).orElse(ItemStack.EMPTY);
	}
	
	/**
	 * 燃料の燃焼時間取得
	 */
	private int getBurnDuration(ItemStack fuel) {
		if (fuel.isEmpty()) {
			return 0;
		} else {
			return ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
		}
	}
	
	/**
	 * 焼くのに掛かる時間を計算
	 */
	private int getSmeltingTime() {
		switch (type) {
		case WOOD:
			return 200;
		case STONE:
			return 150;
		case IRON:
			return 100;
		case GOLD:
			return 50;
		case DIAMOND:
			return 8;
		case EMERALD:
			return 4;
		default:
			return 200;
		}
	}
	
	public void smeltItem() {
		ItemStack resultStack = getSmeltingResult(items.get(0)).copy();
		resultStack.setCount(resultStack.getCount() * type.getTimes());
		ItemStack outputStack = items.get(2);
		
		if (outputStack.isEmpty()) {
			items.set(2, resultStack.copy());
		} else if (outputStack.is(resultStack.getItem())) {
			outputStack.grow(resultStack.getCount());
		}
		
		if (items.get(0).is(Blocks.WET_SPONGE.asItem()) && !items.get(1).isEmpty() && items.get(1).is(Items.BUCKET)) {
			items.set(1, new ItemStack(Items.WATER_BUCKET));
		}
		
		items.get(0).shrink(1);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.furnace");
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : items) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		return ContainerHelper.removeItem(items, slot, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		ItemStack slotStack = items.get(slot);
		boolean sameItem = !stack.isEmpty() && ItemStack.isSameItemSameTags(slotStack, stack);
		items.set(slot, slotStack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(stack.getMaxStackSize());
		}
		
		// インプットスロットのアイテムが変わった場合は燃焼時間などをリセット
		if (slot == 0 && !sameItem) {
			cookingTotalTime = getSmeltingTime();
			cookingProgrees = 0;
			setChanged();
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	@Override
	public void fillStackedContents(StackedContents contents) {
		for (ItemStack stack : items) {
			contents.accountStack(stack);
		}
	}

	@Override
	public void setRecipeUsed(Recipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourceLocation = recipe.getId();
			recipeUsed.addTo(resourceLocation, 1);
		}
	}

	@Override
	public Recipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		switch (side) {
		case UP:
			return SLOTS_UP;
		case DOWN:
			return SLOTS_DOWN;
		default:
			return SLOTS_SIDE;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
		return canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && slot == 1) {
			return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
		} else {
			return true;
		}
	}
	
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (slot == 2) { 
			return false;
		} else if (slot != 1) {
			return true;
		} else {
			ItemStack fuel = items.get(1);
			return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0 || stack.is(Items.BUCKET) && !fuel.is(Items.BUCKET);
		}
	}
	
	public EnumFacilityType getFacilityType() {
		return type;
	}

	public static class Wood extends BlockEntityDoublingFurnace {
		public Wood(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_WOOD, pos, blockState, EnumFacilityType.WOOD);
		}

		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Wood(id, playerInventory, this);
		}
	}
	
	public static class Stone extends BlockEntityDoublingFurnace {
		public Stone(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_STONE, pos, blockState, EnumFacilityType.STONE);
		}
		
		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Stone(id, playerInventory, this);
		}
	}
	
	public static class Iron extends BlockEntityDoublingFurnace {
		public Iron(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_IRON, pos, blockState, EnumFacilityType.IRON);
		}
		
		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Iron(id, playerInventory, this);
		}
	}
	
	public static class Gold extends BlockEntityDoublingFurnace {
		public Gold(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_GOLD, pos, blockState, EnumFacilityType.GOLD);
		}
		
		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Gold(id, playerInventory, this);
		}
	}
	
	public static class Diamond extends BlockEntityDoublingFurnace {
		public Diamond(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_DIAMOND, pos, blockState, EnumFacilityType.DIAMOND);
		}
		
		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Diamond(id, playerInventory, this);
		}
	}
	
	public static class Emerald extends BlockEntityDoublingFurnace {
		public Emerald(BlockPos pos, BlockState blockState) {
			super(DTBlockEntities.DOUBLING_FURNACE_EMERALD, pos, blockState, EnumFacilityType.EMERALD);
		}
		
		@Override
		protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
			return new MenuDoublingFurnace.Emerald(id, playerInventory, this);
		}
	}
}
