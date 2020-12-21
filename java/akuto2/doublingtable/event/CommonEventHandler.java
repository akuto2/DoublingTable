package akuto2.doublingtable.event;

import java.util.Random;

import akuto2.doublingtable.ObjManager;
import akuto2.doublingtable.utils.DoublingTableConfig;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals("DoublingTable")) {
			DoublingTableConfig.syncConfig();
		}
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		World world = event.getEntityLiving().worldObj;
		double x = event.getEntityLiving().posX;
		double y = event.getEntityLiving().posY;
		double z = event.getEntityLiving().posZ;

		Random random = event.getEntityLiving().worldObj.rand;

		int dropChance = (event.getLootingLevel() * 5) + 5;

		int noA = random.nextInt(100);

		if(noA < dropChance && event.getEntityLiving() instanceof EntityMob) {
			event.getDrops().add(new EntityItem(world, x, y, z, new ItemStack(ObjManager.expCore, 1)));
		}
	}
}
