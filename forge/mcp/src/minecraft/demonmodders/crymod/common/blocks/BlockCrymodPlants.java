package demonmodders.crymod.common.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import demonmodders.crymod.common.items.ItemSummoner.Type;

import net.minecraft.block.BlockFlower;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class BlockCrymodPlants extends BlockFlower {

	protected BlockCrymodPlants(String blockName, int defaultId) {
		super(BlockCryMod.getBlockId(blockName, defaultId), 14);
		BlockCryMod.initCrymodBlock(this, blockName, ItemBlockCrymodPlants.class);
		setHardness(0);
		setStepSound(soundGrassFootstep);
	}

	@Override
	public int idDropped(int meta, Random random, int unknown) {
		return super.idDropped(meta, random, unknown);
	}

	@Override
	public int damageDropped(int originalDamage) {
		return super.damageDropped(originalDamage);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return random.nextInt(7 - fortune) == 0 ? 1 : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockId, CreativeTabs tab, List blockList) {
		for (Type type : Type.values()) {
			blockList.add(type.generateItemStack());
		}
	}
	
	@Override
	public String getBlockName() {
		// TODO Auto-generated method stub
		return super.getBlockName();
	}
	
	public static enum Type {		
		MAGICULUS_FLOWER("magiculusFlower");
		
		private final String name;
		
		private Type(String name) {
			this.name = name;
		}

		public static Type fromItemDamage(int damage) {
			return damage == 0 || damage >= values().length ? MAGICULUS_FLOWER : values()[damage];
		}
		
		public ItemStack generateItemStack() {
			return new ItemStack(BlockCryMod.plants, 1, ordinal());
		}

		public static Type fromItemDamage(ItemStack stack) {
			return fromItemDamage(stack.getItemDamage());
		}
		
		public int getItemDamage() {
			return ordinal();
		}

		public String getName() {
			return name;
		}
	}
}