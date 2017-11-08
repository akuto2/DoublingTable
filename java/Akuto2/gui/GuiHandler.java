package Akuto2.gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Akuto2.blocks.BlockDoublingFurnace;
import Akuto2.blocks.BlockDoublingTable;
import Akuto2.item.ItemDoublingCraftRod;
import Akuto2.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(!world.blockExists(x, y, z)){
			return null;
		}
		if(ID == 0){
			return new ContainerDoublingTable(player.inventory, world, x, y, z, BlockDoublingTable.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == 1){
			return new ContainerDoublingFurnace(player.inventory, (TileEntityDoublingFurnace) tileentity, BlockDoublingFurnace.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == 2){
			return new ContainerCraftRod(player.inventory, world, x, y, z);
		}
		if(ID == 3){
			return new ContainerDoublingCraftRod(player.inventory, world, x, y, z, ItemDoublingCraftRod.times[player.getHeldItem().getItemDamage()]);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(!world.blockExists(x, y, z)){
			return null;
		}
		if(ID == 0){
			return new GuiDoublingTable(player.inventory, world, x, y, z, BlockDoublingTable.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == 1){
			return new GuiDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)tileentity, BlockDoublingFurnace.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == 2){
			return new GuiCrafting(player.inventory, world, x, y, z);
		}
		if(ID == 3){
			return new GuiDoublingTable(player.inventory, world, x, y, z, ItemDoublingCraftRod.times[player.getHeldItem().getItemDamage()]);
		}
		return null;
	}

}
