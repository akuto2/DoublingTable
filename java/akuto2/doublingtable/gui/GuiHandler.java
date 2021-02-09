package akuto2.doublingtable.gui;

import akuto2.doublingtable.blocks.BlockDoublingFurnace;
import akuto2.doublingtable.blocks.BlockDoublingTable;
import akuto2.doublingtable.item.ItemDoublingCraftRod;
import akuto2.doublingtable.tile.TileEntityDoublingFurnace;
import akuto2.doublingtable.tile.TileEntityEnchantmentTableMk2;
import akuto2.doublingtable.utils.Utils;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(!world.blockExists(x, y, z)){
			return null;
		}
		if(ID == Utils.GUI_DOUBLINGTABLE_ID){
			return new ContainerDoublingTable(player.inventory, world, x, y, z, BlockDoublingTable.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == Utils.GUI_DOUBLINGFURNACE_ID){
			return new ContainerDoublingFurnace(player.inventory, (TileEntityDoublingFurnace) tileentity, BlockDoublingFurnace.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == Utils.GUI_CRAFTROD_ID){
			return new ContainerCraftRod(player.inventory, world, x, y, z);
		}
		if(ID == Utils.GUI_DOUBLINGCRAFTROD_ID){
			return new ContainerDoublingCraftRod(player.inventory, world, x, y, z, ItemDoublingCraftRod.times[player.getHeldItem().getItemDamage()]);
		}
		if(ID == Utils.GUI_ENCHANTMENTTABLE_MK2_ID) {
			return new ContainerEnchantmentTableMk2(player.inventory, (TileEntityEnchantmentTableMk2)tileentity, world, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(!world.blockExists(x, y, z)){
			return null;
		}
		if(ID == Utils.GUI_DOUBLINGTABLE_ID){
			return new GuiDoublingTable(player.inventory, world, x, y, z, BlockDoublingTable.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == Utils.GUI_DOUBLINGFURNACE_ID){
			return new GuiDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)tileentity, BlockDoublingFurnace.times[world.getBlockMetadata(x, y, z)]);
		}
		if(ID == Utils.GUI_CRAFTROD_ID){
			return new GuiCrafting(player.inventory, world, x, y, z);
		}
		if(ID == Utils.GUI_DOUBLINGCRAFTROD_ID){
			return new GuiDoublingTable(player.inventory, world, x, y, z, ItemDoublingCraftRod.times[player.getHeldItem().getItemDamage()]);
		}
		if(ID == Utils.GUI_ENCHANTMENTTABLE_MK2_ID) {
			return new GuiEnchantmentTableMk2(player.inventory, (TileEntityEnchantmentTableMk2)tileentity, world, x, y, z);
		}
		return null;
	}

}
