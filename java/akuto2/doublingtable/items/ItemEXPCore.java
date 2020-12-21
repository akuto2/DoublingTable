package akuto2.doublingtable.items;

import akuto2.doublingtable.DoublingTable;
import net.minecraft.item.Item;

public class ItemEXPCore extends Item{
	public ItemEXPCore() {
		setCreativeTab(DoublingTable.tabs);
		setUnlocalizedName("expCore");
		setRegistryName("doublingtable", "expcore");
	}
}
