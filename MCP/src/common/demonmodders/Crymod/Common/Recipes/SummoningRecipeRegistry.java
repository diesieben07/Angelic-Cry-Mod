package demonmodders.Crymod.Common.Recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.ItemStack;

public class SummoningRecipeRegistry {
	private static List<SummoningRecipe> recipes = new ArrayList<SummoningRecipe>();
	
	public static List<SummoningRecipe> getRecipes() {
		return recipes;
	}
	
	public static void addRecipe(SummoningRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static int getNumRecipes() {
		return recipes.size();
	}
	
	static {
		addRecipe(new SummoningRecipe(null, "First Demon"));
		addRecipe(new SummoningRecipe(null, "Second Demon"));
	}
}
