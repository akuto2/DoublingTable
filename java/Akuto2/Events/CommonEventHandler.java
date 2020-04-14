package Akuto2.Events;

import Akuto2.Utils.DoublingTableConfig;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CommonEventHandler {
	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals("doublingtable")) {
			DoublingTableConfig.syncConfig();
		}
	}
}
