package akuto2;

import static akuto2.ObjManager.*;

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
	}
}
