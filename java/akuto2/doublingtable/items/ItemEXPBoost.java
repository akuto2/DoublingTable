package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.Utils.EnumExpBoostTypes;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		int dmg = itemStackIn.getItemDamage();
		if(dmg == 10) {
			playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.AIR));
		}
		if(itemStackIn != null && itemStackIn.getItem() == this) {
			playerIn.addExperienceLevel(type.getTimes());
			itemStackIn.damageItem(1, playerIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
}
