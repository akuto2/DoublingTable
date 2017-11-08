package Akuto2.proxy;

import net.minecraft.world.World;
import Akuto2.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;

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
