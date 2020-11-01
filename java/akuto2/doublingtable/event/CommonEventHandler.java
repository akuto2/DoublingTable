package akuto2.doublingtable.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import akuto2.doublingtable.DoublingTable;
import akuto2.doublingtable.utils.DoublingTableConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class CommonEventHandler {
	public static List<String> playerWithChest = new ArrayList<String>();

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if(event.modID.equals("DoublingTable")) {
			DoublingTableConfig.syncConfig();
		}
	}

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

	@SubscribeEvent
	public void onPickupXp(PlayerPickupXpEvent event) {
		// 修繕エンチャントの効果
		EntityPlayer player = event.entityPlayer;
		// 修繕を行う優先度は手に持っているアイテム、ヘルメット、チェストプレート、レギンス、ブーツの順
		List<ItemStack> mendingCheckList = Lists.newArrayList(player.getHeldItem(), player.getCurrentArmor(3), player.getCurrentArmor(2), player.getCurrentArmor(1), player.getCurrentArmor(0));
		for(int i = 0; i < mendingCheckList.size(); i++) {
			if(EnchantmentHelper.getEnchantmentLevel(DoublingTableConfig.mendingID, mendingCheckList.get(i)) > 0) {
				ItemStack stack = mendingCheckList.get(i);
				if(stack.getItemDamage() > 0) {
					int damage = stack.getItemDamage() - event.orb.xpValue < 0 ? 0 : stack.getItemDamage() - event.orb.xpValue;
					stack.setItemDamage(damage);
					event.orb.xpValue = 0;
					break;
				}
			}
		}
	}

	public static String playerKey(EntityPlayer player) {
		return player.getGameProfile().getName() +":"+ player.worldObj.isRemote;
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			String key = playerKey(player);

			// 浮遊エンチャントの効果
			ItemStack armor = player.getCurrentArmor(2);
			Boolean hasFly = armor != null && (EnchantmentHelper.getEnchantmentLevel(DoublingTableConfig.flyID, armor) > 0);
			if(playerWithChest.contains(key)) {
				if(hasFly.booleanValue()) {
					player.capabilities.allowFlying = true;
				}
				else {
					if(!player.capabilities.isCreativeMode) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
					}
					playerWithChest.remove(key);
				}
			}
			else if(hasFly) {
				playerWithChest.add(key);
			}
		}

	}
}
