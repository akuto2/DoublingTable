package akuto2.doublingtable.item;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.Utils;
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
		if(!world.isRemote)
			player.openGui(DoublingTable.instance, Utils.GUI_CRAFTROD_ID, world, player.chunkCoordX, player.chunkCoordY, player.chunkCoordZ);
    	return item;
    }
}
