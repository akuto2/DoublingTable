package com.akuto2.doublingtable.blocks;

import com.akuto2.akutolib.helper.WorldHelper;
import com.akuto2.akutolib.registration.register.BlockEntityRegistryObject;
import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.registers.DTBlockEntities;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
	
	private BlockEntityRegistryObject<? extends BlockEntityDoublingFurnace> getBlockEntityRegistryObject() {
		switch(type) {
		case STONE:
			return DTBlockEntities.DOUBLING_FURNACE_STONE;
		case IRON:
			return DTBlockEntities.DOUBLING_FURNACE_IRON;
		case GOLD:
			return DTBlockEntities.DOUBLING_FURNACE_GOLD;
		case DIAMOND:
			return DTBlockEntities.DOUBLING_FURNACE_DIAMOND;
		case EMERALD:
			return DTBlockEntities.DOUBLING_FURNACE_EMERALD;
		default:
			return DTBlockEntities.DOUBLING_FURNACE_WOOD;
		}
	}

	@Override
	protected void openContainer(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide) {
			BlockEntityDoublingFurnace blockEntity = WorldHelper.getBlockEntity(BlockEntityDoublingFurnace.class, level, pos);
			if (blockEntity != null && type == ((BlockEntityDoublingFurnace)blockEntity).getFacilityType()) {
				NetworkHooks.openScreen((ServerPlayer)player, blockEntity, pos);
			}
		}
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
		BlockEntityRegistryObject<? extends BlockEntityDoublingFurnace> furnace = getBlockEntityRegistryObject();
		if (furnace != null && blockEntity == furnace.get()) {
			return (BlockEntityTicker<T>)furnace.getTicker(level.isClientSide());
		}
		return null;
	}
}
