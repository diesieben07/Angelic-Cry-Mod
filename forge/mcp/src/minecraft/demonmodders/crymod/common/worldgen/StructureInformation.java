package demonmodders.crymod.common.worldgen;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.chunk.Chunk;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Saves information about Structures in the world
 *
 */
public final class StructureInformation {
	
	private final int x;
	private final int y;
	private final int z;
	private final StructureType type;
	private final Rotation rotation;
	
	public StructureInformation(StructureType type, int x, int y, int z, Rotation rotation) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
	}
	
	public NBTTagCompound writeToNbt(NBTTagCompound nbt) {
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		nbt.setByte("type", type.getId());
		nbt.setByte("rot", rotation.getId());
		return nbt;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rotation == null) ? 0 : rotation.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		StructureInformation other = (StructureInformation) obj;
		if (rotation != other.rotation)
			return false;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public static StructureInformation readFromNbt(NBTTagCompound nbt) {
		int x = nbt.getInteger("x");
		int y = nbt.getInteger("y");
		int z = nbt.getInteger("z");
		StructureType type = StructureType.byId(nbt.getByte("type"));
		Rotation rotation = Rotation.byId(nbt.getByte("rot"));
		return new StructureInformation(type, x, y, z, rotation);
	}
	
	private static Multimap<Chunk, StructureInformation> loadedStructures = ArrayListMultimap.create();
	
	/**
	 * get all currently loaded structures
	 * @return all loaded structures
	 */
	public static Collection<StructureInformation> getLoadedStructures() {
		return Collections.unmodifiableCollection(loadedStructures.values());
	}
	
	/**
	 * get all loaded structures in specified chunk
	 * @param chunk the chunk to get structures from
	 * @return the loaded structures in that chunk
	 */
	public static Collection<StructureInformation> getLoadedStructures(Chunk chunk) {
		return Collections.unmodifiableCollection(loadedStructures.get(chunk));
	}
	
	/**
	 * loads all the structures saved in specified chunk
	 * @param chunk the chunk to be loaded
	 * @param chunkData the NBTTagCompound of that chunk
	 */
	public static void loadChunkStructures(Chunk chunk, NBTTagCompound chunkData) {
		NBTTagList structureList = chunkData.getTagList("summoningmodStruct");
		for (int i = 0; i < structureList.tagCount(); i++) {
			loadedStructures.put(chunk, StructureInformation.readFromNbt((NBTTagCompound)structureList.tagAt(i)));
		}
	}
	
	/**
	 * saves all the structures in specified chunk to the give NBTTagCompound
	 * @param chunk the chunk to be saved
	 * @param chunkData the NBTTagCompound the structures should be saved to
	 */
	public static void saveChunkStructures(Chunk chunk, NBTTagCompound chunkData) {
		NBTTagList structureList = new NBTTagList();
		for (StructureInformation structure : loadedStructures.get(chunk)) {
			structureList.appendTag(structure.writeToNbt(new NBTTagCompound()));
		}
		if (structureList.tagCount() > 0) {
			chunkData.setTag("summoningmodStruct", structureList);
		}
	}
	
	/**
	 * unloads all structures in specified chunk
	 * @param chunk the chunk to unload
	 */
	public static void unloadChunkStructures(Chunk chunk) {
		loadedStructures.removeAll(chunk).size();
	}
	
	/**
	 * adds a structure to specified chunk (for use in terrain-generation)
	 * @param chunk the chunk the structure should be added to
	 * @param structure the structure to be loaded
	 */
	public static void loadStructureInChunk(Chunk chunk, StructureInformation structure) {
		System.out.println("Spawned structure " + structure.type + " @ " + structure.x + ", " + structure.y + ", " + structure.z);
		loadedStructures.put(chunk, structure);
	}
}