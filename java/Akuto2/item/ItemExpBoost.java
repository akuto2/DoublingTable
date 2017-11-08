package Akuto2.item;

import Akuto2.DoublingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemExpBoost extends Item{
	private int upLevel;
	public static final int[] level = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};

	public ItemExpBoost(int meta){
		this.setMaxDamage(10);
		this.setCreativeTab(DoublingTable.tabDoublingTable);
		this.setMaxStackSize(1);
		upLevel = level[meta];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player){
		int dmg = item.getItemDamage();
		if(dmg == 10){
			player.inventory.addItemStackToInventory(new ItemStack(Blocks.air));
		}
		if(item != null && item.getItem() == this){
			player.addExperienceLevel(upLevel);
			item.damageItem(1, player);;
		}
		return item;
	}


}
