package akuto2.doublingtable.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiDoublingTable extends GuiContainer{
	private static final ResourceLocation craftingGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");
	private World worldObj;
	private int PosX;
	private int PosY;
	private int PosZ;
	private int magnification;

	public GuiDoublingTable(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, int par6) {
		super(new ContainerDoublingTable(par1InventoryPlayer, par2World, par3, par4, par5, par6));
		this.worldObj = par2World;
		this.PosX = par3;
		this.PosY = par4;
		this.PosZ = par5;
		this.magnification = par6;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int ppar2) {
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.crafting"), 28, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(this.magnification + "x", 120, 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(craftingGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

	}

}
