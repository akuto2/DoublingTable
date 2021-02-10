package akuto2.doublingtable.tile;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityEnchantmentTableMk2 extends TileEntity {
	public int tickCount;
	public float pageFlip;
	public float pageFlipPrev;
	public float flipT;
	public float flipA;
	public float bookSpread;
	public float bookSpreadPrev;
	public float bookRotation;
	public float bookRotationPrev;
	public float tRot;
	private static Random rand = new Random();

	@Override
	public void updateEntity() {
		super.updateEntity();
		bookSpreadPrev = bookSpread;
		bookRotationPrev = bookRotation;
		EntityPlayer player = worldObj.getClosestPlayer((float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, 3.0D);

		if(player != null) {
			double d0 = player.posX - (xCoord + 0.5F);
			double d1 = player.posZ - (zCoord + 0.5F);
			tRot = (float)Math.atan2(d1, d0);
			bookSpread += 0.1F;

			if(bookSpread < 0.5F || rand.nextInt(40) == 0) {
				float f1 = flipT;

				while(true) {
					flipT += (float)(rand.nextInt(4) - rand.nextInt(4));

					if(f1 != flipT) {
						break;
					}
				}
			}
		}
		else {
			tRot += 0.02F;
			bookSpread -= 0.1F;
		}

		while(bookRotation >= Math.PI) {
			bookRotation -= Math.PI * 2F;
		}

		while(bookRotation < -Math.PI) {
			bookRotation += Math.PI * 2F;
		}

		while(tRot >= Math.PI) {
			tRot -= Math.PI * 2F;
		}

		while(tRot < -Math.PI) {
			tRot += Math.PI * 2F;
		}

		float f2;

		for(f2 = tRot - bookRotation; f2 >= Math.PI; f2 -= Math.PI * 2F) {}

		while(f2 < -Math.PI) {
			f2 += Math.PI * 2F;
		}

		bookRotation += f2 * 0.4F;
		bookSpread = MathHelper.clamp_float(bookSpread, 0.0F, 1.0F);
		++tickCount;
		pageFlipPrev = pageFlip;
		float f = (flipT - pageFlip) * 0.4F;
		f = MathHelper.clamp_float(f, -0.2F, 0.2F);
		flipA += (f - flipA) * 0.9F;
		pageFlip += flipA;
	}
}
