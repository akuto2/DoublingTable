package akuto2.doublingtable.gui;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiEnchantmentTableMk2 extends GuiContainer{
	/** GUIのテクスチャ */
	private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("doublingtable:textures/gui/container/enchanting_table.png");
	/** エンチャントテーブルの上にある本のテクスチャ */
	private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
	/** エンチャントテーブルの上にある本のモデルデータ */
	private static final ModelBook MODEL_BOOK = new ModelBook();
	private final InventoryPlayer inventoryPlayer;
	private final Random random = new Random();
	private final ContainerEnchantmentTableMk2 container;
	public int ticks;
	public float flip;
	public float oFlip;
	public float flipT;
	public float flipA;
	public float open;
	public float oOpen;
	private ItemStack last;
	private int tier;

	public GuiEnchantmentTableMk2(InventoryPlayer inventoryPlayer, World world, int posX, int posY, int posZ) {
		super(new ContainerEnchantmentTableMk2(inventoryPlayer, world, posX, posY, posZ));
		this.inventoryPlayer = inventoryPlayer;
		container = (ContainerEnchantmentTableMk2)inventorySlots;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("container.enchant", new Object[0]), 12, 5, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		tickBook();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;

		for(int k = 0; k < 3; ++k) {
			int l = mouseX - (i + 60);
			int m = mouseY - (j + 14 + 19 * k);

			if(l >= 0 && m >= 0 && l < 108 && m < 19 && container.enchantItem(mc.thePlayer, k)) {
				mc.playerController.sendEnchantPacket(container.windowId, k);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution scaledResolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		GL11.glViewport((scaledResolution.getScaledWidth() - 320) / 2 * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - 240) / 2 * scaledResolution.getScaleFactor(), 320 * scaledResolution.getScaleFactor(), 240 * scaledResolution.getScaleFactor());
		GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
		Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
		float f1 = 1.0F;
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslatef(0.0F, 3.3F, -16.0F);
		GL11.glScalef(f1, f1, f1);
		float f2 = 5.0F;
		GL11.glScalef(f2, f2, f2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
		GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
		float f3 = oOpen + (open - oOpen) * partialTicks;
		GL11.glTranslatef((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
		GL11.glRotatef(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		float f4 = oFlip + (flip - oFlip) * partialTicks + 0.25F;
		float f5 = oFlip + (flip - oFlip) * partialTicks + 0.75F;
		f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
		f5 = (f5 - MathHelper.truncateDoubleToInt(f5)) * 1.6F - 0.3F;

		f4 = MathHelper.clamp_float(f4, 0.0F, 1.0F);
		f5 = MathHelper.clamp_float(f5, 0.0F, 1.0F);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		MODEL_BOOK.render(null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		EnchantmentNameParts.instance.reseedRandomGenerator(container.nameSeed);

		for(int i = 0; i < 3; i++) {
			int i1 = x + 60;
			zLevel = 0.0F;
			mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
			int j1 = container.enchantLevels[i];
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if(j1 == 0) {
				drawTexturedModalRect(i1, y + 14 + 19 * i, 0, 185, 108, 19);
			}
			else {
				String s = "" + j1;
				String s1 = EnchantmentNameParts.instance.generateNewRandomName();
				FontRenderer fontRenderer = mc.standardGalacticFontRenderer;
				int i2 = 6839882;

				if(mc.thePlayer.experienceLevel < j1 && !mc.thePlayer.capabilities.isCreativeMode) {
					drawTexturedModalRect(i1, y + 14 + 19 * i, 0, 185, 108, 19);
					fontRenderer.drawSplitString(s1, i1 + 2, y + 16 + 19 * i, 104, (i2 & 16711422) >> 1);
					i2 = 4226832;
				}
				else {
					int x2 = mouseX - (x + 60);
					int y2 = mouseY - (y + 14 + 19 * i);

					if(x2 >= 0 && y2 >= 0 && x2 < 108 && y2 < 19) {
						drawTexturedModalRect(i1, y + 14 + 19 * i, 0, 204, 108, 19);
						i2 = 16777088;
					}
					else {
						drawTexturedModalRect(i1, y + 14 + 19 * i, 0, 166, 108, 19);
					}

					fontRenderer.drawSplitString(s1, i1 + 2, y + 16 + 19 * i, 104, i2);
					i2 = 8453920;
				}
				fontRenderer = mc.fontRenderer;
				fontRenderer.drawStringWithShadow(s, i1 + 2 + 104 - fontRenderer.getStringWidth(s), y + 16 + 19 * i + 7, i2);
			}
		}
	}

	public void tickBook() {
		ItemStack stack = inventorySlots.getSlot(0).getStack();

		if(!ItemStack.areItemStacksEqual(stack, last)) {
			last = stack;

			while(true) {
				flipT += (float)(random.nextInt(4) - random.nextInt(4));

				if(flip > flipT + 1.0F || flip < flipT - 1.0F) {
					break;
				}
			}
		}

		++ticks;
		oFlip = flip;
		oOpen = open;
		boolean flag = false;

		for(int i = 0; i < 3; i++) {
			if(container.enchantLevels[i] != 0) {
				flag = true;
			}
		}

		if(flag) {
			open += 0.2F;
		}
		else {
			open -= 0.2F;
		}

		open = MathHelper.clamp_float(open, 0.0F, 1.0F);
		float f1 = (flipT - flip) * 0.4F;
		float f = 0.2F;
		f1 = MathHelper.clamp_float(f1, -0.2F, 0.2F);
		flipA += (f1 - flipA) * 0.9F;
		flip += flipA;
	}
}
