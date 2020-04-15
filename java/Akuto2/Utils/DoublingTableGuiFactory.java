package Akuto2.Utils;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class DoublingTableGuiFactory implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return (GuiScreen)new DoublingTableConfigGui(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = Lists.newArrayList();
		for(String category : DoublingTableConfig.config.getCategoryNames()) {
			list.addAll(new ConfigElement(DoublingTableConfig.config.getCategory(category)).getChildElements());
		}
		return list;
	}

	public static class DoublingTableConfigGui extends GuiConfig{
		public DoublingTableConfigGui(GuiScreen parent) {
			super(parent, DoublingTableGuiFactory.getConfigElements(), "doublingtable", false, false, "DoublingTable");
		}
	}
}
