package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemUEXPBoost extends Item{
	private final int upLevel = 10240;

	public ItemUEXPBoost() {
		setCreativeTab(DoublingTable.tabs);
		setMaxStackSize(1);
		setUnlocalizedName("uexpBoost");
		setRegistryName("doublingtable", "uexpboost");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(stack != null && stack.getItem() == this) {
			playerIn.addExperienceLevel(upLevel);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
