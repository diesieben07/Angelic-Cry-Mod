package demonmodders.Crymod.Common.Blocks;

import demonmodders.Crymod.Common.Crymod;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockCryMod extends Block {

	public BlockCryMod(int blockId, int texture, Material material) {
		super(blockId, texture, material);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
}