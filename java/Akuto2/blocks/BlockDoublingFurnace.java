package Akuto2.Blocks;

import java.util.Random;

import Akuto2.DoublingTable;
import Akuto2.ObjManager;
import Akuto2.Tiles.TileEntityDoublingFurnace;
import Akuto2.Utils.EnumUtils.EnumFacilityTypes;
import Akuto2.Utils.Utils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDoublingFurnace extends BlockContainer {
	public static final PropertyEnum<EnumFacilityTypes> TYPE = PropertyEnum.create("type", EnumFacilityTypes.class);
	private static boolean isBurning;
	private final boolean isActive;

	public BlockDoublingFurnace(boolean isActive) {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumFacilityTypes.wood));
		this.isActive = isActive;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ObjManager.doublingFurnace);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumFacilityTypes.fromMeta(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(DoublingTable.instance, Utils.GUI_DOUBLINGFURNACE_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDoublingFurnace();
	}

	public static void updateBlockState(boolean burning, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		TileEntity tileEntity = world.getTileEntity(pos);
		isBurning = true;

		if(burning) {
			world.setBlockState(pos, ObjManager.doublingFurnace_On.getDefaultState().withProperty(TYPE, state.getValue(TYPE)), 3);
		}
		else {
			world.setBlockState(pos, ObjManager.doublingFurnace.getDefaultState().withProperty(TYPE, state.getValue(TYPE)), 3);
		}

		isBurning = false;

		if(tileEntity != null) {
			tileEntity.validate();
			world.setTileEntity(pos, tileEntity);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!isBurning) {
			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof TileEntityDoublingFurnace) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityDoublingFurnace)tile);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumFacilityTypes types : EnumFacilityTypes.values()) {
			items.add(new ItemStack(this, 1, types.getMeta()));
		}
	}
}
