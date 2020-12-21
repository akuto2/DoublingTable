package akuto2.doublingtable.tiles;

import akuto2.doublingtable.blocks.BlockDoublingFurnace;
import akuto2.doublingtable.blocks.BlockDoublingTable;
import akuto2.doublingtable.utils.Utils.EnumFacilityTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDoublingFurnace extends TileEntity implements ISidedInventory, ITickable{
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] { 1 };
	public int outputSlot = 1;
	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	public int totalCookTime;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList tagList = compound.getTagList("Items", 10);
		furnaceItemStacks = new ItemStack[getSizeInventory()];

		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound.getByte("Slot");

			if(byte0 >= 0 && byte0 < furnaceItemStacks.length) {
				furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}

		furnaceBurnTime = compound.getShort("BurnTime");
		furnaceCookTime = compound.getShort("CookTime");
		totalCookTime = compound.getShort("TotalCookTime");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("BurnTime", (short)furnaceBurnTime);
		compound.setShort("CookTime", (short)furnaceCookTime);
		compound.setShort("TotalCookTime", (short)totalCookTime);
		NBTTagList tagList = new NBTTagList();

		for(int i = 0; i < furnaceItemStacks.length; ++i) {
			if(furnaceItemStacks[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte)i);
				furnaceItemStacks[i].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}

		compound.setTag("Items", tagList);
		return compound;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return furnaceCookTime != 0 && totalCookTime != 0 ? furnaceCookTime * par1 / totalCookTime : 0;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainScaled(int par1) {
		if(currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}

		return furnaceBurnTime * par1 / currentItemBurnTime;
	}

	public boolean isBurning() {
		return furnaceBurnTime > 0;
	}

	public int getBurnTime() {
		EnumFacilityTypes types = worldObj.getBlockState(pos).getValue(BlockDoublingFurnace.TYPE);
		if(types == EnumFacilityTypes.wood) {
			return 200;
		}
		if(types == EnumFacilityTypes.stone) {
			return 150;
		}
		if(types == EnumFacilityTypes.iron) {
			return 100;
		}
		if(types == EnumFacilityTypes.gold) {
			return 50;
		}
		if(types == EnumFacilityTypes.diamond) {
			return 8;
		}
		if(types == EnumFacilityTypes.emerald) {
			return 4;
		}
		if(types == EnumFacilityTypes.lapis) {
			return 50;
		}
		if(types == EnumFacilityTypes.redstone) {
			return 10;
		}
		return 200;
	}

	private boolean canSmelt() {
		if(furnaceItemStacks[0] == null) {
			return false;
		}
		else {
			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]);
			if(stack == null) return false;
			if(furnaceItemStacks[2] == null) return true;
			if(!furnaceItemStacks[2].isItemEqual(stack)) return false;
			int result = furnaceItemStacks[2].stackSize + stack.stackSize;
			return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
		}
	}

	public void smeltItem() {
		ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]).copy();
		stack.stackSize *= ((EnumFacilityTypes)worldObj.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes();
		if(furnaceItemStacks[2] == null) {
			furnaceItemStacks[2] = stack;
		}
		else if(furnaceItemStacks[2].getItem() == stack.getItem()) {
			furnaceItemStacks[2].stackSize += stack.stackSize;
		}

		furnaceItemStacks[0].stackSize -= 1;
		if(furnaceItemStacks[0].stackSize <= 0) {
			furnaceItemStacks[0] = null;
		}
	}

	private static int getItemBurnTime(ItemStack stack) {
		int fuel = TileEntityFurnace.getItemBurnTime(stack);
		return fuel;
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	@Override
	public void update() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;

		if(isBurning()) {
			--furnaceBurnTime;
		}

		if(!worldObj.isRemote) {
			if(isBurning() || furnaceItemStacks[1] != null && furnaceItemStacks[0] != null) {
				if(!isBurning() && canSmelt()) {
					furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);
					currentItemBurnTime = furnaceBurnTime;

					if(furnaceBurnTime > 0) {
						flag1 = true;
						if(furnaceItemStacks[1] != null) {
							--furnaceItemStacks[1].stackSize;

							if(furnaceItemStacks[1].stackSize == 0) {
								furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
							}
						}
					}
				}

				if(isBurning() && canSmelt()) {
					++furnaceCookTime;
					if(furnaceCookTime == totalCookTime) {
						furnaceCookTime = 0;
						totalCookTime = getBurnTime();
						smeltItem();
						flag1 = true;
					}
				}
				else {
					furnaceCookTime = 0;
				}
			}
			else if(!isBurning() && furnaceCookTime > 0) {
				furnaceCookTime = MathHelper.clamp_int(furnaceCookTime - 2, 0, totalCookTime);
			}

			if(flag != isBurning()) {
				flag1 = true;
				BlockDoublingFurnace.updateBlockState(furnaceBurnTime > 0, worldObj, pos);
			}
		}
		if(flag1) {
			markDirty();
		}
	}

	@Override
	public int getSizeInventory() {
		return furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return furnaceItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if(furnaceItemStacks[index] != null) {
			ItemStack stack;
			if(furnaceItemStacks[index].stackSize <= count) {
				stack = furnaceItemStacks[index];
				furnaceItemStacks[index] = null;
				return stack;
			}
			else {
				stack = furnaceItemStacks[index].splitStack(count);

				if(furnaceItemStacks[index].stackSize == 0) {
					furnaceItemStacks[index] = null;
				}
				return stack;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, furnaceItemStacks[index]);
		furnaceItemStacks[index] = stack;

		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		if(index == 0 && !flag) {
			totalCookTime = getBurnTime();
			furnaceCookTime = 0;
			markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 2 ? false : (index == 1 ? isItemFuel(stack) : true);
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0:
			return furnaceBurnTime;
		case 1:
			return currentItemBurnTime;
		case 2:
			return furnaceCookTime;
		case 3:
			return totalCookTime;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			furnaceBurnTime = value;
			break;
		case 1:
			currentItemBurnTime = value;
			break;
		case 2:
			furnaceCookTime = value;
			break;
		case 3:
			totalCookTime = value;
		}

	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            this.furnaceItemStacks[i] = null;
        }
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return direction != EnumFacing.DOWN || index != 1 || stack.getItem() == Items.BUCKET;
	}
}
