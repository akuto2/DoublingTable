package com.akuto2.doublingtable;

import com.akuto2.doublingtable.registers.Blocks;
import com.akuto2.doublingtable.registers.CreativeTabs;
import com.akuto2.doublingtable.registers.Menus;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DoublingTable.MODID)
public class DoublingTable {
	public static final String MODID = "doublingtable";
	
	public DoublingTable() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		Blocks.BLOCKS.register(eventBus);
		Menus.MENUS.register(eventBus);
		CreativeTabs.TABS.register(eventBus);
	}
}
