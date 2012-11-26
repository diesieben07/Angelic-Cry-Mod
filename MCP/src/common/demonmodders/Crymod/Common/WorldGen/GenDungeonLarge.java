
package demonmodders.Crymod.Common.WorldGen;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraftforge.common.DungeonHooks;
public class GenDungeonLarge extends WorldGenerator
{
    public GenDungeonLarge()
    {
    }
    

    resinresinLoader smallEntrance = new resinresinLoader("smallEntrance.resinresin");
    resinresinLoader spiralstairs = new resinresinLoader("spiralstairs.resinresin");
    resinresinLoader largeHall = new resinresinLoader("largeHall.resinresin");
    
    public boolean generate(World world, Random rand, int i, int j, int k)
    {
int bID = 2;  /*2 is the block id for grass, so the structure going to spawn on grass*/
        if(world.getBlockId(i, j, k) != bID || world.getBlockId(i, j + 1, k) != 0 || world.getBlockId(i + 7, j, k) != bID || world.getBlockId(i + 7, j, k + 7) != bID || world.getBlockId(i, j, k + 7) != bID || world.getBlockId(i + 7, j + 1, k) != 0 || world.getBlockId(i + 7, j + 1, k + 7) != 0 || world.getBlockId(i, j + 1, k + 7) != 0)
        {
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
