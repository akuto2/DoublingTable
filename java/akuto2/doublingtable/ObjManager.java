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
import akuto2.doublingtable.utils.EnumUtils.EnumExpBoostTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = "doublingtable")
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

	@SubscribeEvent
	public static void registerBlock(Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		doublingTable = new BlockDoublingTable();
		doublingFurnace = new BlockDoublingFurnace(false).setRegistryName("doublingtable", "doublingfurnace").setUnlocalizedName("doublingFurnace").setCreativeTab(DoublingTable.tabs);
		doublingFurnace_On = new BlockDoublingFurnace(true).setRegistryName("doublingtable", "doublingfurnaceon").setUnlocalizedName("doublingFurnaceOn");

		registry.register(doublingTable);
		registry.register(doublingFurnace);
		registry.register(doublingFurnace_On);
	}

	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

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

		registry.register(craftRod);
		registry.register(doublingCraftRod);
		registry.register(expBoost1);
		registry.register(expBoost2);
		registry.register(expBoost3);
		registry.register(expBoost4);
		registry.register(expBoost5);
		registry.register(expBoost6);
		registry.register(expBoost7);
		registry.register(expBoost8);
		registry.register(expBoost9);
		registry.register(expBoost10);
		registry.register(expBoost11);
		registry.register(uexpBoost);
		registry.register(expCore);
		registry.register(compressedExpCore);
		registry.register(new ItemBlockDoublingTable(doublingTable).setRegistryName(doublingTable.getRegistryName()));
		registry.register(new ItemBlockDoublingFurnace(doublingFurnace).setRegistryName(doublingFurnace.getRegistryName()));
		registry.register(new ItemBlockDoublingFurnace(doublingFurnace_On).setRegistryName(doublingFurnace_On.getRegistryName()));
	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, new ResourceLocation("doublingtable", "doublingfurnace"));
	}
}
