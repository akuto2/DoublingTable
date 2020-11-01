package akuto2.doublingtable.enchantments;

import static akuto2.doublingtable.utils.DoublingTableConfig.*;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

/**
 * エンチャントを管理しておくクラス
 * @author akuto2
 *
 */
public class DoublingTableEnchantments {
	public static Enchantment mending;
	public static Enchantment fly;

	public static void Initialize() {
		mending = new EnchantmentMending(mendingID, 1, EnumEnchantmentType.breakable);
		fly = new EnchantmentFly(flyID, 1, EnumEnchantmentType.armor_torso);
	}
}
