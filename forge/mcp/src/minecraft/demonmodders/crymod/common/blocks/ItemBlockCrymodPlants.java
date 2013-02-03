package demonmodders.crymod.common.blocks;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCrymodPlants extends ItemBlock {

	public ItemBlockCrymodPlants(int blockId) {
		super(blockId);
	}

	@Override
	public String getItemNameIS(ItemStack item) {
		return "tile.crymod_plants." + BlockCrymodPlants.Type.fromItemDamage(item).getName();
	}
}