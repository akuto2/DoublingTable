package akuto2.blocks;

import java.util.List;

import akuto2.DoublingTable;
import akuto2.utils.Utils.EnumFacilityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDoublingTable extends Block {
	public static final PropertyEnum<EnumFacilityTypes> TYPE = PropertyEnum.create("type", EnumFacilityTypes.class);

	public BlockDoublingTable() {
		super(Material.WOOD);
		setUnlocalizedName("DoublingTable");
		setCreativeTab(DoublingTable.tabs);
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
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for(EnumFacilityTypes types : EnumFacilityTypes.values()) {
			list.add(new ItemStack(itemIn, 1, types.getMeta()));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) {
			return true;
		}
		playerIn.openGui(DoublingTable.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
