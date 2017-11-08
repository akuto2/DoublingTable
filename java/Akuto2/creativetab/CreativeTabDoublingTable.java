package Akuto2.creativetab;

import Akuto2.DoublingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabDoublingTable extends CreativeTabs {
	public CreativeTabDoublingTable(String type) {
		super(type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(DoublingTable.doublingTable);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel(){
		return "DoublingTable";
	}
}