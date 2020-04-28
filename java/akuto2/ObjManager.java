package akuto2;

import akuto2.blocks.BlockDoublingFurnace;
import akuto2.blocks.BlockDoublingTable;
import akuto2.blocks.ItemBlockDoublingFurnace;
import akuto2.blocks.ItemBlockDoublingTable;
import akuto2.tiles.TileEntityDoublingFurnace;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ObjManager {
	public static Block doublingTable;
	public static Block doublingFurnace;
	public static Block doublingFurnace_On;

	public static void register() {
		doublingTable = new BlockDoublingTable();
		GameRegistry.register(doublingTable);
		GameRegistry.register(new ItemBlockDoublingTable(doublingTable).setRegistryName(doublingTable.getRegistryName()));

		doublingFurnace = new BlockDoublingFurnace(false).setRegistryName("doublingtable", "doublingfurnace").setUnlocalizedName("DoublingFurnace").setCreativeTab(DoublingTable.tabs);
		GameRegistry.register(doublingFurnace);
		GameRegistry.register(new ItemBlockDoublingFurnace(doublingFurnace).setRegistryName(doublingFurnace.getRegistryName()));
		doublingFurnace_On = new BlockDoublingFurnace(true).setRegistryName("doublingtable", "doublingfurnaceon").setUnlocalizedName("DoublingFurnaceOn");
		GameRegistry.register(doublingFurnace_On);
		GameRegistry.register(new ItemBlockDoublingFurnace(doublingFurnace_On).setRegistryName(doublingFurnace_On.getRegistryName()));
	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "tile.doublingFurance");
	}
}
