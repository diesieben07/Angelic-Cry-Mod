package demonmodders.crymod.common.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagiciserRecipes {
	
	private static final MagiciserRecipes INSTANCE = new MagiciserRecipes();
	
	private Map<Integer, Integer> values = new HashMap<Integer, Integer>();
	private Map<List<Integer>, Integer> itemMetaValues = new HashMap<List<Integer>, Integer>();
	
	// first array element: item/block id; second element: its value
	private Set<int[]> resultItemValues = new HashSet<int[]>();
	
	private MagiciserRecipes() {
		// normal coal
		addValue(Item.coal, 0, 15);
		// charcoal
		addValue(Item.coal, 1, 14);
		addValue(Item.ingotIron, 10);
		addValue(Item.ingotGold, 8);
		addValue(Item.diamond, 6);
		addValue(Item.stick, 50);
		addValue(Item.bowlEmpty, 45);
		addValue(Item.silk, 43);
		addValue(Item.feather, 40);
		addValue(Item.gunpowder, 30);
		addValue(Item.seeds, 39);
		addValue(Item.wheat, 40);
		addValue(Item.flint, 25);
		addValue(Item.leather, 30);
		addValue(Item.brick, 28);
		addValue(Item.clay, 35);
		addValue(Item.reed, 39);
		addValue(Item.egg, 28);
		addValue(Item.lightStoneDust, 18);
		addValue(Item.dyePowder, 25);
		addValue(Item.blazeRod, 10);
		addValue(Item.netherStalkSeeds, 9);
		addValue(Item.emerald, 6);
		addValue(Item.netherStar, 2);
		addValue(Item.redstone, 10);
		addValue(Block.obsidian, 8);
		addValue(Block.cloth, 25);
		
		addResult(Block.blockLapis, 8);
	}
	
	public int getItemValueFor(ItemStack item) {
		if (values.containsKey(item.itemID)) {
			return values.get(item.itemID);
		} else {
			List<Integer> search = Arrays.asList(item.itemID, item.getItemDamage());
			if (itemMetaValues.containsKey(search)) {
				return itemMetaValues.get(search);
			} else {
				return 0;
			}
		}
	}
	
	public ItemStack generateMatch(float averageValue) {
		int[] closestMatch = null;
		float smallestDistance = Float.MAX_VALUE;
		for (int[] recipe : resultItemValues) {
			float dist = Math.abs((float)recipe[1] - averageValue);
			if (dist < smallestDistance) {
				closestMatch = recipe;
				smallestDistance = dist;
			}
		}
		if (smallestDistance < 3) {
			return new ItemStack(closestMatch[0], smallestDistance < 1 ? 5 : smallestDistance < 2 ? 2 : 1, 0);
		} else {
			return null;
		}
	}
	
	void addValue(Item item, int value) {
		values.put(item.shiftedIndex, value);
	}
	
	void addValue(Block block, int value) {
		values.put(block.blockID, value);
	}
	
	void addValue(Item item, int meta, int value) {
		itemMetaValues.put(Arrays.asList(item.shiftedIndex, meta), value);
	}
	
	void addResult(Item item, int value) {
		resultItemValues.add(new int[] {item.shiftedIndex, value});
	}
	
	void addResult(Block block, int value) {
		resultItemValues.add(new int[] {block.blockID, value});
	}
	
	public static MagiciserRecipes instance() {
		return INSTANCE;
	}
}