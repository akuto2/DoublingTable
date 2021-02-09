package akuto2.doublingtable.event;

import akuto2.doublingtable.utils.EnchantmentHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@SideOnly(Side.CLIENT)
public class TooltipEvent {
	@SubscribeEvent
	public void toolTipEvent(ItemTooltipEvent event) {
		ItemStack currentStack = event.itemStack;

		if(currentStack == null) {
			return;
		}

		if(currentStack.hasTagCompound()) {
			NBTTagList tagList = currentStack.getEnchantmentTagList();

			if(tagList != null) {
				for(int i = 0; i < tagList.tagCount(); ++i) {
					short id = tagList.getCompoundTagAt(i).getShort("id");
					short level = tagList.getCompoundTagAt(i).getShort("lvl");

					if(Enchantment.enchantmentsList[id] != null && level > 5) {
						String enchantName = Enchantment.enchantmentsList[id].getTranslatedName(level);
						if(event.toolTip.contains(enchantName)) {
							int index = event.toolTip.indexOf(enchantName);
							String rewriteEnchantName = EnchantmentHelper.getTranslatedName(Enchantment.enchantmentsList[id], level);
							event.toolTip.set(index, rewriteEnchantName);
						}
					}
				}
			}
		}
	}
}
