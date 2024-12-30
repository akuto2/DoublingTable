package com.akuto2.doublingtable.menu;

import java.util.Optional;

import com.akuto2.akutolib.registration.object.BlockRegistryObject;
import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.doublingtable.blocks.BlockDoublingTable;
import com.akuto2.doublingtable.registers.DTBlocks;
import com.akuto2.doublingtable.registers.DTMenus;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class MenuDoublingTable extends RecipeBookMenu<CraftingContainer> {
	private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 3);
	private final ResultContainer resultSlot = new ResultContainer();
	private final ContainerLevelAccess access;
	private final Player player;
	public final EnumFacilityType type;

	protected MenuDoublingTable(MenuTypeRegistryObject<? extends AbstractContainerMenu> menu, int id, Inventory playerInventory, ContainerLevelAccess access, EnumFacilityType type) {
		super(menu.get(), id);
		this.access = access;
		this.player = playerInventory.player;
		this.type = type;
		
		addSlot(new ResultSlot(player, craftSlots, resultSlot, 0, 124, 35));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				addSlot(new Slot(craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	
	private void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer) {
		if (!level.isClientSide) {
			ServerPlayer serverPlayer = (ServerPlayer)player;
			ItemStack stack = ItemStack.EMPTY;
			Optional<CraftingRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingContainer, level);
			if (optional.isPresent()) {
				CraftingRecipe recipe = optional.get();
				if (resultContainer.setRecipeUsed(level, serverPlayer, recipe)) {
					ItemStack stack2 = recipe.assemble(craftingContainer, level.registryAccess());
					if (stack2.isItemEnabled(level.enabledFeatures())) {
						stack = stack2;
						stack.setCount(stack.getCount() * type.getTimes());
					}
				}
			}
			
			resultContainer.setItem(0, stack);
			menu.setRemoteSlot(0, stack);
			serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, stack));
		}
	}
	
	@Override
	public void slotsChanged(Container container) {
		access.execute((level, pos) ->{
			slotChangedCraftingGrid(this, level, player, craftSlots, resultSlot);
		});
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
		craftSlots.fillStackedContents(itemHelper);
	}

	@Override
	public void clearCraftingContent() {
		craftSlots.clearContent();
		resultSlot.clearContent();
	}

	@Override
	public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
		return recipe.matches(craftSlots, player.level());
	}
	
	@Override
	public void removed(Player player) {
		super.removed(player);
		access.execute((level, pos) -> {
			clearContainer(player, craftSlots);
		});
	}

	@Override
	public int getResultSlotIndex() {
		return 0;
	}

	@Override
	public int getGridWidth() {
		return craftSlots.getWidth();
	}

	@Override
	public int getGridHeight() {
		return craftSlots.getHeight();
	}

	@Override
	public int getSize() {
		return 10;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return RecipeBookType.CRAFTING;
	}

	@Override
	public boolean shouldMoveToInventory(int index) {
		return index != getResultSlotIndex();
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if(slot != null && slot.hasItem()) {
			ItemStack stack2 = slot.getItem();
			stack = stack2.copy();
			if (index == 0) {
				access.execute((level, pos) -> {
					stack2.getItem().onCraftedBy(stack2, level, player);
				});
				if(!moveItemStackTo(stack2, 10, 46, false)) {
					return ItemStack.EMPTY;
				}
				
				slot.onQuickCraft(stack2, stack);
			} else if (index >= 10 && index < 46) {
				if (!moveItemStackTo(stack2, 1, 10, false)) {
					if(index < 37) {
						if (!moveItemStackTo(stack2, 37, 46, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!moveItemStackTo(stack2, 10, 37, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!moveItemStackTo(stack2, 10, 46, false)) {
				return ItemStack.EMPTY;
			}
			
			if (stack2.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if(stack2.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, stack2);
			if (index == 0) {
				player.drop(stack2, false);
			}
		}
		
		return stack;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(access, player, getValidBlock().getBlock());
	}
	
	public abstract BlockRegistryObject<BlockDoublingTable, ?> getValidBlock();

	// 以下それぞれの種類ごとのMenuType
	public static class Wood extends MenuDoublingTable {
		public Wood(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_WOOD, id, playerInventory, access, EnumFacilityType.WOOD);
		}
		
		public Wood(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_WOOD;
		}
	}
	
	public static class Stone extends MenuDoublingTable {
		public Stone(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_STONE, id, playerInventory, access, EnumFacilityType.STONE);
		}
		
		public Stone(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_STONE;
		}
	}
	
	public static class Iron extends MenuDoublingTable {
		public Iron(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_IRON, id, playerInventory, access, EnumFacilityType.IRON);
		}
		
		public Iron(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_IRON;
		}
	}
	
	public static class Gold extends MenuDoublingTable {
		public Gold(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_GOLD, id, playerInventory, access, EnumFacilityType.GOLD);
		}
		
		public Gold(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_GOLD;
		}
	}
	
	public static class Diamond extends MenuDoublingTable {
		public Diamond(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_DIAMOND, id, playerInventory, access, EnumFacilityType.DIAMOND);
		}
		
		public Diamond(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_DIAMOND;
		}
	}
	
	public static class Emerald extends MenuDoublingTable {
		public Emerald(int id, Inventory playerInventory, ContainerLevelAccess access) {
			super(DTMenus.DOUBLING_TABLE_EMERALD, id, playerInventory, access, EnumFacilityType.EMERALD);
		}
		
		public Emerald(int id, Inventory playerInventory, FriendlyByteBuf buf) {
			this(id, playerInventory, ContainerLevelAccess.NULL);
		}

		@Override
		public BlockRegistryObject<BlockDoublingTable, ?> getValidBlock() {
			return DTBlocks.DOUBLING_TABLE_EMERALD;
		}
	}
}
