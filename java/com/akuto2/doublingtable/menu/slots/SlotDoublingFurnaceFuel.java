package com.akuto2.doublingtable.menu.slots;

import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.menu.MenuDoublingFurnace;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SlotDoublingFurnaceFuel extends Slot {
	private final MenuDoublingFurnace menu;
	
	public SlotDoublingFurnaceFuel(MenuDoublingFurnace menu, BlockEntityDoublingFurnace furnace, int slotId, int xPos, int yPos) {
		super(furnace, slotId, xPos, yPos);
		this.menu = menu;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return menu.isFuel(stack) || isBucket(stack);
	}
	
	@Override
	public int getMaxStackSize(ItemStack stack) {
		return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
	}
	
	public static boolean isBucket(ItemStack stack) {
		return stack.is(Items.BUCKET);
	}
}
