package akuto2.doublingtable.gui;

import java.util.List;
import java.util.Random;

import akuto2.doublingtable.tile.TileEntityEnchantmentTableMk2;
import akuto2.doublingtable.utils.EnchantmentHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerEnchantmentTableMk2 extends Container{
	public final TileEntityEnchantmentTableMk2 enchantmentTableMk2;
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private int[] lastEnchantments = new int[3];
	private int lastTier;
	private Random rand = new Random();
	public long nameSeed;

	public ContainerEnchantmentTableMk2(InventoryPlayer inventoryPlayer, TileEntityEnchantmentTableMk2 tile, World world, int posX, int posY, int posZ) {
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		enchantmentTableMk2 = tile;
		setTier(1);
		enchantmentTableMk2.setContainer(this);
		addSlotToContainer(new Slot(enchantmentTableMk2, 0, 25, 47) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return true;
			}
		});

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 0, enchantmentTableMk2.enchantLevels[0]);
		iCrafting.sendProgressBarUpdate(this, 1, enchantmentTableMk2.enchantLevels[1]);
		iCrafting.sendProgressBarUpdate(this, 2, enchantmentTableMk2.enchantLevels[2]);
		iCrafting.sendProgressBarUpdate(this, 3, enchantmentTableMk2.tier);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < crafters.size(); i++) {
			ICrafting iCrafting = (ICrafting)crafters.get(i);
			if(lastEnchantments[0] != enchantmentTableMk2.enchantLevels[0])
				iCrafting.sendProgressBarUpdate(this, 0, enchantmentTableMk2.enchantLevels[0]);

			if(lastEnchantments[1] != enchantmentTableMk2.enchantLevels[1])
				iCrafting.sendProgressBarUpdate(this, 1, enchantmentTableMk2.enchantLevels[1]);

			if(lastEnchantments[2] != enchantmentTableMk2.enchantLevels[2])
				iCrafting.sendProgressBarUpdate(this, 2, enchantmentTableMk2.enchantLevels[2]);

			if(lastTier != enchantmentTableMk2.tier)
				iCrafting.sendProgressBarUpdate(this, 3, enchantmentTableMk2.tier);
		}

		for(int i = 0; i < 3; i++) {
			lastEnchantments[i] = enchantmentTableMk2.enchantLevels[i];
		}
		lastTier = enchantmentTableMk2.tier;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if(id >= 0 && id <= 2) {
			enchantmentTableMk2.enchantLevels[id] = data;
		}
		else if(id == 3) {
			System.out.println("tier:" + data);
			enchantmentTableMk2.tier = data;
		}
		else {
			super.updateProgressBar(id, data);
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if(inventory == enchantmentTableMk2) {
			ItemStack stack = inventory.getStackInSlot(0);

			if(stack != null && stack.isItemEnchantable()) {
				nameSeed = rand.nextLong();
			}

			enchantmentTableMk2.changeEnchantLevel();
		}
	}

	@Override
	public boolean enchantItem(EntityPlayer player, int button) {
		ItemStack stack = enchantmentTableMk2.getStackInSlot(0);
		int enchantLevel = enchantmentTableMk2.enchantLevels[button];

		if(enchantLevel > 0 && stack != null && (player.experienceLevel >= enchantLevel || player.capabilities.isCreativeMode)) {
			if(!world.isRemote) {
				List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(rand, stack, enchantLevel);

				if(!list.isEmpty()) {
					boolean flag = stack.getItem() == Items.enchanted_book;
					player.addExperienceLevel(-enchantLevel);

					if(flag) {
						stack = new ItemStack(Items.enchanted_book);
						enchantmentTableMk2.setInventorySlotContents(0, stack);
						if(list.size() > 1) {
							list.remove(rand.nextInt(list.size()));
						}
					}

					for(int i = 0; i < list.size(); ++i) {
						EnchantmentData enchantmentData = list.get(i);

						if(flag) {
							Items.enchanted_book.addEnchantment(stack, enchantmentData);
						}
						else {
							EnchantmentHelper.addEnchantment(stack, enchantmentData);
						}
					}

					onCraftMatrixChanged(enchantmentTableMk2);
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if(!world.isRemote) {
			ItemStack stack = enchantmentTableMk2.getStackInSlotOnClosing(0);

			if(stack != null) {
				player.dropPlayerItemWithRandomChoice(stack, false);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public void setTier(int tier) {
		enchantmentTableMk2.tier = tier;
	}

	/*
	public void changeEnchantLevel(int tier) {
		this.tier = tier;
		ItemStack stack = tableInventory.getStackInSlot(0);

		if(stack != null && stack.isItemEnchantable()) {
			if(!world.isRemote) {
				for(int i = 0; i < 3; i++) {
					enchantLevels[i] = EnchantmentHelper.calcItemStackEnchantability(rand, i, tier, stack);
				}
			}
		}
		else {
			for(int i = 0; i < 3; i++) {
				enchantLevels[i] = 0;
			}
		}
	}
	*/

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot)inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack2 = slot.getStack();
			stack = stack2.copy();

			if(index == 0) {
				if(!mergeItemStack(stack2, 1, 37, true)) {
					return null;
				}
			}
			else {
				if(((Slot)inventorySlots.get(0)).getHasStack() || !((Slot)inventorySlots.get(0)).isItemValid(stack2)) {
					return null;
				}

				if(stack2.hasTagCompound() && stack2.stackSize == 1) {
					((Slot)inventorySlots.get(0)).putStack(stack2.copy());
					stack2.stackSize = 0;
				}
				else if(stack2.stackSize >= 1) {
					((Slot)inventorySlots.get(0)).putStack(new ItemStack(stack2.getItem(), 1, stack2.getItemDamage()));
					--stack2.stackSize;
				}
			}

			if(stack2.stackSize == 0) {
				slot.putStack((ItemStack)null);
			}
			else {
				slot.onSlotChanged();
			}

			if(stack2.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack2);
		}

		return stack;
	}
}
