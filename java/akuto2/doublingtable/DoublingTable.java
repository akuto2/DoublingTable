package akuto2.doublingtable;

import akuto2.doublingtable.event.CommonEventHandler;
import akuto2.doublingtable.gui.GuiHandler;
import akuto2.doublingtable.proxies.CommonProxy;
import akuto2.doublingtable.utils.CreativeTabDoublingTable;
import akuto2.doublingtable.utils.DoublingTableConfig;
import akuto2.doublingtable.utils.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = "DoublingTable", name = "DoublingTable", version = "2.0.4", guiFactory = "akuto2.doublingtable.utils.DoublingTableGuiFactory")
public class DoublingTable {
	@Instance("DoublingTable")
	public static DoublingTable instance;
	@Metadata("DoublingTable")
	public static ModMetadata meta;
	@SidedProxy(serverSide = "akuto2.doublingtable.proxies.CommonProxy", clientSide = "akuto2.doublingtable.proxies.ClientProxy")
	public static CommonProxy proxy;
	public static final CreativeTabs tabs = new CreativeTabDoublingTable("DoublingTable");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModInfo.registerInfo(meta);
		DoublingTableConfig.initConfig(event);
		ObjManager.register();
		proxy.preInit();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		ObjManager.registerTile();
		RecipeManager.addRecipe();
	}
}
