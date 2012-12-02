package demonmodders.Crymod.Common.Recipes;

import java.util.ArrayList;
import java.util.List;

import demonmodders.Crymod.Common.Entities.EntityHeavenZombie;

public class SummoningRecipeRegistry {
	private static List<SummoningRecipe> recipes = new ArrayList<SummoningRecipe>();
	
	public static List<SummoningRecipe> getRecipes() {
		return recipes;
	}
	
	public static int getNumRecipes() {
		return recipes.size();
	}
	
	static {
		recipes.add(new SummoningRecipe(null, EntityHeavenZombie.class, "Heaven Zombie I"));
		recipes.add(new SummoningRecipe(null, EntityHeavenZombie.class, "Heaven Zombie II"));
		recipes.add(new SummoningRecipe(null, EntityHeavenZombie.class, "Heaven Zombie III"));
	}
}
