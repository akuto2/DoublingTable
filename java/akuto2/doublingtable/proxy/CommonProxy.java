package akuto2.doublingtable.proxy;

import akuto2.doublingtable.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;

public class CommonProxy {
	public World getClientWorld(){
		return null;
	}

	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "TileDoubilngFurnace");
	}
}
