package demonmodders.crymod.common.worldgen.struct;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import demonmodders.crymod.common.worldgen.AbstractWorldGenerator;
import demonmodders.crymod.common.worldgen.Rotation;
import demonmodders.crymod.common.worldgen.SchematicPlacer;
import demonmodders.crymod.common.worldgen.StructureType;

public class GenPillar extends AbstractWorldGenerator {

	private final SchematicPlacer pillar = new SchematicPlacer("pillar/pillarLarge.schematic");
	
	@Override
	protected void generateAt(World world, Random random, int dimension, int x, int y, int z) {
		pillar.place(getRandomRotation(random), world, x, y + 1, z, true);
	}
	
	@Override
	protected StructureType getStructureType() {
		return StructureType.PILLAR;
	}

	private static final int GRASS = Block.grass.blockID;
	
	@Override
	protected boolean canGenerate(World world, int dimension, int x, int y, int z) {
		if (dimension != 0) {
			return false;
		}
		return world.getBlockId(x, y, z) == GRASS && world.getBlockId(x + 7, y, z) == GRASS && world.getBlockId(x, y, z + 7) == GRASS && world.getBlockId(x + 7, y, z + 7) == GRASS;
	}
}