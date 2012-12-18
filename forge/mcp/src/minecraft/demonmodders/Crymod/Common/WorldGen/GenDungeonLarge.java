package demonmodders.Crymod.Common.WorldGen;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenDungeonLarge extends WorldGenerator {
    
	private final SchematicWorldGenerator smallEntrance = new SchematicWorldGenerator("dungeonLarge/smallEntrance.schematic");
	private final SchematicWorldGenerator spiralstairs = new SchematicWorldGenerator("dungeonLarge/spiralstairs.schematic");
	private final SchematicWorldGenerator largeHall = new SchematicWorldGenerator("dungeonLarge/largeHall.schematic");
	private final SchematicWorldGenerator stairs = new SchematicWorldGenerator("dungeonLarge/stairs.schematic");
	private final SchematicWorldGenerator room9 = new SchematicWorldGenerator("dungeonLarge/room9.schematic");
	private final SchematicWorldGenerator lavaHall = new SchematicWorldGenerator("dungeonLarge/lavaHall.schematic");
	    
	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		int bID = 2;  /*2 is the block id for grass, so the structure going to spawn on grass*/
        if (world.getBlockId(i, j, k) != bID || world.getBlockId(i, j + 1, k) != 0 || world.getBlockId(i + 7, j, k) != bID || world.getBlockId(i + 7, j, k + 7) != bID || world.getBlockId(i, j, k + 7) != bID || world.getBlockId(i + 7, j + 1, k) != 0 || world.getBlockId(i + 7, j + 1, k + 7) != 0 || world.getBlockId(i, j + 1, k + 7) != 0) {
        	return false;
        }
        
        smallEntrance.generate(world, i + 0, j + 40, k + 0, true);
        //0, 40
        spiralstairs.generate(world, i + 2, j + 23, k + 3, true);
        //-17, 23
        largeHall.generate(world, i - 6, j + 10, k - 27, true);
        //-30, 10
        room9.generate(world, i + 15, j + 12, k - 19, true);
        //-28, 12
        stairs.generate(world, i + 15, j + 12, k + 11, true);
        //-28, 12 31 (19)
        lavaHall.generate(world, i + 15, j + 12, k + 22, true);
        
        
        //everything is +40 so is in air
        return true;
	}
}