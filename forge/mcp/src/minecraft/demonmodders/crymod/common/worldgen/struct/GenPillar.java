package demonmodders.crymod.common.worldgen.struct;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import demonmodders.crymod.common.worldgen.Structure;
import demonmodders.crymod.common.worldgen.Rotation;
import demonmodders.crymod.common.worldgen.SchematicPlacer;

public class GenPillar extends Structure {

	public GenPillar(int id) {
		super(id);
	}

	private final SchematicPlacer pillar = new SchematicPlacer("pillar/pillarLarge.schematic");
	
	@Override
	protected void generateAt(World world, Random random, int dimension, int x, int y, int z) {
		pillar.place(getRandomRotation(random), world, x, y + 1, z, true);
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
			pillar.getBoundingBox(x, y + 1, z, rotation)	
		};
	}
}