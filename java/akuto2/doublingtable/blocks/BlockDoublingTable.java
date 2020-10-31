package akuto2.doublingtable.blocks;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.EnumUtils.EnumFacilityTypes;
import akuto2.doublingtable.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDoublingTable extends Block {
	public static final PropertyEnum<EnumFacilityTypes> TYPE = PropertyEnum.create("type", EnumFacilityTypes.class);

	public BlockDoublingTable() {
		super(Material.WOOD);
		setCreativeTab(DoublingTable.tabs);
		setHardness(2.5F);
		setUnlocalizedName("doublingTable");
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumFacilityTypes.wood));
		setRegistryName("doublingtable", "doublingtable");
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((EnumFacilityTypes)state.getValue(TYPE)).getMeta();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacilityTypes)state.getValue(TYPE)).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumFacilityTypes.fromMeta(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumFacilityTypes type : EnumFacilityTypes.values()) {
			items.add(new ItemStack(this, 1, type.getMeta()));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(DoublingTable.instance, Utils.GUI_DOUBLINGTABLE_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
