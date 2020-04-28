package akuto2.gui;

import org.lwjgl.opengl.GL11;

import akuto2.tiles.TileEntityDoublingFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDoublingFurnace extends GuiContainer{
	private TileEntityDoublingFurnace tile;
	private final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
	private int magnification;

	public GuiDoublingFurnace(InventoryPlayer player, TileEntityDoublingFurnace tile, int type) {
		super(new ContainerDoublingFurnace(player, tile, type));
		this.tile = tile;
		magnification = type;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("container.furnace", new Object[0]), 28, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
		fontRendererObj.drawString(magnification + "x", 120, 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.getTextureManager().bindTexture(TEXTURE);

		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		int i;

		if(tile.isBurning()) {
			i = tile.getBurnTimeRemainScaled(12);
			drawTexturedModalRect(k + 56, l + 36 + 12 - i, 176, 12 - i, 14, i + 2);
		}
		i = tile.getCookProgressScaled(24);
		drawTexturedModalRect(k + 79, l + 34, 176, 14, i + 1, 16);
	}
}
