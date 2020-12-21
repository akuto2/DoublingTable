package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCraftRod extends Item{
	public ItemCraftRod() {
		setUnlocalizedName("craftRod");
		setCreativeTab(DoublingTable.tabs);
		setRegistryName("doublingtable", "craftrod");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if(!worldIn.isRemote) {
			playerIn.openGui(DoublingTable.instance, Utils.GUI_CRAFTROD_ID, worldIn, playerIn.chunkCoordX, playerIn.chunkCoordY, playerIn.chunkCoordZ);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}
}
