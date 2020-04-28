package akuto2.event;

import akuto2.utils.DoublingTableConfig;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals("DoublingTable")) {
			DoublingTableConfig.syncConfig();
		}
	}
}
