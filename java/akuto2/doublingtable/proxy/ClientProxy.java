package akuto2.doublingtable.proxy;

import akuto2.doublingtable.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy{
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "TileDoubilngFurnace");
	}
}
