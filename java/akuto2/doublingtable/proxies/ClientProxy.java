package akuto2.doublingtable.proxies;

import akuto2.doublingtable.ObjManager;
import akuto2.doublingtable.items.ItemCompressedEXPCore;
import akuto2.doublingtable.utils.Utils.EnumFacilityTypes;
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
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingTable), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingtable/doublingtable_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingfurnace/doublingfurnace_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ObjManager.doublingFurnace_On), types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingfurnace/doublingfurnaceon_" + types.getName()), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ObjManager.doublingCraftRod, types.getMeta(), new ModelResourceLocation(new ResourceLocation("doublingtable", "doublingcraftrod/doublingcraftrod_" + types.getName()), "inventory"));
		}

		ModelLoader.setCustomModelResourceLocation(ObjManager.craftRod, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "craftrod"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost1, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost1"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost2, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost2"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost3, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost3"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost4, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost4"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost5, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost5"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost6, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost6"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost7, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost7"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost8, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost8"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost9, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost9"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost10, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost10"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.expBoost11, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/expboost11"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(ObjManager.uexpBoost, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expboost/uexpboost"), "inventory"));

		ModelLoader.setCustomModelResourceLocation(ObjManager.expCore, 0, new ModelResourceLocation(new ResourceLocation("doublingtable", "expcore"), "inventory"));
		for(int i = 0; i < ItemCompressedEXPCore.times.length; i++) {
			ModelLoader.setCustomModelResourceLocation(ObjManager.compressedExpCore, i, new ModelResourceLocation(new ResourceLocation("doublingtable", "compressedexpcore" + i), "inventory"));
		}
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}
}
