package akuto2.gui;

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

public class ContainerDoublingTable extends Container{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private BlockPos pos;
	private int magnification;

	public ContainerDoublingTable(InventoryPlayer inventoryPlayer, World world, BlockPos pos, int type) {
		this.worldObj = world;
		this.pos = pos;
		magnification = type;
		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, craftMatrix, craftResult, 0, 124, 35));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		ItemStack stack = CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);
		if(stack != null) {
			stack.stackSize *= magnification;
		}
		craftResult.setInventorySlotContents(0, stack);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		if(!worldObj.isRemote) {
			for(int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
				ItemStack stack = craftMatrix.getStackInSlot(i);

				if(stack != null) {
					playerIn.dropItem(stack, false);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) <= 64.0D;
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
			else if(index >= 10 && index < 37) {
				if(!mergeItemStack(slotStack, 37, 46, false))
					return null;
			}
			else if(index >= 37 && index < 46) {
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
