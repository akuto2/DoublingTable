package akuto2.doublingtable.item;

import java.util.List;

import akuto2.doublingtable.DoublingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemCommpressedExpCore extends Item {
	public static final int[] times = {9, 81, 729, 6561, 59049, 531441};
	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	public ItemCommpressedExpCore(){
		super();
		setCreativeTab(DoublingTable.tabDoublingTable);
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.icon = new IIcon[times.length];

		for (int i = 0; i < times.length; i++) {
			this.icon[i] = iconRegister.registerIcon("doublingtable:compressedexpCore_" + times[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for (int i = 0; i < times.length; i ++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + times[itemStack.getItemDamage()];
	}
}
