package akuto2.doublingtable.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;

/**
 * EnchantmentTableMk2用のエンチャントに関するクラス
 */
public class EnchantmentHelper {
	/**
	 * アイテムにランダムでエンチャントを付ける
	 */
	public static ItemStack addRandomEnchantment(Random random, ItemStack stack, int level) {
		List<EnchantmentData> list = buildEnchantmentList(random, stack, level);
		boolean flag = stack.getItem() == Items.book;

		if(flag) {
			stack = new ItemStack(Items.enchanted_book);
		}

		for(EnchantmentData data : list) {
			if(flag) {
				Items.enchanted_book.addEnchantment(stack, data);
			}
			else {
				addEnchantment(stack, data);
			}
		}

		return stack;
	}

	/**
	 * エンチャントのデータを作成する
	 */
	public static List<EnchantmentData> buildEnchantmentList(Random random, ItemStack stack, int level) {
		List<EnchantmentData> list = Lists.newArrayList();
		Item item = stack.getItem();
		int j = item.getItemEnchantability(stack);

		if(j <= 0) {
			return null;
		}
		else {
			int enchantability = level + 1 + random.nextInt(j / 4 + 1) + random.nextInt(j / 4 + 1);
			float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
			enchantability = MathHelper.clamp_int(Math.round((float)level + (float)level * f), 1, Integer.MAX_VALUE);
			List<EnchantmentData> enchantList = getEnchantmentDatas(level, stack, enchantability);

			if(!enchantList.isEmpty()) {
				if(level <= 100) {
					list.add((EnchantmentData)WeightedRandom.getRandomItem(random, enchantList));

					int randomValue = level <= 30 ? 50 : 160;
					while(random.nextInt(100) <= enchantability) {
						removeIncompatible(enchantList, list.get(list.size() - 1));

						if(enchantList.isEmpty()) {
							break;
						}

						list.add((EnchantmentData)WeightedRandom.getRandomItem(random, enchantList));
						level /= 2;
					}
				}
				else {
					list = enchantList;
				}
			}

			return list;
		}
	}

	/**
	 * 指定のエンチャントと同時につけることが出来ないエンチャントをリストから削除する
	 */
	public static void removeIncompatible(List<EnchantmentData> list, EnchantmentData enchantmentData) {
		Iterator<EnchantmentData> iterator = list.iterator();

		while(iterator.hasNext()) {
			EnchantmentData nextData = iterator.next();
			if(!(enchantmentData.enchantmentobj.canApplyTogether(nextData.enchantmentobj) && nextData.enchantmentobj.canApplyTogether(enchantmentData.enchantmentobj))) {
				iterator.remove();
			}
		}
	}

	/**
	 * エンチャントのレベルを求める
	 */
	public static int calcItemStackEnchantability(Random random, int enchantNum, int tier, ItemStack stack) {
		Item item = stack.getItem();
		int i = item.getItemEnchantability(stack);

		if(i <= 0) {
			return 0;
		}
		else {
			if(tier == 0) {
				int j = random.nextInt(8) + 1 + (15 >> 1) + random.nextInt(15 + 1);
				return enchantNum == 0 ? Math.max(j / 3, 1) : (enchantNum == 1 ? j * 2 / 3 + 1 : Math.max(j, 30));
			}
			else {
				if(enchantNum == 0) {
					return random.nextInt(50) + 1 + (tier - 1) * 100;
				}
				else if(enchantNum == 1) {
					return random.nextInt(50) + 51 + (tier - 1) * 100;
				}
				else if(enchantNum == 2) {
					return tier * 100;
				}
				else {
					return 0;
				}
			}
		}
	}

	public static List<EnchantmentData> getEnchantmentDatas(int level, ItemStack stack, int enchantability) {
		List<EnchantmentData> list = Lists.newArrayList();
		Item item = stack.getItem();
		boolean flag = stack.getItem() == Items.book;

		for(Enchantment enchantment : Enchantment.enchantmentsList) {
			if(enchantment == null)	continue;
			if(enchantment.canApplyAtEnchantingTable(stack) || (flag && enchantment.isAllowedOnBooks())) {
				if(enchantment.getMaxLevel() <= 1) {
					if(canEnchant(enchantment, level, 1, enchantability)) {
						list.add(new EnchantmentData(enchantment, 1));
					}
				}
				else {
					for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
						if(canEnchant(enchantment, level, i, enchantability)) {
							list.add(new EnchantmentData(enchantment, i * level));
							break;
						}
					}
				}
			}
		}

		return list;
	}

	public static void addEnchantment(ItemStack stack, EnchantmentData data) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		if(!stack.getTagCompound().hasKey("ench", 9)) {
			stack.getTagCompound().setTag("ench", new NBTTagList());
		}

		NBTTagList tagList = stack.getTagCompound().getTagList("ench", 10);
		NBTTagCompound compound = new NBTTagCompound();
		compound.setShort("id", (short)data.enchantmentobj.effectId);
		compound.setShort("lvl", (short)data.enchantmentLevel);
		tagList.appendTag(compound);
	}

	/**
	 * EnchantTableMk2でのレベル6以上のエンチャント用の翻訳名とレベルを返す
	 * レベルはローマ数字ではなくアラビア数字になる
	 */
	public static String getTranslatedName(Enchantment enchantment, int level) {
		return StatCollector.translateToLocal(enchantment.getName()) + " " + level;
	}

	/**
	 * エンチャント出来るかチェックする
	 */
	public static boolean canEnchant(Enchantment enchantment, int playerLevel, int enchantLevel, int enchantability) {
		if(playerLevel < 1) {
			return false;
		}
		else if(playerLevel > 30) {
			return true;
		}
		else {
			if(enchantability >= enchantment.getMinEnchantability(enchantLevel) && enchantability <= enchantment.getMaxEnchantability(enchantLevel)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
