package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.object.CreativeTabRegistryObject;
import com.akuto2.akutolib.registration.register.CreativeTabDeferredRegister;
import com.akuto2.doublingtable.DoublingTable;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class CreativeTabs {
	public static final CreativeTabDeferredRegister TABS = new CreativeTabDeferredRegister(DoublingTable.MODID, CreativeTabs::addToExisitingTabs);
	
	public static final CreativeTabRegistryObject DOUBLING_TABLE = TABS.register(DoublingTable.MODID, "creativetab.doublingtable", Blocks.DOUBLING_TABLE_WOOD, builder -> 
		builder.displayItems((parameters, output) -> {
			output.accept(Blocks.DOUBLING_TABLE_WOOD);
			output.accept(Blocks.DOUBLING_TABLE_STONE);
			output.accept(Blocks.DOUBLING_TABLE_IRON);
			output.accept(Blocks.DOUBLING_TABLE_GOLD);
			output.accept(Blocks.DOUBLING_TABLE_DIAMOND);
			output.accept(Blocks.DOUBLING_TABLE_EMERALD);
		})
	);

	/**
	 * バニラのクリエイティブタブに入れる場合はこっちで
	 */
	private static void addToExisitingTabs(BuildCreativeModeTabContentsEvent event) {
		
	}
}
