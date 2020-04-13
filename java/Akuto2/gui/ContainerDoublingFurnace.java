package Akuto2.Gui;

import Akuto2.Tiles.TileEntityDoublingFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerDoublingFurnace extends Container{
	private TileEntityDoublingFurnace tileFurnace;
	private int cookTime;
	private int totalCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	private int magnification;

	public ContainerDoublingFurnace(InventoryPlayer player, TileEntityDoublingFurnace tile, int type) {
		tileFurnace = tile;
		magnification = type;
		addSlotToContainer(new Slot(tileFurnace, 0, 56, 17));
		addSlotToContainer(new SlotFurnaceFuel(tileFurnace, 1, 56, 53));
		addSlotToContainer(new SlotFurnaceOutput(player.player, tileFurnace, 2, 116, 35));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileFurnace);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileFurnace.isUsableByPlayer(playerIn);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener)listeners.get(i);

			if(cookTime != tileFurnace.getField(2)) {
				listener.sendWindowProperty(this, 2, tileFurnace.getField(2));
			}

			if(lastBurnTime != tileFurnace.getField(0)) {
				listener.sendWindowProperty(this, 0, tileFurnace.getField(0));
			}

			if(lastItemBurnTime != tileFurnace.getField(1)) {
				listener.sendWindowProperty(this, 1, tileFurnace.getField(1));
			}

			if(totalCookTime != tileFurnace.getField(3)) {
				listener.sendWindowProperty(this, 3, tileFurnace.getField(3));
			}
		}

		cookTime = tileFurnace.getField(2);
		lastBurnTime = tileFurnace.getField(0);
		lastItemBurnTime = tileFurnace.getField(1);
		totalCookTime = tileFurnace.getField(3);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		tileFurnace.setField(id, data);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = null;
		Slot slot = (Slot)inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if(index == 2) {
				if(!mergeItemStack(slotStack, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(slotStack, stack);
			}
			else if(index != 1 && index != 0) {
				if(!FurnaceRecipes.instance().getSmeltingResult(slotStack).isEmpty()) {
					if(!mergeItemStack(slotStack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if(TileEntityDoublingFurnace.isItemFuel(slotStack)) {
					if(!mergeItemStack(slotStack, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 3 & index < 30) {
					if(!mergeItemStack(slotStack, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if(index >= 30 && index < 39 && !mergeItemStack(slotStack, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if(!mergeItemStack(slotStack, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if(slotStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}

			if(slotStack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, slotStack);
		}

		return stack;
	}
}
