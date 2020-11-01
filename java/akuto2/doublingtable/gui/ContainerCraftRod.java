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
import net.minecraft.world.World;

public class ContainerCraftRod extends Container{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int PosX;
	private int PosY;
	private int PosZ;

	public ContainerCraftRod(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5){
		this.worldObj = par2World;
		this.PosX = par3;
		this.PosY = par4;
		this.PosZ = par5;
		this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));

		int l;
		int i1;
		for(l = 0; l < 3; ++l) {
			for(i1 = 0; i1 < 3; ++i1){
				this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
			}
		}

		for(l = 0; l < 3; ++l){
			for(i1 = 0; i1 < 9; ++i1){
				this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for(l = 0; l < 9; ++l){
			this.addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1EntityPlayer){
		ItemStack itemStack = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);
		this.craftResult.setInventorySlotContents(0, itemStack);
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer){
		super.onContainerClosed(par1EntityPlayer);

		if(!this.worldObj.isRemote){
			for (int var2 = 0; var2 < this.craftMatrix.getSizeInventory(); ++var2){
				ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

				if(var3 != null)
					par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player){
		return true;
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if(var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if(par2 == 0)
            {
                if(!this.mergeItemStack(var5, 10, 46, true))
                    return null;

                var4.onSlotChange(var5, var3);
            }
            else if(par2 >= 19 && par2 < 46)
            {
                if(!this.mergeItemStack(var5, 37, 46, false))
                    return null;
            }
            else if(par2 >= 46 && par2 < 55)
            {
                if(!this.mergeItemStack(var5, 10, 37, false))
                    return null;
            }
            else if(!this.mergeItemStack(var5, 10, 46, false))
            	return null;

            if(var5.stackSize == 0)
                var4.putStack((ItemStack)null);
            else
                var4.onSlotChanged();

            if(var5.stackSize == var3.stackSize)
                return null;

            var4.onPickupFromSlot(player, var5);
        }

        return var3;
    }

	public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot){
		return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
	}
}
