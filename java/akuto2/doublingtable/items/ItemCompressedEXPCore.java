package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCompressedEXPCore extends Item{
	public static final int[] times = { 9, 81, 729, 6561, 59049, 531441 };

	public ItemCompressedEXPCore() {
		setCreativeTab(DoublingTable.tabs);
		setHasSubtypes(true);
		setMaxDamage(0);
		setUnlocalizedName("compressedexpCore");
		setRegistryName("doublingtable", "compressedexpcore");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < times.length; i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + times[stack.getItemDamage()];
	}
}
