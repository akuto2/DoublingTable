package akuto2.doublingtable;

import static akuto2.doublingtable.ObjManager.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeManager {
	public static void addRecipe() {
		// 倍加テーブル
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 0), "xxx", "xyx", "xxx", 'x', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE), 'y', Blocks.CRAFTING_TABLE);
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 1), "xxx", "xyx", "xxx", 'x', Blocks.COBBLESTONE, 'y', new ItemStack(doublingTable, 1, 0));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 2), "xxx", "xyx", "xxx", 'x', Items.IRON_INGOT, 'y', new ItemStack(doublingTable, 1, 1));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 3), "xxx", "xyx", "xxx", 'x', Items.GOLD_INGOT, 'y', new ItemStack(doublingTable, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 4), "xxx", "xyx", "xxx", 'x', Items.DIAMOND, 'y', new ItemStack(doublingTable, 1, 3));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 5), "xxx", "xyx", "xxx", 'x', Items.EMERALD, 'y', new ItemStack(doublingTable, 1, 4));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 6), "xxx", "xyx", "xxx", 'x', new ItemStack(Items.DYE, 1, 4), 'y', new ItemStack(doublingTable, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingTable, 1, 7), "xxx", "xyx", "xxx", 'x', Items.REDSTONE, 'y', new ItemStack(doublingTable, 1, 2));

		// 倍加かまど
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 0), "xxx", "xyx", "xxx", 'x', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE), 'y', Blocks.FURNACE);
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 1), "xxx", "xyx", "xxx", 'x', Blocks.COBBLESTONE, 'y', new ItemStack(doublingFurnace, 1, 0));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 2), "xxx", "xyx", "xxx", 'x', Items.IRON_INGOT, 'y', new ItemStack(doublingFurnace, 1, 1));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 3), "xxx", "xyx", "xxx", 'x', Items.GOLD_INGOT, 'y', new ItemStack(doublingFurnace, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 4), "xxx", "xyx", "xxx", 'x', Items.DIAMOND, 'y', new ItemStack(doublingFurnace, 1, 3));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 5), "xxx", "xyx", "xxx", 'x', Items.EMERALD, 'y', new ItemStack(doublingFurnace, 1, 4));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 6), "xxx", "xyx", "xxx", 'x', new ItemStack(Items.DYE, 1, 4), 'y', new ItemStack(doublingFurnace, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingFurnace, 1, 7), "xxx", "xyx", "xxx", 'x', Items.REDSTONE, 'y', new ItemStack(doublingFurnace, 1, 2));

		// クラフトロッド
		GameRegistry.addRecipe(new ItemStack(craftRod), "  x", " y ", "z  ", 'x', Items.ENDER_PEARL, 'y', Blocks.CRAFTING_TABLE, 'z', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 0), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 0), 'z', Items.STICK, 'w', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 1), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 1), 'z', Items.STICK, 'w', Blocks.COBBLESTONE);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 2), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 2), 'z', Items.STICK, 'w', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 3), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 3), 'z', Items.STICK, 'w', Items.GOLD_INGOT);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 4), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 4), 'z', Items.STICK, 'w', Items.DIAMOND);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 5), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 5), 'z', Items.STICK, 'w', Items.EMERALD);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 6), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 6), 'z', Items.STICK, 'w', new ItemStack(Items.DYE, 1, 4));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 7), "wwx", "wyw", "zww", 'x', Items.ENDER_PEARL, 'y', new ItemStack(doublingTable, 1, 7), 'z', Items.STICK, 'w', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 0), " x", "y ", 'x', new ItemStack(doublingTable, 1, 0), 'y', craftRod);
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 1), " x", "y ", 'x', new ItemStack(doublingTable, 1, 1), 'y', new ItemStack(doublingCraftRod, 1, 0));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 2), " x", "y ", 'x', new ItemStack(doublingTable, 1, 2), 'y', new ItemStack(doublingCraftRod, 1, 1));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 3), " x", "y ", 'x', new ItemStack(doublingTable, 1, 3), 'y', new ItemStack(doublingCraftRod, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 4), " x", "y ", 'x', new ItemStack(doublingTable, 1, 4), 'y', new ItemStack(doublingCraftRod, 1, 3));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 5), " x", "y ", 'x', new ItemStack(doublingTable, 1, 5), 'y', new ItemStack(doublingCraftRod, 1, 4));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 6), " x", "y ", 'x', new ItemStack(doublingTable, 1, 6), 'y', new ItemStack(doublingCraftRod, 1, 2));
		GameRegistry.addRecipe(new ItemStack(doublingCraftRod, 1, 7), " x", "y ", 'x', new ItemStack(doublingTable, 1, 7), 'y', new ItemStack(doublingCraftRod, 1, 2));

		// EXPブースト
		GameRegistry.addRecipe(new ItemStack(expBoost1), "  x", " y ", "x  ", 'x', Items.EXPERIENCE_BOTTLE, 'y', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(expBoost2), "  x", " y ", "x  ", 'x', expBoost1, 'y', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(expBoost3), "z x", " y ", "x z", 'x', expBoost2, 'y', Blocks.COAL_BLOCK, 'z', Items.COAL);
		GameRegistry.addRecipe(new ItemStack(expBoost4), "z x", " y ", "x z", 'x', expBoost3, 'y', Blocks.REDSTONE_BLOCK, 'z', Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(expBoost5), "z x", " y ", "x z", 'x', expBoost4, 'y', Blocks.IRON_BLOCK, 'z', Items.IRON_INGOT);
		GameRegistry.addRecipe(new ItemStack(expBoost6), "z x", " y ", "x z", 'x', expBoost5, 'y', Blocks.GOLD_BLOCK, 'z', Items.GOLD_INGOT);
		GameRegistry.addRecipe(new ItemStack(expBoost7), "z x", " y ", "x z", 'x', expBoost6, 'y', Blocks.DIAMOND_BLOCK, 'z', Items.DIAMOND);
		GameRegistry.addRecipe(new ItemStack(expBoost8), "z x", " y ", "x z", 'x', expBoost7, 'y', Blocks.EMERALD_BLOCK, 'z', Items.EMERALD);
		GameRegistry.addRecipe(new ItemStack(expBoost9), "z x", " y ", "x z", 'x', expBoost8, 'y', Items.BLAZE_ROD, 'z', Items.MAGMA_CREAM);
		GameRegistry.addRecipe(new ItemStack(expBoost10), "z x", " y ", "x z", 'x', expBoost9, 'y', Items.ENDER_PEARL, 'z', Items.ENDER_EYE);
		GameRegistry.addRecipe(new ItemStack(expBoost11), "z x", " y ", "x z", 'x', expBoost10, 'y', Items.NETHER_STAR, 'z', Blocks.DRAGON_EGG);
		GameRegistry.addRecipe(new ItemStack(uexpBoost), "x x", " y ", "x x", 'x', expBoost11, 'y', new ItemStack(compressedExpCore, 1, 5));

		// EXPコア
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 0), "xxx", "xxx", "xxx", 'x', expCore);
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 1), "xxx", "xxx", "xxx", 'x', new ItemStack(compressedExpCore, 1, 0));
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 2), "xxx", "xxx", "xxx", 'x', new ItemStack(compressedExpCore, 1, 1));
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 3), "xxx", "xxx", "xxx", 'x', new ItemStack(compressedExpCore, 1, 2));
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 4), "xxx", "xxx", "xxx", 'x', new ItemStack(compressedExpCore, 1, 3));
		GameRegistry.addRecipe(new ItemStack(compressedExpCore, 1, 5), "xxx", "xxx", "xxx", 'x', new ItemStack(compressedExpCore, 1, 4));

		// バニラのレシピ追加
		GameRegistry.addRecipe(new ItemStack(Items.EXPERIENCE_BOTTLE, 4), "x", "y", 'x', Items.DIAMOND, 'y', Items.GLASS_BOTTLE);
		GameRegistry.addRecipe(new ItemStack(Blocks.DRAGON_EGG), "zxz", "xyx", "zxz", 'x', Blocks.DIAMOND_BLOCK, 'y', Items.EGG, 'z', Items.NETHER_STAR);
	}
}
