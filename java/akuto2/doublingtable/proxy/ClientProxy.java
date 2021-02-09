package akuto2.doublingtable.proxy;

import akuto2.doublingtable.event.TooltipEvent;
import akuto2.doublingtable.renderer.RenderEnchantmentTableMk2;
import akuto2.doublingtable.tile.TileEntityDoublingFurnace;
import akuto2.doublingtable.tile.TileEntityEnchantmentTableMk2;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityDoublingFurnace.class, "TileDoubilngFurnace");
		GameRegistry.registerTileEntity(TileEntityEnchantmentTableMk2.class, "TileEnchantmentTableMk2");
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentTableMk2.class, new RenderEnchantmentTableMk2());
	}

	@Override
	public void registerClientOnlyEvents() {
		MinecraftForge.EVENT_BUS.register(new TooltipEvent());
	}
}
