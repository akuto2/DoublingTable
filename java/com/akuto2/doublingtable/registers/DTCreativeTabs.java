package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.object.CreativeTabRegistryObject;
import com.akuto2.akutolib.registration.register.CreativeTabDeferredRegister;
import com.akuto2.doublingtable.DoublingTable;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class DTCreativeTabs {
	public static final CreativeTabDeferredRegister TABS = new CreativeTabDeferredRegister(DoublingTable.MODID, DTCreativeTabs::addToExisitingTabs);
	
	public static final CreativeTabRegistryObject DOUBLING_TABLE = TABS.register(DoublingTable.MODID, "creativetab.doublingtable", DTBlocks.DOUBLING_TABLE_WOOD, builder -> 
		builder.displayItems((parameters, output) -> {
			output.accept(DTBlocks.DOUBLING_TABLE_WOOD);
			output.accept(DTBlocks.DOUBLING_TABLE_STONE);
			output.accept(DTBlocks.DOUBLING_TABLE_IRON);
			output.accept(DTBlocks.DOUBLING_TABLE_GOLD);
			output.accept(DTBlocks.DOUBLING_TABLE_DIAMOND);
			output.accept(DTBlocks.DOUBLING_TABLE_EMERALD);
			
			output.accept(DTBlocks.DOUBLING_FURNACE_WOOD);
			output.accept(DTBlocks.DOUBLING_FURNACE_STONE);
			output.accept(DTBlocks.DOUBLING_FURNACE_IRON);
			output.accept(DTBlocks.DOUBLING_FURNACE_GOLD);
			output.accept(DTBlocks.DOUBLING_FURNACE_DIAMOND);
			output.accept(DTBlocks.DOUBLING_FURNACE_EMERALD);
		})
	);

	/**
	 * バニラのクリエイティブタブに入れる場合はこっちで
	 */
	private static void addToExisitingTabs(BuildCreativeModeTabContentsEvent event) {
		
	}
}
