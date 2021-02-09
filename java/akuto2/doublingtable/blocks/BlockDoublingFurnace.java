package akuto2.doublingtable.blocks;

import java.util.List;
import java.util.Random;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.tile.TileEntityDoublingFurnace;
import akuto2.doublingtable.utils.DoublingTableConfig;
import akuto2.doublingtable.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDoublingFurnace extends BlockContainer{
	public static final String[] type = new String[] {"wood", "stone", "iron", "gold", "diamond", "emerald", "lapis", "redstone"};
	public static final int[] times = new int[] {2, 4, 8, 16, 32, 64, 9, DoublingTableConfig.DoublingAmountFurnace};
	private static boolean isBurning;
	private final boolean isBurning2;
	public byte furnaceFacing = 0;
	private final Random random = new Random();
	@SideOnly(Side.CLIENT)
	private IIcon[] front;
	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons;

	public BlockDoublingFurnace(boolean isActive) {
		super(Material.rock);
		setHardness(5.0F);
		this.isBurning2 = isActive;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(DoublingTable.doublingFurnace);
    }

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side == 1){
			return front[meta];
		}
		else{
			return blockIcons[meta];
		}
    }


	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.front = new IIcon[type.length];
		this.blockIcons = new IIcon[type.length];

		for(int i = 0; i < type.length	; i++){
			this.front[i] = register.registerIcon("doublingtable:doublingfurnace_" + type[i] + (this.isBurning2 ? "_front_on" : "_front_off"));
			this.blockIcons[i] = register.registerIcon("doublingtable:doublingfurnace_" + type[i]);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entity, int side, float dx, float dy, float dz){
		if(world.isRemote){
			return true;
		}
		else{
			entity.openGui(DoublingTable.instance, Utils.GUI_DOUBLINGFURNACE_ID, world, x, y, z);
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityDoublingFurnace();
	}

//	@SideOnly(Side.CLIENT)
//	public void onBlockAdded(World world, int x, int y, int z) {
//		super.onBlockAdded(world, x, y, z);
//		world.markBlockForUpdate(x, y, z);
//	}
//
//	private void direction(World world, int x, int y, int z) {
//		if (!world.isRemote) {
//			Block direction = world.getBlock(x, y, z - 1);
//			Block direction1 = world.getBlock(x, y, z + 1);
//			Block direction2 = world.getBlock(x - 1, y, z);
//			Block direction3 = world.getBlock(x + 1, y, z);
//			byte byte0 = 3;
//
//			if (direction.func_149730_j() && direction.func_149730_j()) {
//				byte0 = 3;
//			}
//
//			if (direction1.func_149730_j() && direction1.func_149730_j()) {
//				byte0 = 2;
//			}
//			if (direction2.func_149730_j() && direction2.func_149730_j()) {
//				byte0 = 5;
//			}
//
//			if (direction3.func_149730_j() && direction3.func_149730_j()) {
//				byte0 = 4;
//			}
//
//			world.setBlockMetadataWithNotify(x, y, z, byte0, 2);
//		}
//	}
//
//	@Override
//	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
//		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//
//		if(direction == 0){
//			furnaceFacing = 2;
//		}
//		if(direction == 1){
//			furnaceFacing = 5;
//		}
//		if(direction == 2){
//			furnaceFacing = 3;
//		}
//		if(direction == 3){
//			furnaceFacing = 4;
//		}
//	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int notice){
			if(!isBurning){
			TileEntityDoublingFurnace tile = (TileEntityDoublingFurnace)world.getTileEntity(x, y, z);
			if(tile != null){
				for(int i = 0; i < tile.getSizeInventory(); ++i){
					ItemStack itemstack = tile.getStackInSlot(i);

					if(itemstack != null){
						float f = this.random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0){
							int j1 = this.random.nextInt(21) + 10;

							if(j1 > itemstack.stackSize){
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));


	                        if (itemstack.hasTagCompound())
	                        {
	                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
	                        }

	                        float f3 = 0.05F;
	                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
	                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
	                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
	                        world.spawnEntityInWorld(entityitem);
						}
					}
				}
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, notice);
	}

	public static void updateBlockState(boolean burning, World world, int x, int y, int z){
        int direction = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        isBurning = true;

        if(burning) {
           world.setBlock(x, y, z, DoublingTable.doublingFurnaceOn);
        } else {
           world.setBlock(x, y, z, DoublingTable.doublingFurnace);
        }

        isBurning = false;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);

        if(tileentity != null){
           tileentity.validate();
           world.setTileEntity(x, y, z, tileentity);
        }
     }

//	@SideOnly(Side.CLIENT)
//	@Override
//    public void randomDisplayTick(World world, int x, int y, int z, Random rnd)
//    {
//        if (this.isBurning2)
//        {
//        	TileEntityDoublingFurnace te = (TileEntityDoublingFurnace)world.getTileEntity(x, y, z);
//            float f = (float)x + 0.5F;
//            float f1 = (float)y + 0.0F + rnd.nextFloat() * 6.0F / 16.0F;
//            float f2 = (float)z + 0.5F;
//            float f3 = 0.52F;
//            float f4 = rnd.nextFloat() * 0.6F - 0.3F;
//
//            if (te.facing == 4)
//            {
//                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
//                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
//            }
//            else if (te.facing == 5)
//            {
//            	world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
//                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
//            }
//            else if (te.facing == 2)
//            {
//            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
//            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
//            }
//            else if (te.facing == 3)
//            {
//            	world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
//            	world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
//            }
//        }
//    }

	 @SideOnly(Side.CLIENT)
	 @Override
	 public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List arraylist) {
		 for(int i = 0; i < type.length; ++i){
				arraylist.add(new ItemStack(item, 1, i));
		 }
	}

	@Override
	public boolean hasComparatorInputOverride()
    {
        return true;
    }

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta)
    {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(x, y, z));
    }
}
