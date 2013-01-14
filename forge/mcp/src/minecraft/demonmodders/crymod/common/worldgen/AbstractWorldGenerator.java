package demonmodders.crymod.common.worldgen;

import java.util.Random;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public abstract class AbstractWorldGenerator implements IWorldGenerator {

	private Rotation lastRotation = Rotation.NONE;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if (random.nextInt(getRarity()) != 0) {
			return;
		}
		int dimension = world.provider.dimensionId;
		int maxTries = getMaxTriesPerChunk();
		
		for (int i = 0; i < maxTries; i++) {
			int randomX = chunkX * 16 + random.nextInt(16);
			int randomY = random.nextInt(128);
			int randomZ = chunkZ * 16 + random.nextInt(16);
			if (canGenerate(world, dimension, randomX, randomY, randomZ)) {
				generateAt(world, random, dimension, randomX, randomY, randomZ);
				StructureType type = getStructureType();
				if (type != null) {
					StructureInformation.loadStructureInChunk(world.getChunkFromChunkCoords(chunkX, chunkZ), new StructureInformation(type, randomX, randomY, randomZ, lastRotation));
				}
				break;
			}
		}
	}
	
	protected abstract void generateAt(World world, Random random, int dimension, int x, int y, int z);
	
	public abstract AxisAlignedBB[] getBoundingBoxes(ChunkPosition position, Rotation rotation);
	
	protected boolean canGenerate(World world, int dimension, int x, int y, int z) {
		return true;
	}
	
	protected int getMaxTriesPerChunk() {
		return 15;
	}
	
	protected int getRarity() {
		return 1;
	}
	
	protected StructureType getStructureType() {
		return null;
	}
	
	protected final void setLastRotation(Rotation r) {
		lastRotation = r;
	}
	
	protected final Rotation getRandomRotation(Random r) {
		lastRotation = Rotation.random(r);
		return lastRotation;
	}
}