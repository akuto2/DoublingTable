package Akuto2;

import java.util.logging.Logger;

import Akuto2.Gui.GuiHandler;
import Akuto2.Proxies.CommonProxy;
import Akuto2.Utils.CreativeTabDoublingTable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = "doublingtable", name = "DoublingTable", version = "3.0.0")
@Mod.EventBusSubscriber(modid = "doublingtable")
public class DoublingTable {
	@Instance("doublingtable")
	public static DoublingTable instance;
	@SidedProxy(serverSide = "Akuto2.Proxies.CommonProxy", clientSide = "Akuto2.Proxies.ClientProxy")
	public static CommonProxy proxy;
	public static final CreativeTabs tabs = new CreativeTabDoublingTable("DoublingTable");

	public static Logger logger = Logger.getLogger("DoublingTable");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(DoublingTable.instance, new GuiHandler());
		ObjManager.registerTile();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

	}
}
