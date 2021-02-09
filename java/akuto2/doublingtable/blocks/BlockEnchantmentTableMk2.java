package akuto2.doublingtable.blocks;

import java.util.Random;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.tile.TileEntityEnchantmentTableMk2;
import akuto2.doublingtable.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEnchantmentTableMk2 extends BlockContainer{
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;

	public BlockEnchantmentTableMk2() {
		super(Material.rock);
		setCreativeTab(DoublingTable.tabDoublingTable);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityEnchantmentTableMk2();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return null;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {

	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase base, ItemStack stack) {

	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(DoublingTable.instance, Utils.GUI_ENCHANTMENTTABLE_MK2_ID, world, x, y, z);
		}
		return true;
	}
}
