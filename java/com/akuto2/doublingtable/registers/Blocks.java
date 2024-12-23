package com.akuto2.doublingtable.registers;

import com.akuto2.akutolib.registration.object.BlockRegistryObject;
import com.akuto2.akutolib.registration.register.BlockDeferredRegister;
import com.akuto2.doublingtable.DoublingTable;
import com.akuto2.doublingtable.blocks.BlockDoublingTable;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class Blocks {
	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(DoublingTable.MODID);
	
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_WOOD = registerDoublingTable(EnumFacilityType.WOOD); 
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_STONE = registerDoublingTable(EnumFacilityType.STONE);
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_IRON = registerDoublingTable(EnumFacilityType.IRON);
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_GOLD = registerDoublingTable(EnumFacilityType.GOLD);
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_DIAMOND = registerDoublingTable(EnumFacilityType.DIAMOND);
	public static final BlockRegistryObject<BlockDoublingTable, BlockItem> DOUBLING_TABLE_EMERALD = registerDoublingTable(EnumFacilityType.EMERALD);

	public static BlockRegistryObject<BlockDoublingTable, BlockItem> registerDoublingTable(EnumFacilityType type) {
		return BLOCKS.register("doublingtable_" + type.toString(), () -> new BlockDoublingTable(type, Properties.of().mapColor(MapColor.WOOD).strength(2.5F).sound(SoundType.WOOD)));
	}
}
