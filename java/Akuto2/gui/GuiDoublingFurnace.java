package Akuto2.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import Akuto2.tile.TileEntityDoublingFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDoublingFurnace extends GuiContainer{
	private TileEntityDoublingFurnace tileentity;
	private static final ResourceLocation GUITEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
	private int magnification;

	public GuiDoublingFurnace(InventoryPlayer player, TileEntityDoublingFurnace tileentity2, int par3) {
		super(new ContainerDoublingFurnace(player, tileentity2, par3));
		this.tileentity = tileentity2;
		this.magnification = par3;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.furnace"), 28, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("containor.invantory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(this.magnification + "x", 120, 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(GUITEXTURE);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.tileentity.isBurning())
		{
			i1 = this.tileentity.getBurnTimeRemainScaled(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}
		i1 = this.tileentity.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

}
