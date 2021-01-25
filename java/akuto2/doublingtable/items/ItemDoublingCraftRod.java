package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.EnumUtils.EnumFacilityTypes;
import akuto2.doublingtable.utils.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDoublingCraftRod extends Item{
	public ItemDoublingCraftRod() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(DoublingTable.tabs);
		setUnlocalizedName("doublingCraftRod");
		setRegistryName("doublingtable", "doublingcraftrod");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(EnumFacilityTypes type : EnumFacilityTypes.values()) {
				items.add(new ItemStack(this, 1, type.getMeta()));
			}
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "." + EnumFacilityTypes.fromMeta(stack.getMetadata());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote) {
			playerIn.openGui(DoublingTable.instance, Utils.GUI_DOUBLINGCRAFTROD_ID, worldIn, playerIn.chunkCoordX, playerIn.chunkCoordY, playerIn.chunkCoordZ);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}
}
