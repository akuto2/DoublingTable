package akuto2.doublingtable.item;

import akuto2.doublingtable.DoublingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
