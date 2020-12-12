package akuto2.doublingtable.item;

import akuto2.doublingtable.DoublingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUEXPBoost extends Item {
	private int upLevel = 10240;

	public ItemUEXPBoost(){
		this.setCreativeTab(DoublingTable.tabDoublingTable);
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player){
		if(item != null && item.getItem() == this){
			player.addExperienceLevel(upLevel);
		}
		return item;
	}
}
