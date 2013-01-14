package demonmodders.crymod.common.worldgen.struct;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import demonmodders.crymod.common.worldgen.AbstractWorldGenerator;
import demonmodders.crymod.common.worldgen.Rotation;
import demonmodders.crymod.common.worldgen.SchematicPlacer;
import demonmodders.crymod.common.worldgen.StructureInformation;
import demonmodders.crymod.common.worldgen.StructureType;

import static demonmodders.crymod.common.worldgen.Rotation.*;

public class GenDungeonLarge extends AbstractWorldGenerator {

	private final SchematicPlacer smallEntrance = new SchematicPlacer("dungeonLarge/smallEntrance.schematic");
	private final SchematicPlacer spiralstairs = new SchematicPlacer("dungeonLarge/spiralstairs.schematic");
	private final SchematicPlacer largeHall = new SchematicPlacer("dungeonLarge/largeHall.schematic");
	private final SchematicPlacer stairs = new SchematicPlacer("dungeonLarge/stairs.schematic");
	private final SchematicPlacer room9 = new SchematicPlacer("dungeonLarge/room9.schematic");
	private final SchematicPlacer lavaHall = new SchematicPlacer("dungeonLarge/lavaHall.schematic");
	
	@Override
	protected void generateAt(World world, Random random, int dimension, int x, int y, int z) {
		smallEntrance.place(NONE, world, x + 0, y + 0, z + 0, true);
        //0, 40
        spiralstairs.place(NONE, world, x + 2, y - 17, z + 3, true);
        //-17, 23
        largeHall.place(NONE, world, x - 6, y - 30, z - 27, true);
        //-30, 10
        room9.place(NONE, world, x + 15, y - 28, z - 19, true);
        //-28, 12
        stairs.place(NONE, world, x + 15, y - 28, z + 11, true);
        //-28, 12 31 (19)
        lavaHall.place(NONE, world, x + 15, y - 28, z + 22, true);
	}
	
	@Override
	protected StructureType getStructureType() {
		return StructureType.DUNGEON_LARGE;
	}

	private static final int GRASS = Block.grass.blockID;
	
	@Override
	protected boolean canGenerate(World world, int dimension, int x, int y, int z) {
		if (dimension != 0) {
			return false;
		}
		return world.getBlockId(x, y, z) == GRASS && world.getBlockId(x + 7, y, z) == GRASS && world.getBlockId(x, y, z + 7) == GRASS && world.getBlockId(x + 7, y, z + 7) == GRASS;
	}

	@Override
	public AxisAlignedBB[] getBoundingBoxes(ChunkPosition position, Rotation rotation) {
		return new AxisAlignedBB[] {
			smallEntrance.getBoundingBox(position, Rotation.NONE),
			spiralstairs.getBoundingBox(position.x + 2, position.y - 17, position.z + 3, Rotation.NONE),
			largeHall.getBoundingBox(position.x - 6, position.y - 30, position.z - 27, Rotation.NONE),
			room9.getBoundingBox(position.x + 15, position.y - 28, position.z - 19, Rotation.NONE),
			stairs.getBoundingBox(position.x + 15, position.y - 28, position.z + 11, Rotation.NONE),
			lavaHall.getBoundingBox(position.x + 15, position.y - 28, position.z + 22, Rotation.NONE)
		};
	}
}