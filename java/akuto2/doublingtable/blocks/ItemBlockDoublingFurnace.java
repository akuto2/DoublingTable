package akuto2.doublingtable.blocks;

import java.util.List;

import akuto2.doublingtable.utils.EnumUtils.EnumFacilityTypes;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockDoublingFurnace extends ItemBlock{
	public ItemBlockDoublingFurnace(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + EnumFacilityTypes.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(EnumFacilityTypes.fromMeta(stack.getMetadata()).getTimes() + "x");
	}
}
