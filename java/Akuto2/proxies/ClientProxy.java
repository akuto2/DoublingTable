package akuto2.proxies;

import akuto2.ObjManager;
import akuto2.utils.Utils.EnumFacilityTypes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy  extends CommonProxy{

	@Override
	public void preInit() {
		for(EnumFacilityTypes types : EnumFacilityTypes.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingTable), types.getMeta(), new ModelResourceLocation(new ResourceLocation("DoublingTable", "doublingtable_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace), types.getMeta(), new ModelResourceLocation(new ResourceLocation("DoublingTable", "doublingfurnace_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace_On), types.getMeta(), new ModelResourceLocation(new ResourceLocation("DoublingTable", "doublingfurnaceon_" + types.getName()), "inventory"));
		}
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}
}
