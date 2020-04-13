package Akuto2.Tiles;

import Akuto2.Blocks.BlockDoublingFurnace;
import Akuto2.Utils.EnumUtils.EnumFacilityTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDoublingFurnace extends TileEntity implements ISidedInventory, ITickable{
	private static final int[] slotsTop = new int[] { 0 };
	private static final int[] slotsBottom = new int[] { 2, 1 };
	private static final int[] slotsSides = new int[] { 1 };
	public int outputSlot = 1;
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	public int totalCookTime;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		furnaceItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, furnaceItemStacks);

		furnaceBurnTime = compound.getShort("BurnTime");
		furnaceCookTime = compound.getShort("CookTime");
		totalCookTime = compound.getShort("TotalCookTime");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks.get(1));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setShort("BurnTime", (short)furnaceBurnTime);
		compound.setShort("CookTime", (short)furnaceCookTime);
		compound.setShort("TotalCookTime", (short)totalCookTime);

		ItemStackHelper.saveAllItems(compound, furnaceItemStacks);

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
		EnumFacilityTypes types = world.getBlockState(pos).getValue(BlockDoublingFurnace.TYPE);
		switch(types) {
		case wood:
			return 200;
		case stone:
			return 150;
		case iron:
			return 100;
		case gold:
			return 50;
		case diamond:
			return 8;
		case emerald:
			return 4;
		case lapis:
			return 50;
		case redstone:
			return 10;
		default:
			return 200;
		}
	}

	private boolean canSmelt() {
		if(furnaceItemStacks.get(0).isEmpty()) {
			return false;
		}
		else {
			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks.get(0));
			if(stack.isEmpty())	return false;
			if(furnaceItemStacks.get(2).isEmpty())	return true;
			if(!furnaceItemStacks.get(2).isItemEqual(stack))	return false;
			int result = furnaceItemStacks.get(2).getCount() + stack.getCount();
			return (result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
		}
	}

	public void smeltItem() {
		ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0)).copy();
		stack.setCount(stack.getCount() * ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingFurnace.TYPE)).getTimes());
		if(getStackInSlot(2).isEmpty()) {
			furnaceItemStacks.set(2, stack.copy());
		}
		else if(getStackInSlot(2).getItem() == stack.getItem()) {
			getStackInSlot(2).grow(stack.getCount());;
		}

		if(getStackInSlot(0).getItem() == Item.getItemFromBlock(Blocks.SPONGE) && getStackInSlot(0).getMetadata() == 1 && !((ItemStack)getStackInSlot(1)).isEmpty() && ((ItemStack)getStackInSlot(1)).getItem() == Items.BUCKET) {
			furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
		}

		furnaceItemStacks.get(0).shrink(1);
		if(furnaceItemStacks.get(0).getCount() <= 0) {
			furnaceItemStacks.set(0, ItemStack.EMPTY);
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		int fuel = TileEntityFurnace.getItemBurnTime(stack);
		return fuel;
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	@Override
	public void update() {
		boolean flag = isBurning();
		boolean flag1 = false;

		if(isBurning()) {
			--furnaceBurnTime;
		}

		if(!world.isRemote) {
			ItemStack stack = getStackInSlot(1);

			if(isBurning() || !stack.isEmpty() && !((ItemStack)getStackInSlot(0)).isEmpty()) {
				if(!isBurning() && canSmelt()) {
					furnaceBurnTime = getItemBurnTime(stack);
					currentItemBurnTime = furnaceBurnTime;

					if(isBurning()) {
						flag1 = true;

						if(!stack.isEmpty()) {
							Item item = stack.getItem();
							stack.shrink(1);

							if(stack.isEmpty()) {
								ItemStack stack1 = item.getContainerItem(stack);
								furnaceItemStacks.set(1, stack1);
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
				furnaceCookTime = MathHelper.clamp(furnaceCookTime - 2, 0, totalCookTime);
			}

			if(flag != isBurning()) {
				flag1 = true;

			}
		}

		if(flag1) {
			markDirty();
		}
	}

	@Override
	public int getSizeInventory() {
		return furnaceItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : furnaceItemStacks) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = getStackInSlot(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackShareTagsEqual(stack, itemStack);
		furnaceItemStacks.set(index, stack);

		if(stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
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
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) != this ? false : player.getDistanceSq((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 2 ? false : (index == 1 ? isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && stack.getItem() != Items.BUCKET : true);
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
		default:
			return 0;
		}
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
		furnaceItemStacks.clear();
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
		return direction != EnumFacing.DOWN || index != 1 || stack.getItem() == Items.BUCKET || stack.getItem() == Items.WATER_BUCKET;
	}
}
