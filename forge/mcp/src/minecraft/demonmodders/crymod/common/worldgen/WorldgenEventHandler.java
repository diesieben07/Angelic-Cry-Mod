package demonmodders.crymod.common.worldgen;

import demonmodders.crymod.common.blocks.BlockCryMod;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

public class WorldgenEventHandler {
	
	private static final WorldGenerator flowerGen = new WorldGenFlowers(BlockCryMod.plants.blockID);
	
	@ForgeSubscribe
	public void onBiomeDecorate(DecorateBiomeEvent.Decorate event) {
		if (event.type == EventType.FLOWERS) {
			for (int i = 0; i < 2; i++) {
				int x = event.chunkX + event.rand.nextInt(16) + 8;
				int y = event.rand.nextInt(128);
				int z = event.chunkZ + event.rand.nextInt(16) + 8;
				flowerGen.generate(event.world, event.rand, x, y, z);
			}
		}
	}
}