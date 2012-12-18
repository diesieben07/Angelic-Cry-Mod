package demonmodders.Crymod.Common.WorldGen;
 
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;
 
public class CryWorldGenerator implements IWorldGenerator {
	
	private final WorldGenerator dungeonLargeGen = new GenDungeonLarge();
	private final WorldGenerator featurePillar = new GenFeaturePillar();
	private final WorldGenerator featureRuin = new GenFeatureRuin();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,  IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		switch(world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX*16, chunkZ*16);
			break;
		case 0:
			generateSurface(world, random, chunkX*16, chunkZ*16);
			break;
		}
	}
	
	private void generateNether(World world, Random random, int i, int j) {
		
	}
	
	private void generateSurface(World world, Random random, int blockX, int blockZ) {
		int RandPosX = blockX + random.nextInt(16);
		int RandPosY = random.nextInt(128);
		int RandPosZ = blockZ + random.nextInt(16);
		dungeonLargeGen.generate(world, random, RandPosX, RandPosY, RandPosZ);
		int RandPosX1 = blockX + random.nextInt(16);
		int RandPosY1 = random.nextInt(128);
		int RandPosZ1 = blockZ + random.nextInt(16);
		featurePillar.generate(world, random, RandPosX1, RandPosY1, RandPosZ1);
		int RandPosX2 = blockX + random.nextInt(16);
		int RandPosY2 = random.nextInt(128);
		int RandPosZ2 = blockZ + random.nextInt(16);
		featureRuin.generate(world, random, RandPosX2, RandPosY2, RandPosZ2);
	}
	
}      
       
