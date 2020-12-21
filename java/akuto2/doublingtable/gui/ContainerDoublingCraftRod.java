package akuto2.doublingtable.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerDoublingCraftRod extends Container{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	private World world;
	private BlockPos pos;
	private int magnification;

	public ContainerDoublingCraftRod(InventoryPlayer inventoryPlayer, World world, BlockPos pos, int magnification) {
		this.world = world;
		this.pos = pos;
		this.magnification = magnification;

		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, craftMatrix, craftResult, 0, 124, 35));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + j * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		ItemStack stack = CraftingManager.getInstance().findMatchingRecipe(craftMatrix, world);
		if(stack != null) {
			stack.stackSize *= magnification;
		}
		craftResult.setInventorySlotContents(0, stack);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		if(!world.isRemote) {
			for(int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = craftMatrix.removeStackFromSlot(i);

				if(stack != null)
					playerIn.dropItem(stack, false);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = null;
		Slot slot = (Slot)inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if(index == 0) {
				if(!mergeItemStack(slotStack, 10, 46, true))
					return null;

				slot.onSlotChange(slotStack, stack);
			}
			else if(index >= 10 && index < 46) {
				if(!mergeItemStack(slotStack, 37, 46, false))
					return null;
			}
			else if(index >= 46 && index < 55) {
				if(!mergeItemStack(slotStack, 10, 37, false))
					return null;
			}
			else if(!mergeItemStack(slotStack, 10, 46, false))
				return null;

			if(slotStack.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if(slotStack.stackSize == stack.stackSize)
				return null;

			slot.onPickupFromSlot(playerIn, slotStack);
		}

		return stack;
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != craftResult && super.canMergeSlot(stack, slotIn);
	}
}
