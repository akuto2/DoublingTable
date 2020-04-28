package akuto2.gui;

import akuto2.blocks.BlockDoublingTable;
import akuto2.tiles.TileEntityDoublingFurnace;
import akuto2.utils.Utils.EnumFacilityTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);

		if(ID == 0) {
			return new ContainerDoublingTable(player.inventory, world, pos, ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		if(ID == 1) {
			return new ContainerDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)world.getTileEntity(pos), ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);

		if(ID == 0) {
			return new GuiDoublingTable(player.inventory, world, pos, ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		if(ID == 1) {
			return new GuiDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)world.getTileEntity(pos), ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		return null;
	}
}
