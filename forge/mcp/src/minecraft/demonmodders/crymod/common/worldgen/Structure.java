package demonmodders.crymod.common.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.worldgen.struct.GenDungeonLarge;
import demonmodders.crymod.common.worldgen.struct.GenPillar;
import demonmodders.crymod.common.worldgen.struct.GenRuin;

public abstract class Structure implements IWorldGenerator {

	private static Map<Integer,Structure> generators = new HashMap<Integer,Structure>();
	
	public static final Structure DUNGEON_LARGE = new GenDungeonLarge(0);
	public static final Structure PILLAR = new GenPillar(1);
	public static final Structure RUIN = new GenRuin(2);
	
	public static Structure getGeneratorById(int id) {
		return generators.get(id);
	}
	
	public static void init() { }

	private Rotation lastRotation = Rotation.NONE;
	
	private final int id;
	
	public Structure(int id) {
		GameRegistry.registerWorldGenerator(this);
		generators.put(id, this);
		this.id = id;
	}
	
	public byte getId() {
		return (byte)id;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		if (chunkGenerator instanceof ChunkProviderFlat && random.nextInt(50) != 0) {
			return;
		}
		if (random.nextInt(getRarity()) != 0) {
			return;
		}
		int dimension = world.provider.dimensionId;
		int maxTries = getMaxTriesPerChunk();
		
		for (int i = 0; i < maxTries; i++) {
			int randomX = chunkX * 16 + random.nextInt(16);
			int randomY = random.nextInt(128);
			int randomZ = chunkZ * 16 + random.nextInt(16);
			
			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			StructurePosition thisPosition = new StructurePosition(this, randomX, randomY, randomZ, lastRotation);
			
			for (StructurePosition struct : StructurePosition.getLoadedStructures(chunk)) {
				if (struct.intersectsWith(thisPosition)) {
					continue;
				}
			}
			
			if (canGenerate(world, dimension, randomX, randomY, randomZ)) {
				generateAt(world, random, dimension, randomX, randomY, randomZ);
				StructurePosition.loadStructureInChunk(chunk, thisPosition);
				break;
			}
		}
	}
	
	protected abstract void generateAt(World world, Random random, int dimension, int x, int y, int z);
	
	public abstract AxisAlignedBB[] getBoundingBoxes(int x, int y, int z, Rotation rotation);
	
	protected boolean canGenerate(World world, int dimension, int x, int y, int z) {
		return true;
	}
	
	protected int getMaxTriesPerChunk() {
		return 15;
	}
	
	protected int getRarity() {
		return 1;
	}
	
	protected final void setLastRotation(Rotation r) {
		lastRotation = r;
	}
	
	protected final Rotation getRandomRotation(Random r) {
		lastRotation = Rotation.random(r);
		return lastRotation;
	}
	
	protected SchematicPlacer getSchematicPlacer(String name) {
		return new SchematicPlacer(getClass().getResourceAsStream("/demonmodders/crymod/resource/schematics/" + name));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Structure other = (Structure) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Structure [id=" + id + "]";
	}
}