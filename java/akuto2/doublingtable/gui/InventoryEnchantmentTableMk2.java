package akuto2.doublingtable.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryEnchantmentTableMk2 implements IInventory{
	private ContainerEnchantmentTableMk2 container;
	private ItemStack inventorySlotContents;
	private int[] enchantLevels;
	private int tier;

	public InventoryEnchantmentTableMk2(ContainerEnchantmentTableMk2 container) {
		this.container = container;
		enchantLevels = new int[3];
		tier = 1;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? inventorySlotContents : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if(inventorySlotContents != null) {
			ItemStack stack;

			if(inventorySlotContents.stackSize <= count) {
				stack = inventorySlotContents;
				inventorySlotContents = null;
				markDirty();
				return stack;
			}
			else {
				stack = inventorySlotContents.splitStack(count);

				if(inventorySlotContents.stackSize == 0) {
					inventorySlotContents = null;
				}

				markDirty();
				return stack;
			}
		}

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if(inventorySlotContents != null) {
			ItemStack stack = inventorySlotContents;
			inventorySlotContents = null;
			return stack;
		}
		else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventorySlotContents = stack;

		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		container.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getTier() {
		return tier;
	}

	public void setEnchantLevels(int index, int level) {
		enchantLevels[index] = level;
	}

	public int getEnchantLevels(int index) {
		return enchantLevels[index];
	}
}
