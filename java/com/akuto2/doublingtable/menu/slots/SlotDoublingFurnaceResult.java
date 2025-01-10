package com.akuto2.doublingtable.menu.slots;

import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;

public class SlotDoublingFurnaceResult extends Slot {
	private final Player player;
	private int removeCount;

	public SlotDoublingFurnaceResult(Player player, Container pContainer, int slotId, int xPosition, int yPosition) {
		super(pContainer, slotId, xPosition, yPosition);
		this.player = player;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack remove(int amount) {
		if (hasItem()) {
			removeCount += Math.min(amount, getItem().getCount());
		}
		
		return super.remove(amount);
	}
	
	@Override
	public void onTake(Player player, ItemStack stack) {
		checkTake(stack);
		super.onTake(player, stack);
	}
	
	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		removeCount += amount;
		checkTake(stack);
	}
	
	private void checkTake(ItemStack stack) {
		stack.onCraftedBy(player.level(), player, removeCount);
		if (player instanceof ServerPlayer) {
			if (container instanceof BlockEntityDoublingFurnace) {
				((BlockEntityDoublingFurnace)container).onTakeResult((ServerPlayer)player);
			}
		}
		
		removeCount = 0;
		ForgeEventFactory.firePlayerSmeltedEvent(player, stack);
	}
}
