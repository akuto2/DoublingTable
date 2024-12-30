package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.register.BlockEntityDeferredRegister;
import com.akuto2.akutolib.registration.register.BlockEntityRegistryObject;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;

public class DTBlockEntities {
	public static final BlockEntityDeferredRegister BLOCK_ENTITY = new BlockEntityDeferredRegister(DoublingTable.MODID);
	
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Wood> DOUBLING_FURNACE_WOOD = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_WOOD, BlockEntityDoublingFurnace.Wood::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Stone> DOUBLING_FURNACE_STONE = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_STONE, BlockEntityDoublingFurnace.Stone::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Iron> DOUBLING_FURNACE_IRON = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_IRON, BlockEntityDoublingFurnace.Iron::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Gold> DOUBLING_FURNACE_GOLD = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_GOLD, BlockEntityDoublingFurnace.Gold::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Diamond> DOUBLING_FURNACE_DIAMOND = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_DIAMOND, BlockEntityDoublingFurnace.Diamond::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
	public static final BlockEntityRegistryObject<BlockEntityDoublingFurnace.Emerald> DOUBLING_FURNACE_EMERALD = BLOCK_ENTITY.builder(DTBlocks.DOUBLING_FURNACE_EMERALD, BlockEntityDoublingFurnace.Emerald::new).serverTicker(BlockEntityDoublingFurnace::serverTick).build();
}
