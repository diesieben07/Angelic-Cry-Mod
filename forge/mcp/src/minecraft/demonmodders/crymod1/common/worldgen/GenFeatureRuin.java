package demonmodders.crymod1.common.worldgen;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
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
       
        TileEntityMobSpawner teMobSpawner = (TileEntityMobSpawner)world.getBlockTileEntity(i + 5, j + 2, k + 5);
        if (teMobSpawner != null) {
            teMobSpawner.setMobID(DungeonHooks.getRandomDungeonMob(rand));
        } else {
            System.err.println("Failed to fetch mob spawner entity at (" + i + ", " + j + ", " + k + ")");
        }
        
        world.setBlockWithNotify(i + 4, j + 8, k + 3, Block.chest.blockID);
    	TileEntityChest teChest = new TileEntityChest();
        world.setBlockTileEntity(i + 4, j + 8, k + 3, teChest);
        for (int slot = 0; slot < teChest.getSizeInventory(); slot++) {
        	
        	switch (rand.nextInt(85)) {
        	case 1:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.ingotIron));
        		break;
        	case 2:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.ingotGold));
        		break;
        	case 3:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.bone));
        		break;
        	case 4:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.book));
        		break;
        	case 5:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.rottenFlesh));
        		break;
        	case 6:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.bread));
        		break;
        	case 7:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.emerald));
        		break;
        	case 8:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.slimeBall));
        		break;
        	case 9:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.expBottle));
        		break;
        	case 10:
        		teChest.setInventorySlotContents(slot, new ItemStack(Item.enderPearl));
        		break;
        	}
        }
        return true;
	}
}