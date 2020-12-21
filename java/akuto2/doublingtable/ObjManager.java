package akuto2.doublingtable;

import akuto2.doublingtable.blocks.BlockDoublingFurnace;
import akuto2.doublingtable.blocks.BlockDoublingTable;
import akuto2.doublingtable.blocks.ItemBlockDoublingFurnace;
import akuto2.doublingtable.blocks.ItemBlockDoublingTable;
import akuto2.doublingtable.items.ItemCompressedEXPCore;
import akuto2.doublingtable.items.ItemCraftRod;
import akuto2.doublingtable.items.ItemDoublingCraftRod;
import akuto2.doublingtable.items.ItemEXPBoost;
import akuto2.doublingtable.items.ItemEXPCore;
import akuto2.doublingtable.items.ItemUEXPBoost;
import akuto2.doublingtable.tiles.TileEntityDoublingFurnace;
import akuto2.doublingtable.utils.Utils.EnumExpBoostTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ObjManager {
	public static Block doublingTable;
	public static Block doublingFurnace;
	public static Block doublingFurnace_On;

	public static Item craftRod;
	public static Item doublingCraftRod;
	public static Item expBoost1;
	public static Item expBoost2;
	public static Item expBoost3;
	public static Item expBoost4;
	public static Item expBoost5;
	public static Item expBoost6;
	public static Item expBoost7;
	public static Item expBoost8;
	public static Item expBoost9;
	public static Item expBoost10;
	public static Item expBoost11;
	public static Item uexpBoost;
	public static Item expCore;
	public static Item compressedExpCore;

	public static void register() {
		doublingTable = new BlockDoublingTable();
		doublingFurnace = new BlockDoublingFurnace(false).setRegistryName("doublingtable", "doublingfurnace").setUnlocalizedName("DoublingFurnace").setCreativeTab(DoublingTable.tabs);
		doublingFurnace_On = new BlockDoublingFurnace(true).setRegistryName("doublingtable", "doublingfurnaceon").setUnlocalizedName("DoublingFurnaceOn");

		craftRod = new ItemCraftRod();
		doublingCraftRod = new ItemDoublingCraftRod();
		expBoost1 = new ItemEXPBoost(EnumExpBoostTypes.x1);
		expBoost2 = new ItemEXPBoost(EnumExpBoostTypes.x2);
		expBoost3 = new ItemEXPBoost(EnumExpBoostTypes.x4);
		expBoost4 = new ItemEXPBoost(EnumExpBoostTypes.x8);
		expBoost5 = new ItemEXPBoost(EnumExpBoostTypes.x16);
		expBoost6 = new ItemEXPBoost(EnumExpBoostTypes.x32);
		expBoost7 = new ItemEXPBoost(EnumExpBoostTypes.x64);
		expBoost8 = new ItemEXPBoost(EnumExpBoostTypes.x128);
		expBoost9 = new ItemEXPBoost(EnumExpBoostTypes.x256);
		expBoost10 = new ItemEXPBoost(EnumExpBoostTypes.x512);
		expBoost11 = new ItemEXPBoost(EnumExpBoostTypes.x1024);
		uexpBoost = new ItemUEXPBoost();
		expCore = new ItemEXPCore();
		compressedExpCore = new ItemCompressedEXPCore();

		GameRegistry.register(doublingTable);
		GameRegistry.register(new ItemBlockDoublingTable(doublingTable).setRegistryName(doublingTable.getRegistryName()));
		GameRegistry.register(doublingFurnace);
		GameRegistry.register(new ItemBlockDoublingFurnace(doublingFurnace).setRegistryName(doublingFurnace.getRegistryName()));
		GameRegistry.register(doublingFurnace_On);
		GameRegistry.register(new ItemBlockDoublingFurnace(doublingFurnace_On).setRegistryName(doublingFurnace_On.getRegistryName()));
		GameRegistry.register(craftRod);
		GameRegistry.register(doublingCraftRod);
		GameRegistry.register(expBoost1);
		GameRegistry.register(expBoost2);
		GameRegistry.register(expBoost3);
		GameRegistry.register(expBoost4);
		GameRegistry.register(expBoost5);
		GameRegistry.register(expBoost6);
		GameRegistry.register(expBoost7);
		GameRegistry.register(expBoost8);
		GameRegistry.register(expBoost9);
		GameRegistry.register(expBoost10);
		GameRegistry.register(expBoost11);
		GameRegistry.register(uexpBoost);
		GameRegistry.register(expCore);
		GameRegistry.register(compressedExpCore);
	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "tile.doublingFurance");
	}
}
