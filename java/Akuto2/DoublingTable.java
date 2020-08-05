package Akuto2;

import Akuto2.Gui.GuiHandler;
import Akuto2.Proxies.CommonProxy;
import Akuto2.Utils.CreativeTabDoublingTable;
import Akuto2.Utils.DoublingTableConfig;
import Akuto2.Utils.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = "doublingtable", name = "DoublingTable", version = "3.0.2", guiFactory = "Akuto2.Utils.DoublingTableGuiFactory")
@Mod.EventBusSubscriber(modid = "doublingtable")
public class DoublingTable {
	@Instance("doublingtable")
	public static DoublingTable instance;
	@Metadata("doublingtable")
	public static ModMetadata meta;
	@SidedProxy(serverSide = "Akuto2.Proxies.CommonProxy", clientSide = "Akuto2.Proxies.ClientProxy")
	public static CommonProxy proxy;
	public static final CreativeTabs tabs = new CreativeTabDoublingTable("DoublingTable");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModInfo.registerInfo(meta);
		DoublingTableConfig.initConfig(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(DoublingTable.instance, new GuiHandler());
		ObjManager.registerTile();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

	}
}
