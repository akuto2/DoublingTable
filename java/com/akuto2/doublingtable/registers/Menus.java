package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.akutolib.registration.register.MenuTypeDeferredRegister;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.menu.MenuDoublingTable;

public class Menus {
	public static final MenuTypeDeferredRegister MENUS = new MenuTypeDeferredRegister(DoublingTable.MODID);
	
	public static final MenuTypeRegistryObject<MenuDoublingTable.Wood> DOUBLING_TABLE_WOOD = MENUS.register(Blocks.DOUBLING_TABLE_WOOD, MenuDoublingTable.Wood::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable.Stone> DOUBLING_TABLE_STONE = MENUS.register(Blocks.DOUBLING_TABLE_STONE, MenuDoublingTable.Stone::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable.Iron> DOUBLING_TABLE_IRON = MENUS.register(Blocks.DOUBLING_TABLE_IRON, MenuDoublingTable.Iron::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable.Gold> DOUBLING_TABLE_GOLD = MENUS.register(Blocks.DOUBLING_TABLE_GOLD, MenuDoublingTable.Gold::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable.Diamond> DOUBLING_TABLE_DIAMOND = MENUS.register(Blocks.DOUBLING_TABLE_DIAMOND, MenuDoublingTable.Diamond::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable.Emerald> DOUBLING_TABLE_EMERALD = MENUS.register(Blocks.DOUBLING_TABLE_EMERALD, MenuDoublingTable.Emerald::new);
}
