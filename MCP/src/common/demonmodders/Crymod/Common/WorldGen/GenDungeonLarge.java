package demonmodders.Crymod.Common.WorldGen;
import java.util.Random;

import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class GenDungeonLarge extends WorldGenerator {
    
	private final SchematicWorldGenerator smallEntrance = new SchematicWorldGenerator("dungeonLarge/smallEntrance.schematic");
	private final SchematicWorldGenerator spiralstairs = new SchematicWorldGenerator("dungeonLarge/spiralstairs.schematic");
	private final SchematicWorldGenerator largeHall = new SchematicWorldGenerator("dungeonLarge/largeHall.schematic");
    
	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		int bID = 2;  /*2 is the block id for grass, so the structure going to spawn on grass*/
        if (world.getBlockId(i, j, k) != bID || world.getBlockId(i, j + 1, k) != 0 || world.getBlockId(i + 7, j, k) != bID || world.getBlockId(i + 7, j, k + 7) != bID || world.getBlockId(i, j, k + 7) != bID || world.getBlockId(i + 7, j + 1, k) != 0 || world.getBlockId(i + 7, j + 1, k + 7) != 0 || world.getBlockId(i, j + 1, k + 7) != 0) {
        	return false;
        }
        
        smallEntrance.generate(world, i + 0, j + 0, k + 0, true);
        //0, 40
        spiralstairs.generate(world, i + 2, j - 17, k + 3, true);
        //-17, 23
        largeHall.generate(world, i - 6, j - 30, k - 27, true);
        //-30, 10
        //everything is +40 so is in air
        return true;
	}
}