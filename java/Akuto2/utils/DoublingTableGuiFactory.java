package Akuto2.utils;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class DoublingTableGuiFactory implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return DoublingTableConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static class DoublingTableConfigGui extends GuiConfig{
		public DoublingTableConfigGui(GuiScreen parent) {
			super(parent, (new ConfigElement<Object>(DoublingTableConfig.config.getCategory("Doubling"))).getChildElements(), "DoublingTable", false, false, "DoublingTable");
		}
	}
}
