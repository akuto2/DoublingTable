package akuto2.utils;

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
			super(parent, DoublingTableGuiFactory.getConfigElements(), "DoublingTable", false, false, "DoublingTable");
		}
	}

	private static List<IConfigElement> getConfigElements(){
		List<IConfigElement> list = Lists.newArrayList();
		for(String category : DoublingTableConfig.config.getCategoryNames()) {
			list.addAll(new ConfigElement(DoublingTableConfig.config.getCategory(category)).getChildElements());
		}
		return list;
	}
}
