package com.akuto2.doublingtable.gui;

import com.akuto2.doublingtable.menu.MenuDoublingTable;
import com.akuto2.doublingtable.utils.enums.EnumFacilityType;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiDoublingTable<T extends RecipeBookMenu<CraftingContainer>> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
	private static final ResourceLocation RECIPE_BUTTON = new ResourceLocation("textures/gui/recipe_button.png");
	private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
	private final EnumFacilityType type;
	private boolean widthTooNarrow;
	
	protected GuiDoublingTable(T menu, Inventory playerInventory, Component title, EnumFacilityType type) {
		super(menu, playerInventory, title);
		this.type = type;
	}
	
	@Override
	protected void init() {
		super.init();
		widthTooNarrow = width < 379;
		recipeBookComponent.init(width, height, minecraft, widthTooNarrow, menu);
		leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
		addRenderableWidget(new ImageButton(leftPos + 5, height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON, (button) -> {
			recipeBookComponent.toggleVisibility();
			leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
			button.setPosition(leftPos + 5, height / 2 - 49);
		}));
		addWidget(recipeBookComponent);
		setInitialFocus(recipeBookComponent);
		titleLabelX = 29;
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
			recipeBookComponent.renderGhostRecipe(graphics, mouseX, mouseY, true, partialTick);
		}
		
		renderTooltip(graphics, mouseX, mouseY);
		recipeBookComponent.renderTooltip(graphics, leftPos, topPos, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(TEXTURE, leftPos, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
	}
	
	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		super.renderLabels(graphics, mouseX, mouseY);
		graphics.drawString(font, type.getTimes() + "x", 120, 20, 0x404040, false);
	}
	
	@Override
	protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
		return (!widthTooNarrow || !recipeBookComponent.isVisible()) && super.isHovering(x, y, width, height, mouseX, mouseY);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
			setFocused(recipeBookComponent);
			return true;
		} else {
			return widthTooNarrow && recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
		return recipeBookComponent.hasClickedOutside(mouseX, mouseY, mouseButton, mouseButton, guiLeft, guiTop, mouseButton) && super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
	}
	
	@Override
	protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
		super.slotClicked(slot, slotId, mouseButton, type);
		recipeBookComponent.slotClicked(slot);
	}

	@Override
	public void recipesUpdated() {
		recipeBookComponent.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() {
		return recipeBookComponent;
	}
	
	public static class Wood extends GuiDoublingTable<MenuDoublingTable.Wood> {
		public Wood(MenuDoublingTable.Wood menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.WOOD);
		}
	}
	
	public static class Stone extends GuiDoublingTable<MenuDoublingTable.Stone> {
		public Stone(MenuDoublingTable.Stone menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.STONE);
		}
	}
	
	public static class Iron extends GuiDoublingTable<MenuDoublingTable.Iron> {
		public Iron(MenuDoublingTable.Iron menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.IRON);
		}
	}
	
	public static class Gold extends GuiDoublingTable<MenuDoublingTable.Gold> {
		public Gold(MenuDoublingTable.Gold menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.GOLD);
		}
	}
	
	public static class Diamond extends GuiDoublingTable<MenuDoublingTable.Diamond> {
		public Diamond(MenuDoublingTable.Diamond menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.DIAMOND);
		}
	}
	
	public static class Emerald extends GuiDoublingTable<MenuDoublingTable.Emerald> {
		public Emerald(MenuDoublingTable.Emerald menu, Inventory playerInventory, Component title) {
			super(menu, playerInventory, title, EnumFacilityType.EMERALD);
		}
	}
}
