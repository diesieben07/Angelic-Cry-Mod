package demonmodders.crymod.common.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFlowersMeta extends WorldGenerator {

	private final int id;
	private final int meta;

	public WorldGenFlowersMeta(int id, int meta) {
		this.id = id;
		this.meta = meta;
	}

	public boolean generate(World world, Random random, int x, int y, int z) {
		for (int i = 0; i < 64; ++i) {
			int randX = x + random.nextInt(8) - random.nextInt(8);
			int randY = y + random.nextInt(4) - random.nextInt(4);
			int randZ = z + random.nextInt(8) - random.nextInt(8);

			if (world.isAirBlock(randX, randY, randZ) && (!world.provider.hasNoSky || randY < 127) && Block.blocksList[id].canBlockStay(world, randX, randY, randZ)) {
				world.setBlockAndMetadata(randX, randY, randZ, id, meta);
			}
		}

		return true;
	}
}
