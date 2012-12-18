package demonmodders.crymod.common.worldgen;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenFeaturePillar extends WorldGenerator {
    
	private final SchematicWorldGenerator pillar = new SchematicWorldGenerator("pillar/pillarLarge.schematic");
	   
	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		int bID = 2;  /*2 is the block id for grass, so the structure going to spawn on grass*/
        if (world.getBlockId(i, j, k) != bID || world.getBlockId(i, j + 1, k) != 0 || world.getBlockId(i + 7, j, k) != bID || world.getBlockId(i + 7, j, k + 7) != bID || world.getBlockId(i, j, k + 7) != bID || world.getBlockId(i + 7, j + 1, k) != 0 || world.getBlockId(i + 7, j + 1, k + 7) != 0 || world.getBlockId(i, j + 1, k + 7) != 0) {
        	return false;
        }
        pillar.generate(world, i + 0, j + 1, k + 0, true);
        
        return true;
	}
}