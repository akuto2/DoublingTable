package akuto2.doublingtable.utils;

import akuto2.doublingtable.ObjManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabDoublingTable extends CreativeTabs{
	public CreativeTabDoublingTable(String name) {
		super(name);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ObjManager.doublingTable);
	}

	@Override
	public String getTranslatedTabLabel() {
		return "DoublingTable";
	}
}
