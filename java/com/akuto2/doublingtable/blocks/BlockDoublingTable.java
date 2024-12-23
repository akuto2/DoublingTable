package com.akuto2.doublingtable.blocks;

import com.akuto2.doublingtable.menu.MenuDoublingTable;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockDoublingTable extends Block {
	private final EnumFacilityType type;
	
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");
	
	public BlockDoublingTable(EnumFacilityType type, Properties properties) {
		super(properties);
		this.type = type;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(level, pos));
			return InteractionResult.CONSUME;
		}
	}
	
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((id, playerInventory, player) -> {
			switch (type) {
			case STONE:
				return new MenuDoublingTable.Stone(id, playerInventory, ContainerLevelAccess.create(level, pos));
			case IRON:
				return new MenuDoublingTable.Iron(id, playerInventory, ContainerLevelAccess.create(level, pos));
			case GOLD:
				return new MenuDoublingTable.Gold(id, playerInventory, ContainerLevelAccess.create(level, pos));
			case DIAMOND:
				return new MenuDoublingTable.Diamond(id, playerInventory, ContainerLevelAccess.create(level, pos));
			case EMERALD:
				return new MenuDoublingTable.Emerald(id, playerInventory, ContainerLevelAccess.create(level, pos));
			default:
				return new MenuDoublingTable.Wood(id, playerInventory, ContainerLevelAccess.create(level, pos));
			}
		}, CONTAINER_TITLE);
	}
}
