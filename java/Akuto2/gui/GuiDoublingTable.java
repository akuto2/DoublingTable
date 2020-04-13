package Akuto2.Gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiDoublingTable extends GuiCrafting{
	private int magnification;

	public GuiDoublingTable(InventoryPlayer inventoryPlayer, World world, BlockPos pos, int type) {
		super(inventoryPlayer, world, pos);
		magnification = type;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
		fontRenderer.drawString(magnification + "x", 120, 20, 4210752);
	}
}
