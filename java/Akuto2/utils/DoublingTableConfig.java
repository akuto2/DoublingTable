package Akuto2.utils;

import Akuto2.blocks.BlockDoublingFurnace;
import Akuto2.blocks.BlockDoublingTable;
import Akuto2.enchantments.DoublingTableEnchantments;
import Akuto2.item.ItemDoublingCraftRod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class DoublingTableConfig {
	public static Configuration config;
	public static int DoublingAmountTable;
	public static int DoublingAmountFurnace;
	public static int DoublingAmountRod;

	public static void initConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile(), true);
		syncConfig();
	}

	public static void syncConfig() {
		DoublingAmountTable = config.getInt("DoublingAmountTable", "Doubling", 1, 1, 100, "Set Redstone Doubling Table magnification", "config.doublingtable.prop.DoublingAmountTable");
		DoublingAmountFurnace = config.getInt("DoublingAmountFurnace", "Doubling", 1, 1, 100, "Set Redstone Doubling Furnace magnification", "config.doublingtable.prop.DoublingAmountFurnace");
		DoublingAmountRod = config.getInt("DoublingAmountRod", "Doubling", 1, 1, 100, "Set Redstone Doubling Craft Rod magnification", "config.doublingtable.prop.DoublingAmountRod");

		DoublingTableEnchantments.mendingID = config.get("Enchants", "Mending", 100).getInt();

		config.save();
		changedValue();
	}

	public static void changedValue() {
		BlockDoublingTable.times[7] = DoublingAmountTable;
		BlockDoublingFurnace.times[7] = DoublingAmountFurnace;
		ItemDoublingCraftRod.times[7] = DoublingAmountRod;
	}
}
