package akuto2.doublingtable.tile;

import java.util.Random;

import akuto2.doublingtable.gui.ContainerEnchantmentTableMk2;
import akuto2.doublingtable.utils.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityEnchantmentTableMk2 extends TileEntity implements IInventory{
	public int tickCount;
	public float pageFlip;
	public float pageFlipPrev;
	public float flipT;
	public float flipA;
	public float bookSpread;
	public float bookSpreadPrev;
	public float bookRotation;
	public float bookRotationPrev;
	public float tRot;
	public int[] enchantLevels = new int[3];
	public int tier;
	public int prevTier;
	private ItemStack inventorySlotContents;
	private ContainerEnchantmentTableMk2 container;
	private static Random rand = new Random();

	@Override
	public void updateEntity() {
		super.updateEntity();
		bookSpreadPrev = bookSpread;
		bookRotationPrev = bookRotation;
		EntityPlayer player = worldObj.getClosestPlayer((float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, 3.0D);

		if(tier != prevTier) {
			markDirty();

			prevTier = tier;
		}

		if(player != null) {
			double d0 = player.posX - (xCoord + 0.5F);
			double d1 = player.posZ - (zCoord + 0.5F);
			tRot = (float)Math.atan2(d1, d0);
			bookSpread += 0.1F;

			if(bookSpread < 0.5F || rand.nextInt(40) == 0) {
				float f1 = flipT;

				while(true) {
					flipT += (float)(rand.nextInt(4) - rand.nextInt(4));

					if(f1 != flipT) {
						break;
					}
				}
			}
		}
		else {
			tRot += 0.02F;
			bookSpread -= 0.1F;
		}

		while(bookRotation >= Math.PI) {
			bookRotation -= Math.PI * 2F;
		}

		while(bookRotation < -Math.PI) {
			bookRotation += Math.PI * 2F;
		}

		while(tRot >= Math.PI) {
			tRot -= Math.PI * 2F;
		}

		while(tRot < -Math.PI) {
			tRot += Math.PI * 2F;
		}

		float f2;

		for(f2 = tRot - bookRotation; f2 >= Math.PI; f2 -= Math.PI * 2F) {}

		while(f2 < -Math.PI) {
			f2 += Math.PI * 2F;
		}

		bookRotation += f2 * 0.4F;
		bookSpread = MathHelper.clamp_float(bookSpread, 0.0F, 1.0F);
		++tickCount;
		pageFlipPrev = pageFlip;
		float f = (flipT - pageFlip) * 0.4F;
		f = MathHelper.clamp_float(f, -0.2F, 0.2F);
		flipA += (f - flipA) * 0.9F;
		pageFlip += flipA;
	}

	/**
	 * エンチャントテーブルに表示されるレベルを変更する
	 */
	public void changeEnchantLevel() {
		ItemStack stack = inventorySlotContents;

		if(stack != null && stack.isItemEnchantable()) {
			if(!worldObj.isRemote) {
				for(int i = 0; i < 3; i++) {
					enchantLevels[i] = EnchantmentHelper.calcItemStackEnchantability(rand, i, tier, stack);
				}

				if(container != null)
					container.detectAndSendChanges();
			}
		}
		else {
			for(int i = 0; i < 3; i++) {
				enchantLevels[i] = 0;
			}
		}
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
		if(container != null) {
			container.onCraftMatrixChanged(this);
		}
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

	public void setContainer(ContainerEnchantmentTableMk2 container) {
		this.container = container;
	}
}
