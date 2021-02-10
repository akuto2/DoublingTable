package akuto2.doublingtable.gui;

import java.util.List;
import java.util.Random;

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
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerEnchantmentTableMk2 extends Container{
	public IInventory tableInventory;
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private Random rand = new Random();
	public long nameSeed;
	public int[] enchantLevels = new int[3];

	public ContainerEnchantmentTableMk2(InventoryPlayer inventoryPlayer, World world, int posX, int posY, int posZ) {
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		tableInventory = new InventoryBasic("Enchant", true, 2) {
			@Override
			public int getInventoryStackLimit() {
				return 64;
			}

			@Override
			public void markDirty() {
				super.markDirty();
				ContainerEnchantmentTableMk2.this.onCraftMatrixChanged(this);
			}
		};

		addSlotToContainer(new Slot(tableInventory, 0, 15, 47) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return true;
			}

			@Override
			public int getSlotStackLimit() {
				return 1;
			}
		});

		addSlotToContainer(new Slot(tableInventory, 1, 35, 47) {
			List<ItemStack> ores = OreDictionary.getOres("gemLapis");

			@Override
			public boolean isItemValid(ItemStack stack) {
				for(ItemStack ore : ores) {
					if(OreDictionary.itemMatches(ore, stack, false)) {
						return true;
					}
				}
				return false;
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
		iCrafting.sendProgressBarUpdate(this, 0, enchantLevels[0]);
		iCrafting.sendProgressBarUpdate(this, 1, enchantLevels[1]);
		iCrafting.sendProgressBarUpdate(this, 2, enchantLevels[2]);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < crafters.size(); i++) {
			ICrafting iCrafting = (ICrafting)crafters.get(i);
			iCrafting.sendProgressBarUpdate(this, 0, enchantLevels[0]);
			iCrafting.sendProgressBarUpdate(this, 1, enchantLevels[1]);
			iCrafting.sendProgressBarUpdate(this, 2, enchantLevels[2]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if(id >= 0 && id <= 2) {
			enchantLevels[id] = data;
		}
		else {
			super.updateProgressBar(id, data);
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if(inventory == tableInventory) {
			ItemStack stack = inventory.getStackInSlot(0);
			ItemStack lapis = inventory.getStackInSlot(1);

			if(stack != null && stack.isItemEnchantable()) {
				nameSeed = rand.nextLong();
			}

			changeEnchantLevel(lapis != null ? lapis.stackSize : 0);
		}
	}

	@Override
	public boolean enchantItem(EntityPlayer player, int button) {
		ItemStack stack = tableInventory.getStackInSlot(0);
		ItemStack lapis = tableInventory.getStackInSlot(1);
		int enchantLevel = enchantLevels[button];

		if(enchantLevel > 0 && stack != null && (player.experienceLevel >= enchantLevel || player.capabilities.isCreativeMode)) {
			if(!world.isRemote) {
				List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(rand, stack, enchantLevel);

				if(!list.isEmpty()) {
					boolean flag = stack.getItem() == Items.enchanted_book;
					player.addExperienceLevel(-enchantLevel);

					if(flag) {
						stack = new ItemStack(Items.enchanted_book);
						tableInventory.setInventorySlotContents(0, stack);
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

					if(!player.capabilities.isCreativeMode) {
						lapis.stackSize = lapis.stackSize > 10 ? lapis.stackSize - 10 : 0;
						if(lapis.stackSize <= 0) {
							tableInventory.setInventorySlotContents(1, null);
						}
					}

					onCraftMatrixChanged(tableInventory);
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
			for(int i = 0; i < tableInventory.getSizeInventory(); i++) {
				ItemStack stack = tableInventory.getStackInSlotOnClosing(i);

				if(stack != null) {
					player.dropPlayerItemWithRandomChoice(stack, false);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	/**
	 * エンチャントテーブルに表示されるレベルを変更する
	 */
	public void changeEnchantLevel(int tier) {
		ItemStack stack = tableInventory.getStackInSlot(0);
		int amount = MathHelper.clamp_int(tier, 0, 10);

		if(stack != null && stack.isItemEnchantable()) {
			if(!world.isRemote) {
				for(int i = 0; i < 3; i++) {
					enchantLevels[i] = EnchantmentHelper.calcItemStackEnchantability(rand, i, amount, stack);
				}

				detectAndSendChanges();
			}
		}
		else {
			for(int i = 0; i < 3; i++) {
				enchantLevels[i] = 0;
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot)inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack2 = slot.getStack();
			stack = stack2.copy();

			if(index == 0) {
				if(!mergeItemStack(stack2, 2, 38, true)) {
					return null;
				}
			}
			else if(index == 1) {
				if(!mergeItemStack(stack2, 2, 38, true)) {
					return null;
				}
			}
			else if(stack2.getItem() == Items.dye && (stack2.getItemDamage() == 4)) {
				if(!mergeItemStack(stack2, 1, 2, true)) {
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
