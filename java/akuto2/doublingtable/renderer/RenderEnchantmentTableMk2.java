package akuto2.doublingtable.renderer;

import org.lwjgl.opengl.GL11;

import akuto2.doublingtable.tile.TileEntityEnchantmentTableMk2;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEnchantmentTableMk2 extends TileEntitySpecialRenderer{
	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
	private ModelBook modelBook = new ModelBook();

	public void renderTileEntityAt(TileEntityEnchantmentTableMk2 enchantmentTableMk2, double x, double y, double z, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
		float f1 = (float)enchantmentTableMk2.tickCount + partialTicks;
		GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
		float f2;

		for(f2 = enchantmentTableMk2.bookRotation - enchantmentTableMk2.bookRotationPrev; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
			;
		}

		while(f2 < -(float)Math.PI) {
			f2 += ((float)Math.PI * 2F);
		}

		float f3 = enchantmentTableMk2.bookRotationPrev + f2 * partialTicks;
		GL11.glRotatef(-f3 * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);
		bindTexture(TEXTURE_BOOK);
		float f4 = enchantmentTableMk2.pageFlipPrev + (enchantmentTableMk2.pageFlip - enchantmentTableMk2.pageFlipPrev) * partialTicks + 0.25F;
		float f5 = enchantmentTableMk2.pageFlipPrev + (enchantmentTableMk2.pageFlip - enchantmentTableMk2.pageFlipPrev) * partialTicks + 0.75F;
		f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
		f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;

		f4 = MathHelper.clamp_float(f4, 0.0F, 1.0F);
		f5 = MathHelper.clamp_float(f5, 0.0F, 1.0F);

		float f6 = enchantmentTableMk2.bookSpreadPrev + (enchantmentTableMk2.bookSpread - enchantmentTableMk2.bookSpreadPrev) * partialTicks;
		GL11.glEnable(GL11.GL_CULL_FACE);
		modelBook.render(null, f1, f4, f5, f6, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
		renderTileEntityAt((TileEntityEnchantmentTableMk2)tile, x, y, z, partialTicks);
	}
}
