package demonmodders.Crymod.Common.WorldGen;
 
import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;
 
public class CryWorldGenerator implements IWorldGenerator {
	
	private final WorldGenerator dungeonLargeGen = new GenDungeonLarge();
	
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
	}
}      
       
