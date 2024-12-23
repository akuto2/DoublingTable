package com.akuto2.doublingtable.registers.client;

import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.gui.GuiDoublingTable;
import com.akuto2.doublingtable.registers.Menus;

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
			registerScreen(Menus.DOUBLING_TABLE_WOOD, GuiDoublingTable.Wood::new);
			registerScreen(Menus.DOUBLING_TABLE_STONE, GuiDoublingTable.Stone::new);
			registerScreen(Menus.DOUBLING_TABLE_IRON, GuiDoublingTable.Iron::new);
			registerScreen(Menus.DOUBLING_TABLE_GOLD, GuiDoublingTable.Gold::new);
			registerScreen(Menus.DOUBLING_TABLE_DIAMOND, GuiDoublingTable.Diamond::new);
			registerScreen(Menus.DOUBLING_TABLE_EMERALD, GuiDoublingTable.Emerald::new);
		});
	}
	
	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> void registerScreen(MenuTypeRegistryObject<C> menu, ScreenConstructor<C, S> factory) {
		MenuScreens.register(menu.get(), factory);
	}
}
