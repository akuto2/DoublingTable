package com.akuto2.doublingtable.gui;

import com.akuto2.doublingtable.blockentities.BlockEntityDoublingFurnace;
import com.akuto2.doublingtable.menu.MenuDoublingFurnace;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiDoublingFurnace extends AbstractContainerScreen<MenuDoublingFurnace> implements RecipeUpdateListener {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
	private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
	private final RecipeBookComponent recipeBookComponent = new SmeltingRecipeBookComponent();
	private final BlockEntityDoublingFurnace furnace;
	private boolean widthTooNarrow;

	public GuiDoublingFurnace(MenuDoublingFurnace menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.furnace = menu.furnace;
	}
	
	@Override
	protected void init() {
		super.init();
		widthTooNarrow = width < 379;
		recipeBookComponent.init(width, height, minecraft, widthTooNarrow, menu);
		leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
		addRenderableWidget(new ImageButton(leftPos + 20, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (button) -> {
			recipeBookComponent.toggleVisibility();
			leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
			button.setPosition(leftPos + 20, height / 2 - 49);
		}));
		titleLabelX = (imageWidth - font.width(title)) / 2;
	}
	
	@Override
	protected void containerTick() {
		super.containerTick();
		recipeBookComponent.tick();
	}
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		renderBackground(graphics);
		if (recipeBookComponent.isVisible() && widthTooNarrow) {
			renderBg(graphics, partialTick, mouseX, mouseY);
			recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
		} else {
			recipeBookComponent.render(graphics, mouseX, mouseY, partialTick);
			super.render(graphics, mouseX, mouseY, partialTick);
			recipeBookComponent.renderGhostRecipe(graphics, leftPos, topPos, true, partialTick);
		}
		
		renderTooltip(graphics, mouseX, mouseY);
		recipeBookComponent.renderTooltip(graphics, leftPos, topPos, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		if (menu.isLit()) {
			int litProgress = menu.getLitProgress();
			graphics.blit(TEXTURE, leftPos + 56, topPos + 36 + 12 - litProgress, 176, 12 - litProgress, 14, litProgress + 1);
		}
		
		int burnProgrees = menu.getBurnProgress();
		graphics.blit(TEXTURE, leftPos + 76, topPos + 34, 176, 14, burnProgrees + 1, 16);
	}
	
	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
		graphics.drawString(font, furnace.getFacilityType().getTimes() + "x", 120, 20, 0x404040, false);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
			return true;
		} else {
			return widthTooNarrow && recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	protected void slotClicked(Slot slot, int id, int mouseButton, ClickType type) {
		super.slotClicked(slot, id, mouseButton, type);
		recipeBookComponent.slotClicked(slot);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) ? false : super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
		return recipeBookComponent.hasClickedOutside(mouseX, mouseY, leftPos, topPos, imageWidth, imageHeight, mouseButton) && super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
	}
	
	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		return recipeBookComponent.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
	}

	@Override
	public void recipesUpdated() {
		recipeBookComponent.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() {
		return recipeBookComponent;
	}

}
