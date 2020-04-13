package Akuto2.Utils;

import Akuto2.ObjManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabDoublingTable extends CreativeTabs {

	public CreativeTabDoublingTable(String name) {
		super(name);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ObjManager.doublingTable, 1, 0);
	}

	@Override
	public String getTranslatedTabLabel() {
		return "DoublingTable";
	}
}
