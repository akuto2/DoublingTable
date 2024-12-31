package com.akuto2.doublingtable.menu;

import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.menu.slots.SlotDoublingFurnaceFuel;
import com.akuto2.doublingtable.registers.DTMenus;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

public class MenuDoublingFurnace extends RecipeBookMenu<Container>{
	public final BlockEntityDoublingFurnace furnace;
	private final Level level;

	protected MenuDoublingFurnace(MenuTypeRegistryObject<? extends MenuDoublingFurnace> menu, int containerId, Inventory playerInventory, BlockEntityDoublingFurnace furnace) {
		super(menu.get(), containerId);
		this.level = playerInventory.player.level();
		this.furnace = furnace;
		
		addSlot(new Slot(furnace, 0, 56, 17));
		addSlot(new SlotDoublingFurnaceFuel(this, furnace, 1, 56, 53));
		addSlot(new FurnaceResultSlot(playerInventory.player, furnace, 2, 116, 35));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
		
		addDataSlots(furnace.dataAccess);
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents helper) {
		if (furnace instanceof StackedContentsCompatible) {
			((StackedContentsCompatible)furnace).fillStackedContents(helper);
		}
	}

	@Override
	public void clearCraftingContent() {
		getSlot(0).set(ItemStack.EMPTY);
		getSlot(2).set(ItemStack.EMPTY);
	}

	@Override
	public boolean recipeMatches(Recipe<? super Container> recipe) {
		return recipe.matches(furnace, level);
	}

	@Override
	public int getResultSlotIndex() {
		return 2;
	}

	@Override
	public int getGridWidth() {
		return 1;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return RecipeBookType.FURNACE;
	}

	@Override
	public boolean shouldMoveToInventory(int id) {
		return id != 1;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int id) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(id);
		if (slot != null && slot.hasItem()) {
			ItemStack stack2 = slot.getItem();
			stack = stack2.copy();
			if (id == 2) {
				if (!moveItemStackTo(stack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				
				slot.onQuickCraft(stack2, stack);
			} else if (id != 1 && id != 0) {
				if (canSmelt(stack2)) {
					if (!moveItemStackTo(stack2, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (isFuel(stack2)) {
					if (!moveItemStackTo(stack2, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (id >= 3 && id < 30) {
					if (!moveItemStackTo(stack2, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (id >= 30 && id < 39 && !moveItemStackTo(stack2, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(stack2, 3, 39, false)) {
				return ItemStack.EMPTY;
			}
			
			if (stack2.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if (stack2.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(player, stack2);
		}
		return stack;
	}

	@Override
	public boolean stillValid(Player player) {
		return furnace.stillValid(player);
	}

	private boolean canSmelt(ItemStack stack) {
		return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), level).isPresent();
	}
	
	public boolean isFuel(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
	}
	
	// 以下それぞれの種類ごとのMenuType
	public static class Wood extends MenuDoublingFurnace {
		public Wood(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Wood furnace) {
			super(DTMenus.DOUBLING_FURNACE_WOOD, id, playerInventory, furnace);
		}
	}
	
	public static class Stone extends MenuDoublingFurnace {
		public Stone(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Stone furnace) {
			super(DTMenus.DOUBLING_FURNACE_STONE, id, playerInventory, furnace);
		}
	}
	
	public static class Iron extends MenuDoublingFurnace {
		public Iron(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Iron furnace) {
			super(DTMenus.DOUBLING_FURNACE_IRON, id, playerInventory, furnace);
		}
	}
	
	public static class Gold extends MenuDoublingFurnace {
		public Gold(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Gold furnace) {
			super(DTMenus.DOUBLING_FURNACE_GOLD, id, playerInventory, furnace);
		}
	}
	
	public static class Diamond extends MenuDoublingFurnace {
		public Diamond(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Diamond furnace) {
			super(DTMenus.DOUBLING_FURNACE_DIAMOND, id, playerInventory, furnace);
		}
	}
	
	public static class Emerald extends MenuDoublingFurnace {
		public Emerald(int id, Inventory playerInventory, BlockEntityDoublingFurnace.Emerald furnace) {
			super(DTMenus.DOUBLING_FURNACE_EMERALD, id, playerInventory, furnace);
		}
	}
}
