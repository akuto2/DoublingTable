package Akuto2.event;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import Akuto2.DoublingTable;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event){
		World world = event.entityLiving.worldObj;
		double x = event.entityLiving.posX;
		double y = event.entityLiving.posY;
		double z = event.entityLiving.posZ;

		Random r = event.entityLiving.worldObj.rand;

		int lootinglevel = event.lootingLevel;

		int noA = new java.util.Random().nextInt(100);

		if(noA < lootinglevel * 5 && event.entityLiving instanceof EntityMob){
			event.drops.add(new EntityItem(world, x, y, z, new ItemStack(DoublingTable.expCore, 1)));
		}
	}
}
