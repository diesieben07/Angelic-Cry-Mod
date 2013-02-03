package demonmodders.crymod.common.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.blocks.BlockCryMod;
import demonmodders.crymod.common.items.CrystalType;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.items.ItemSummoner;

public final class CraftingRecipes {

	private CraftingRecipes() {
	}

	public static void registerRecipes() {
		// Recharge Station:
		GameRegistry.addRecipe(new ItemStack(BlockCryMod.rechargeStation),
				"GLG", "IFI", "OOO",
				'G', Block.glass,
				'L', Block.redstoneLampIdle,
				'I', Item.ingotGold,
				'F', Block.stoneOvenIdle,
				'O', Block.obsidian);
		
		// ender book
		GameRegistry.addRecipe(new ItemStack(BlockCryMod.enderBook), 
				" B ", "RWR", "OEO",
				'B', Item.writableBook,
				'R', Item.redstone,
				'W', new ItemStack(Block.cloth, 1, 9), // cyan
				'O', Block.obsidian,
				'E', Item.eyeOfEnder);
		
		// summoning book
		GameRegistry.addShapelessRecipe(new ItemStack(ItemCryMod.summoner, 1, ItemSummoner.Type.SUMMONING_BOOK.getItemDamage()), 
				Item.book,
				new ItemStack(Item.dyePowder, 1, 4)); // lapis lazuli
		
		// evil tablet
		GameRegistry.addShapelessRecipe(new ItemStack(ItemCryMod.summoner, 1, ItemSummoner.Type.EVIL_TABLET.getItemDamage()),
				Item.flintAndSteel,
				Block.stoneSingleSlab);
		
		// gold crystal
		GameRegistry.addRecipe(CrystalType.YELLOW.generateItemStack(),
				"GG", "GG",
				'G', Item.goldNugget
				);
		
		// green crystal
		GameRegistry.addRecipe(CrystalType.GREEN.generateItemStack(),
				"SC", "CS",
				'S', new ItemStack(Block.sapling, 1, 0),
				'C', CrystalType.YELLOW.generateItemStack());
		
		// fiery crystal
		GameRegistry.addRecipe(CrystalType.ORANGE.generateItemStack(),
				"CF", "FC",
				'C', CrystalType.PURPLE.generateItemStack(),
				'F', Item.flintAndSteel);
	}
}