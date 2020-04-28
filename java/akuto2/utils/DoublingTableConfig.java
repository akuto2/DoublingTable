package akuto2.utils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class DoublingTableConfig {
	public static Configuration config;
	public static int redstoneAmount;

	public static void initConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile(), true);
		syncConfig();
	}

	public static void syncConfig() {
		redstoneAmount = config.getInt("RedstoneAmount", "Doubling", 1, 1, 100, "Set Redstone Facility Magnification", "config.doublingtable.prop.redstone");

		config.save();
		changedValue();
	}

	public static void changedValue() {
		Utils.redstoneAmount = redstoneAmount;
	}
}
