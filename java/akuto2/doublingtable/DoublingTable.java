package akuto2.doublingtable;

import akuto2.doublingtable.gui.GuiHandler;
import akuto2.doublingtable.proxies.CommonProxy;
import akuto2.doublingtable.utils.CreativeTabDoublingTable;
import akuto2.doublingtable.utils.DoublingTableConfig;
import akuto2.doublingtable.utils.ModInfo;
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

@Mod(modid = "doublingtable", name = "DoublingTable", version = "3.0.3", guiFactory = "akuto2.doublingtable.utils.DoublingTableGuiFactory")
@Mod.EventBusSubscriber(modid = "doublingtable")
public class DoublingTable {
	@Instance("doublingtable")
	public static DoublingTable instance;
	@Metadata("doublingtable")
	public static ModMetadata meta;
	@SidedProxy(serverSide = "akuto2.doublingtable.proxies.CommonProxy", clientSide = "akuto2.doublingtable.proxies.ClientProxy")
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
