package demonmodders.Crymod.Common.WorldGen;

import java.io.InputStream;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

/**
 * Class that makes structure generation easier
 * @author Resinresin
 *
 */

public class resinresinLoader {

 public byte[] blocks;
 public byte[] datablocks;
 public short width;
 public short length;
 public short height;
 public short[] sizes;
 
 public resinresinLoader(String path) {
  blocks = null;
  datablocks = null;
  width = 0;
  length = 0;
  height = 0;
  loadResinresin(path);
 }

 
 public void loadResinresin(String path) {
  try {
   InputStream inputstream = resinresinLoader.class.getResourceAsStream("/crymodResource/resinresin/" + path);
   NBTTagCompound nbt = CompressedStreamTools.readCompressed(inputstream);
   
   blocks = nbt.getByteArray("Blocks");
   datablocks = nbt.getByteArray("Data");
   width = nbt.getShort("Width");
   length = nbt.getShort("Length");
   height = nbt.getShort("Height");
   sizes = new short[] {width, length, height};
  } catch(Exception e) {
   e.printStackTrace();
  }
 }
 
 public void generate(World world, int posX, int posY, int posZ, boolean spawnairblocks) {
  try {
   int xnum = 0;
   int ynum = 0;
   int znum = 0;
   for (int i = 0; i < blocks.length; i++) {
    if(((blocks[i] != 0) && (!spawnairblocks)) || (spawnairblocks == true)) {
     world.setBlockAndMetadata(posX + xnum, posY + ynum, posZ + znum, blocks[i], datablocks[i]);
    }
    
    if(xnum < width - 1) {
     xnum++;
    } else if((xnum >= width - 1) && (znum < length - 1)) {
     xnum = 0;
     znum++;
    } else if((xnum >= width - 1) && (znum >= length - 1) && (ynum < height - 1)) {
     xnum = 0;
     znum = 0;
     ynum++;
    } 
   }
  } catch(Exception e) {
   e.printStackTrace();
  }
 }
}