package akuto2;

import akuto2.blocks.BlockDoublingTable;
import akuto2.blocks.ItemBlockDoublingTable;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ObjHandler {
	public static Block doublingTable;

	public static void register() {
		doublingTable = new BlockDoublingTable();
		GameRegistry.register(doublingTable);
		GameRegistry.register(new ItemBlockDoublingTable(doublingTable).setRegistryName(doublingTable.getRegistryName()));
	}
}
