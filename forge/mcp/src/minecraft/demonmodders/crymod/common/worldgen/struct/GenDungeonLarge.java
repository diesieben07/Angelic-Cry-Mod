package demonmodders.crymod.common.worldgen.struct;

import static demonmodders.crymod.common.worldgen.Rotation.NONE;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import demonmodders.crymod.common.worldgen.Structure;
import demonmodders.crymod.common.worldgen.Rotation;
import demonmodders.crymod.common.worldgen.SchematicPlacer;

public class GenDungeonLarge extends Structure {

	public GenDungeonLarge(int id) {
		super(id);
	}

	private final SchematicPlacer smallEntrance = getSchematicPlacer("dungeonLarge/smallEntrance.schematic");
	private final SchematicPlacer spiralstairs = getSchematicPlacer("dungeonLarge/spiralstairs.schematic");
	private final SchematicPlacer largeHall = getSchematicPlacer("dungeonLarge/largeHall.schematic");
	private final SchematicPlacer stairs = getSchematicPlacer("dungeonLarge/stairs.schematic");
	private final SchematicPlacer room9 = getSchematicPlacer("dungeonLarge/room9.schematic");
	private final SchematicPlacer lavaHall = getSchematicPlacer("dungeonLarge/lavaHall.schematic");
	
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
	
	private static final int GRASS = Block.grass.blockID;
	
	@Override
	protected boolean canGenerate(World world, int dimension, int x, int y, int z) {
		if (dimension != 0) {
			return false;
		}
		return world.getBlockId(x, y, z) == GRASS && world.getBlockId(x + 7, y, z) == GRASS && world.getBlockId(x, y, z + 7) == GRASS && world.getBlockId(x + 7, y, z + 7) == GRASS;
	}

	@Override
	public AxisAlignedBB[] getBoundingBoxes(int x, int y, int z, Rotation rotation) {
		return new AxisAlignedBB[] {
			smallEntrance.getBoundingBox(x, y, z, Rotation.NONE),
			spiralstairs.getBoundingBox(x + 2, y - 17, z + 3, Rotation.NONE),
			largeHall.getBoundingBox(x - 6, y - 30, z - 27, Rotation.NONE),
			room9.getBoundingBox(x + 15, y - 28, z - 19, Rotation.NONE),
			stairs.getBoundingBox(x + 15, y - 28, z + 11, Rotation.NONE),
			lavaHall.getBoundingBox(x + 15, y - 28, z + 22, Rotation.NONE)
		};
	}
}