package Akuto2.Gui;

import Akuto2.Blocks.BlockDoublingTable;
import Akuto2.Tiles.TileEntityDoublingFurnace;
import Akuto2.Utils.EnumUtils.EnumFacilityTypes;
import Akuto2.Utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);

		if(ID == Utils.GUI_DOUBLINGTABLE_ID) {
			return new ContainerDoublingTable(player.inventory, world, pos, ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		if(ID == Utils.GUI_DOUBLINGFURNACE_ID) {
			return new ContainerDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)world.getTileEntity(pos), ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);

		if(ID == Utils.GUI_DOUBLINGTABLE_ID) {
			return new GuiDoublingTable(player.inventory, world, pos, ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		if(ID == Utils.GUI_DOUBLINGFURNACE_ID) {
			return new GuiDoublingFurnace(player.inventory, (TileEntityDoublingFurnace)world.getTileEntity(pos), ((EnumFacilityTypes)world.getBlockState(pos).getValue(BlockDoublingTable.TYPE)).getTimes());
		}
		return null;
	}
}
