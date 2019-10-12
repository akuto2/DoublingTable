package Akuto2.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

/**
 * エンチャントを管理しておくクラス
 * @author akuto2
 *
 */
public class DoublingTableEnchantments {
	public static int mendingID = 100;
	public static int flyID = 101;

	public static Enchantment mending = new EnchantmentMending(mendingID, 1, EnumEnchantmentType.breakable);
	public static Enchantment fly = new EnchantmentFly(flyID, 1, EnumEnchantmentType.armor_torso);
}
