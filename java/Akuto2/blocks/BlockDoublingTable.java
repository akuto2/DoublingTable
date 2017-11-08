package Akuto2.blocks;

import java.util.List;

import Akuto2.DoublingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDoublingTable extends BlockDirectional{
	public static final String[] type = new String[] {"wood", "stone", "iron", "gold", "diamond", "emerald"};
	public static final int[] times = new int[] {2, 4, 8, 16, 32, 64};
	@SideOnly(Side.CLIENT)
	private IIcon[] top;
	@SideOnly(Side.CLIENT)
	private IIcon[] side;
	@SideOnly(Side.CLIENT)
	private IIcon[] bottom;

	public BlockDoublingTable(){
		super(Material.wood);
		this.setCreativeTab(DoublingTable.tabDoublingTable);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2){
		if(par1 == 0){
			return bottom[par2];
		}
		else if (par1 == 1){
			return top[par2];
		}
		else{
			return side[par2];
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		entityplayer.openGui(DoublingTable.instance, 0, world, posX, posY, posZ);
		return true;
	}


    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("unchecked")
	public void getSubBlocks(Item par1Item, CreativeTabs par2CreativeTabs, List par3List){
		for(int i = 0; i < type.length; ++i){
			par3List.add(new ItemStack(par1Item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IIconRegister){
		this.top = new IIcon[type.length];
		this.side = new IIcon[type.length];
		this.bottom = new IIcon[type.length];

		for(int i = 0; i < type.length; i++){
			this.side[i] = par1IIconRegister.registerIcon("doublingtable:doublingtable_" + type[i]);
			this.top[i] = par1IIconRegister.registerIcon("doublingtable:doublingtable_" + type[i] + "_top");
			this.bottom[i] = par1IIconRegister.registerIcon("doublingtable:doublingtable_" + type[i] + "_bottom");
		}
	}

}
