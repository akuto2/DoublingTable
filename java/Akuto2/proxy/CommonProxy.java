package Akuto2.proxy;

import net.minecraft.world.World;
import Akuto2.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public World getClientWorld(){
		return null;
	}

	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "TileDoubilngFurnace");
	}
}
