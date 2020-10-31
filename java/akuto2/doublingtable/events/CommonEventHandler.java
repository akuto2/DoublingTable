package akuto2.doublingtable.events;

import akuto2.doublingtable.utils.DoublingTableConfig;
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
