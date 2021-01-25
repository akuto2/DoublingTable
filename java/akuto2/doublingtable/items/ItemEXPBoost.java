package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.EnumUtils.EnumExpBoostTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEXPBoost extends Item{
	private EnumExpBoostTypes type;

	public ItemEXPBoost(EnumExpBoostTypes type) {
		this.type = type;
		setMaxDamage(10);
		setCreativeTab(DoublingTable.tabs);
		setMaxStackSize(1);
		setUnlocalizedName("expBoost" + type.getNum());
		setRegistryName("doublingtable", "expboost" + type.getNum());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		int dmg = stack.getItemDamage();
		if(dmg == 10) {
			playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.AIR));
		}
		if(!stack.isEmpty() && stack.getItem() == this) {
			playerIn.addExperienceLevel(type.getTimes());
			stack.damageItem(1, playerIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
