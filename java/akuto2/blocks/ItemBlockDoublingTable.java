package akuto2.blocks;

import java.util.List;

import akuto2.utils.Utils.EnumFacilityTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDoublingTable extends ItemBlock{
	public ItemBlockDoublingTable(Block block) {
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
		return super.getUnlocalizedName() + "." + EnumFacilityTypes.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(EnumFacilityTypes.fromMeta(stack.getMetadata()).getTimes() + "x");
	}
}
