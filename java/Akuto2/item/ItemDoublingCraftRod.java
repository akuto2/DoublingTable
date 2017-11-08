package Akuto2.item;

import java.util.List;

import Akuto2.DoublingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDoublingCraftRod extends Item {
	public static final String[] type = new String[] {"wood", "stone", "iron", "gold", "diamond", "emerald"};
	public static final int[] times = new int[] {2, 4, 8, 16, 32, 64};
	@SideOnly(Side.CLIENT)
	private IIcon[] iicon;

	public ItemDoublingCraftRod(){
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.iicon = new IIcon[type.length];

		for (int i = 0; i < type.length; i++) {
			this.iicon[i] = iconRegister.registerIcon(this.getIconString()+ "_" + type[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return iicon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for (int i = 0; i < type.length; i ++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + type[itemStack.getItemDamage()];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
		player.openGui(DoublingTable.instance, 3, world, player.chunkCoordX, player.chunkCoordY, player.chunkCoordZ);
    	return item;
    }
}
