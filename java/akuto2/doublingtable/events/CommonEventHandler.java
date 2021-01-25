package akuto2.doublingtable.events;

import java.util.Random;

import akuto2.doublingtable.ObjManager;
import akuto2.doublingtable.utils.DoublingTableConfig;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = "doublingtable")
public class CommonEventHandler {
	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals("doublingtable")) {
			DoublingTableConfig.syncConfig();
		}
	}

	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event) {
		World world = event.getEntityLiving().world;
		double x = event.getEntityLiving().posX;
		double y = event.getEntityLiving().posY;
		double z = event.getEntityLiving().posZ;

		Random rand = event.getEntityLiving().world.rand;

		int lootingLevel = (event.getLootingLevel() * 5) + 5;

		int noA = rand.nextInt(100);

		if(noA < lootingLevel && event.getEntityLiving() instanceof EntityMob) {
			event.getDrops().add(new EntityItem(world, x, y, z, new ItemStack(ObjManager.expCore, 1)));
		}
	}
}
