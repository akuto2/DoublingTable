package Akuto2;

import Akuto2.Blocks.BlockDoublingFurnace;
import Akuto2.Blocks.BlockDoublingTable;
import Akuto2.Blocks.ItemBlockDoublingFurnace;
import Akuto2.Blocks.ItemBlockDoublingTable;
import Akuto2.Tiles.TileEntityDoublingFurnace;
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

	@SubscribeEvent
	public static void registerBlock(Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		doublingTable = new BlockDoublingTable();
		doublingFurnace = new BlockDoublingFurnace(false).setRegistryName("doublingtable", "doublingfurnace").setUnlocalizedName("DoublingFurnace").setCreativeTab(DoublingTable.tabs);
		doublingFurnace_On = new BlockDoublingFurnace(true).setRegistryName("doublingtable", "doublingfurnaceon").setUnlocalizedName("DoublingFurnaceOn");

		registry.register(doublingTable);
		registry.register(doublingFurnace);
		registry.register(doublingFurnace_On);
	}

	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.register(new ItemBlockDoublingTable(doublingTable).setRegistryName(doublingTable.getRegistryName()));
		registry.register(new ItemBlockDoublingFurnace(doublingFurnace).setRegistryName(doublingFurnace.getRegistryName()));
		registry.register(new ItemBlockDoublingFurnace(doublingFurnace_On).setRegistryName(doublingFurnace_On.getRegistryName()));
	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, new ResourceLocation("doublingtable", "doublingfurnace"));
	}
}
