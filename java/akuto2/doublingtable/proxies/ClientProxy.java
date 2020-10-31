package akuto2.doublingtable.proxies;

import akuto2.doublingtable.ObjManager;
import akuto2.doublingtable.utils.EnumUtils.EnumFacilityTypes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = "doublingtable")
public class ClientProxy extends CommonProxy{

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for(EnumFacilityTypes types : EnumFacilityTypes.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingTable), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingtable_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingfurnace_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace_On), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingfurnaceon_" + types.getName()), "inventory"));
		}
	}

	@Override
	public World getClieWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}
}
