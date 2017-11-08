package Akuto2.tile;

import Akuto2.blocks.BlockDoublingFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityDoublingFurnace extends TileEntity implements ISidedInventory{
	private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    public byte facing = 0;
    public int outputSlot = 1;
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    private String name = null;
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;

	@Override
	public int getSizeInventory() {
		return this.furnaceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int meta) {
		return this.furnaceItemStacks[meta];
	}

	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.furnaceItemStacks[par1] != null) {
			ItemStack itemstack;
			if (this.furnaceItemStacks[par1].stackSize <= par2) {
				itemstack = this.furnaceItemStacks[par1];
				this.furnaceItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.furnaceItemStacks[par1].splitStack(par2);

				if (this.furnaceItemStacks[par1].stackSize == 0) {
					this.furnaceItemStacks[par1] = null;
				}
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot){
		if(this.furnaceItemStacks[slot] != null){
			ItemStack itemStack = this.furnaceItemStacks[slot];
			this.furnaceItemStacks[slot] = null;
			return itemStack;
		}
		else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.furnaceItemStacks[slot] = itemStack;

		if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()){
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName()?"tile." + this.getBlockMetadata() + ".name":this.name;
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < tagList.tagCount(); ++i){
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");

			if(byte0 >= 0 && byte0 < this.furnaceItemStacks.length){
				this.furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}

		this.furnaceBurnTime = tagCompound.getShort("BurnTime");
		this.furnaceCookTime = tagCompound.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

		if (tagCompound.hasKey("CustomName", 8)) {
			this.name = tagCompound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
		tagCompound.setShort("CookTime", (short)this.furnaceCookTime);
		NBTTagList tagList = new NBTTagList();

		for(int i = 0; i < this.furnaceItemStacks.length; ++i){
			if(this.furnaceItemStacks[i] != null){
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound1.setByte("Slot", (byte)i);
				this.furnaceItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}

		tagCompound.setTag("Items", tagList);

		if (this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.name);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1){
		return this.furnaceCookTime * par1 / getBurnTime();
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainScaled(int par1){
		if(this.currentItemBurnTime == 0){
			this.currentItemBurnTime = getBurnTime();
		}

		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
	}

	public boolean isBurning()
	{
		return this.furnaceBurnTime > 0;
	}

	public void updateEntity() {
		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;

		if (this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}

		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

				if (this.furnaceBurnTime > 0) {
					flag1 = true;
					if (this.furnaceItemStacks[1] != null) {
						--this.furnaceItemStacks[1].stackSize;

						if (this.furnaceItemStacks[1].stackSize == 0) {
							this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
						}
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				++this.furnaceCookTime;
				if (this.furnaceCookTime == getBurnTime()) {
					this.furnaceCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}
		}

		if (flag != this.furnaceBurnTime > 0) {
			flag1 = true;
			BlockDoublingFurnace.updateBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}

		if (flag1) {
			this.markDirty();
		}
	}

	private boolean canSmelt()
	{
		if (this.furnaceItemStacks[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
			if (itemstack == null) return false;
			if (this.furnaceItemStacks[2] == null) return true;
			if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;
			int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
			return (result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
		}
	}

	public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]).copy();
        itemstack.stackSize *= BlockDoublingFurnace.times[getBlockMetadata()];
        if (this.furnaceItemStacks[2] == null)
        {
            this.furnaceItemStacks[2] = itemstack;
        }
        else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem())
        {
            this.furnaceItemStacks[2].stackSize += itemstack.stackSize;
        }

        this.furnaceItemStacks[0].stackSize -= 1;
        if(this.furnaceItemStacks[0].stackSize <= 0){
        	this.furnaceItemStacks[0] = null;
        }
    }

	public int getBurnTime()
	{
	    int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	    if (meta == 0) {
	      return 200;
	    }
	    if (meta == 1) {
	    	return 150;
	    }
	    if (meta == 2) {
	    	return 100;
	    }
	    if (meta == 3) {
	    	return 50;
	    }
	    if (meta == 4) {
	    	return 8;
	    }
	    if (meta == 5){
	    	return 4;
	    }
	    return 200;
	  }

	private int getItemBurnTime(ItemStack par0ItemStack)
	{
//		if(par0ItemStack == null){
//			return 0;
//		}
//		else{
//			Item item = par0ItemStack.getItem();
//
//			if (par0ItemStack.getItem() instanceof ItemBlock && Block.getBlockFromItem(item) != null)
//			{
//				Block block = Block.getBlockFromItem(item);
//
//				if (block == Blocks.wooden_slab)
//				{
//					return 150;
//				}
//
//				if (block.getMaterial() == Material.wood)
//				{
//					return 300;
//				}
//
//				if (block == Blocks.coal_block)
//				{
//					return 16000;
//				}
//
//				if(block == Blocks.sapling){
//					return 100;
//				}
//			}
//
//			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
//			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
//			if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 200;
//			if (item == Items.stick) return 100;
//			if (item == Items.coal) return 1600;
//			if (item == Items.lava_bucket) return 20000;
//			if (item == Items.blaze_rod) return 2400;
//			return GameRegistry.getFuelValue(par0ItemStack);
//		}
		int fuel = TileEntityFurnace.getItemBurnTime(par0ItemStack);
		return fuel * getBurnTime() / 200;
	}

	public  boolean isItemFuel(ItemStack itemStack){
		return getItemBurnTime(itemStack) > 0;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.name != null && this.name.length() > 0;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemStack) {
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(itemStack) : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemStack, int par3) {
		return this.isItemValidForSlot(par1, itemStack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemStack, int par3) {
		return par3 != 0 || par1 != 1 || itemStack.getItem() == Items.bucket;
	}
}
