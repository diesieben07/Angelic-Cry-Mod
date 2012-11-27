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

public class GenFeatureRuin extends WorldGenerator {
    
	private final SchematicWorldGenerator towerLarge = new SchematicWorldGenerator("ruin/towerLarge.schematic");
	   
	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		int bID = 2;  /*2 is the block id for grass, so the structure going to spawn on grass*/
        if (world.getBlockId(i, j, k) != bID || world.getBlockId(i, j + 1, k) != 0 || world.getBlockId(i + 7, j, k) != bID || world.getBlockId(i + 7, j, k + 7) != bID || world.getBlockId(i, j, k + 7) != bID || world.getBlockId(i + 7, j + 1, k) != 0 || world.getBlockId(i + 7, j + 1, k + 7) != 0 || world.getBlockId(i, j + 1, k + 7) != 0) {
        	return false;
        }
        towerLarge.generate(world, i + 0, j + 1, k + 0, true);
        
        world.setBlockWithNotify(i + 5, j + 2, k + 5, Block.mobSpawner.blockID);
        TileEntityMobSpawner var19 = (TileEntityMobSpawner)world.getBlockTileEntity(i + 5, j + 2, k + 5);
        if (var19 != null)
        {
            var19.setMobID(DungeonHooks.getRandomDungeonMob(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + i + ", " + j + ", " + k + ")");
        }
        
        world.setBlockWithNotify(i + 4, j + 8, k + 3, Block.chest.blockID);
    	TileEntityChest chest2 = new TileEntityChest();
        world.setBlockTileEntity(i + 4, j + 8, k + 3, chest2);
        Random random = new Random();
        for(int slot = 0; slot < chest2.getSizeInventory(); slot++){
                int num = random.nextInt(85);
               
                if (num == 1){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.ingotIron));
 
                }
                if (num == 2){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.ingotGold));
 
                }
               
                if (num == 3){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.bone));
 
                }
               
                if (num == 4){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.book));
 
                }
                if (num == 5){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.rottenFlesh));
       
 
            
                }
                if (num == 7){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.bread));
                
                }
                if (num == 8){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.emerald));
                
                }
                if (num == 9){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.slimeBall));
                
                }
                if (num == 10){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.expBottle));
                }
                if (num == 11){
                chest2.setInventorySlotContents(slot, new ItemStack(Item.enderPearl));
       
               
                }
        
        }
		return true;
	}
        
        private String pickMobSpawner(Random par1Random)
        {
            int var2 = par1Random.nextInt(4);
            return var2 == 0 ? "Skeleton" : (var2 == 1 ? "Zombie" : (var2 == 2 ? "Blaze" : (var2 == 3 ? "Spider" : "")));
        }
}