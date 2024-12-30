package com.akuto2.doublingtable.registers.client;

import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.gui.GuiDoublingFurnace;
import com.akuto2.doublingtable.gui.GuiDoublingTable;
import com.akuto2.doublingtable.registers.DTMenus;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegisterEvent;

@EventBusSubscriber(modid = DoublingTable.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientRegistration {
	@SubscribeEvent
	public static void guiRegistryEvent(RegisterEvent event) {
		event.register(Registries.MENU, helper -> {
			registerScreen(DTMenus.DOUBLING_TABLE_WOOD, GuiDoublingTable::new);
			registerScreen(DTMenus.DOUBLING_TABLE_STONE, GuiDoublingTable::new);
			registerScreen(DTMenus.DOUBLING_TABLE_IRON, GuiDoublingTable::new);
			registerScreen(DTMenus.DOUBLING_TABLE_GOLD, GuiDoublingTable::new);
			registerScreen(DTMenus.DOUBLING_TABLE_DIAMOND, GuiDoublingTable::new);
			registerScreen(DTMenus.DOUBLING_TABLE_EMERALD, GuiDoublingTable::new);
			
			registerScreen(DTMenus.DOUBLING_FURNACE_WOOD, GuiDoublingFurnace::new);
			registerScreen(DTMenus.DOUBLING_FURNACE_STONE, GuiDoublingFurnace::new);
			registerScreen(DTMenus.DOUBLING_FURNACE_IRON, GuiDoublingFurnace::new);
			registerScreen(DTMenus.DOUBLING_FURNACE_GOLD, GuiDoublingFurnace::new);
			registerScreen(DTMenus.DOUBLING_FURNACE_DIAMOND, GuiDoublingFurnace::new);
			registerScreen(DTMenus.DOUBLING_FURNACE_EMERALD, GuiDoublingFurnace::new);
		});
	}
	
	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> void registerScreen(MenuTypeRegistryObject<C> menu, ScreenConstructor<C, S> factory) {
		MenuScreens.register(menu.get(), factory);
	}
}
