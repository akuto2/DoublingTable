package Akuto2.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Akuto2.DoublingTable;

public class ItemCraftRod extends Item {
	public ItemCraftRod(){
		super();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		player.openGui(DoublingTable.instance, 2, world, player.chunkCoordX, player.chunkCoordY, player.chunkCoordZ);
    	return item;
    }
}
