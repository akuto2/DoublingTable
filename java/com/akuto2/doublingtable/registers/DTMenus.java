package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.object.MenuTypeRegistryObject;
import com.akuto2.akutolib.registration.register.MenuTypeDeferredRegister;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.menu.MenuDoublingFurnace;
import com.akuto2.doublingtable.menu.MenuDoublingTable;

public class DTMenus {
	public static final MenuTypeDeferredRegister MENUS = new MenuTypeDeferredRegister(DoublingTable.MODID);
	
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_WOOD = MENUS.register(DTBlocks.DOUBLING_TABLE_WOOD, MenuDoublingTable.Wood::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_STONE = MENUS.register(DTBlocks.DOUBLING_TABLE_STONE, MenuDoublingTable.Stone::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_IRON = MENUS.register(DTBlocks.DOUBLING_TABLE_IRON, MenuDoublingTable.Iron::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_GOLD = MENUS.register(DTBlocks.DOUBLING_TABLE_GOLD, MenuDoublingTable.Gold::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_DIAMOND = MENUS.register(DTBlocks.DOUBLING_TABLE_DIAMOND, MenuDoublingTable.Diamond::new);
	public static final MenuTypeRegistryObject<MenuDoublingTable> DOUBLING_TABLE_EMERALD = MENUS.register(DTBlocks.DOUBLING_TABLE_EMERALD, MenuDoublingTable.Emerald::new);

	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_WOOD = MENUS.register(DTBlocks.DOUBLING_FURNACE_WOOD, BlockEntityDoublingFurnace.Wood.class, MenuDoublingFurnace.Wood::new);
	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_STONE = MENUS.register(DTBlocks.DOUBLING_FURNACE_STONE, BlockEntityDoublingFurnace.Stone.class, MenuDoublingFurnace.Stone::new);
	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_IRON = MENUS.register(DTBlocks.DOUBLING_FURNACE_IRON, BlockEntityDoublingFurnace.Iron.class, MenuDoublingFurnace.Iron::new);
	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_GOLD = MENUS.register(DTBlocks.DOUBLING_FURNACE_GOLD, BlockEntityDoublingFurnace.Gold.class, MenuDoublingFurnace.Gold::new);
	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_DIAMOND = MENUS.register(DTBlocks.DOUBLING_FURNACE_DIAMOND, BlockEntityDoublingFurnace.Diamond.class, MenuDoublingFurnace.Diamond::new);
	public static final MenuTypeRegistryObject<MenuDoublingFurnace> DOUBLING_FURNACE_EMERALD = MENUS.register(DTBlocks.DOUBLING_FURNACE_EMERALD, BlockEntityDoublingFurnace.Emerald.class, MenuDoublingFurnace.Emerald::new);
}
