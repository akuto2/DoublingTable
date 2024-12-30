package com.akuto2.doublingtable.blocks;

import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class BlockDoublingFurnace extends AbstractFurnaceBlock {
	private final EnumFacilityType type;
	
	public BlockDoublingFurnace(EnumFacilityType type, Properties properties) {
		super(properties);
		this.type = type;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		switch(type) {
		case STONE:
			return new BlockEntityDoublingFurnace.Stone(pos, state);
		case IRON:
			return new BlockEntityDoublingFurnace.Iron(pos, state);
		case GOLD:
			return new BlockEntityDoublingFurnace.Gold(pos, state);
		case DIAMOND:
			return new BlockEntityDoublingFurnace.Diamond(pos, state);
		case EMERALD:
			return new BlockEntityDoublingFurnace.Emerald(pos, state);
		default:
			return new BlockEntityDoublingFurnace.Wood(pos, state);
		}
	}

	@Override
	protected void openContainer(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof BlockEntityDoublingFurnace && type == ((BlockEntityDoublingFurnace)blockEntity).getFacilityType()) {
				NetworkHooks.openScreen((ServerPlayer)player, (BlockEntityDoublingFurnace)blockEntity, pos);
			}
		}
	}
}
