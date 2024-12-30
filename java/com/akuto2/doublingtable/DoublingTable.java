package com.akuto2.doublingtable;

import com.akuto2.doublingtable.registers.DTBlockEntities;
import com.akuto2.doublingtable.registers.DTBlocks;
import com.akuto2.doublingtable.registers.DTCreativeTabs;
import com.akuto2.doublingtable.registers.DTMenus;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DoublingTable.MODID)
public class DoublingTable {
	public static final String MODID = "doublingtable";
	
	public DoublingTable() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		DTBlocks.BLOCKS.register(eventBus);
		DTBlockEntities.BLOCK_ENTITY.register(eventBus);
		DTMenus.MENUS.register(eventBus);
		DTCreativeTabs.TABS.register(eventBus);
	}
}
