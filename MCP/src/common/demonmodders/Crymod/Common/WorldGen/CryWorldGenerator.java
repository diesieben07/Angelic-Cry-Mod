package demonmodders.Crymod.Common.WorldGen;
 
import java.util.Random;


 
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenOcean;
import net.minecraft.src.BiomeGenPlains;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
 
public class CryWorldGenerator implements IWorldGenerator{
 
        public void generate(Random random, int chunkX, int chunkZ, World world,  IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
               
               
                switch(world.provider.dimensionId){
               
                case -1: generateNether(world, random, chunkX*16, chunkZ*16);
                case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
               
                }
        }
       
       
        private void generateNether(World world, Random random, int i, int j) {
			// TODO Auto-generated method stub
			
		}


		public void generateSurface(World world, Random random, int blockX, int blockZ)
        {BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(blockX, blockZ);
                {     
                	
                	
                	
                    for(int k = 0; k < 1; k++)
                    {
                        int RandPosX = blockX + random.nextInt(16);
                        int RandPosY = random.nextInt(128);
                        int RandPosZ = blockZ + random.nextInt(16);
                        (new GenDungeonLarge()).generate(world, random, RandPosX, RandPosY, RandPosZ);
                    }	
                	
                }
       
     
            
               
        }
        }
       
       
