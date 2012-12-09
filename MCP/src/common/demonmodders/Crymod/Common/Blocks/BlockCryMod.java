package demonmodders.Crymod.Common.Blocks;

import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.TileEntities.TileEntityInventory;

public class BlockCryMod extends Block {
	
	private final Random rand = new Random();
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int meta) {
		if (hasTileEntity(meta) && world.getBlockTileEntity(x, y, z) instanceof TileEntityInventory) {
			TileEntityInventory te = (TileEntityInventory)world.getBlockTileEntity(x, y, z);

            if (te != null) {
                for (int slot = 0; slot < te.getSizeInventory(); slot++) {
                    ItemStack stack = te.getStackInSlot(slot);

                    if (stack != null) {
                        float randomPositionX = rand.nextFloat() * 0.8F + 0.1F;
                        float randomPositionY = rand.nextFloat() * 0.8F + 0.1F;
                        float randomPositionZ = rand.nextFloat() * 0.8F + 0.1F;

                        while (stack.stackSize > 0) {
                            int partialStackSize = rand.nextInt(21) + 10;

                            if (partialStackSize > stack.stackSize) {
                                partialStackSize = stack.stackSize;
                            }

                            stack.stackSize -= partialStackSize;
                            EntityItem itemEntity = new EntityItem(world, x + randomPositionX, y + randomPositionY, z + randomPositionZ, new ItemStack(stack.itemID, partialStackSize, stack.getItemDamage()));

                            if (stack.hasTagCompound()) {
                                itemEntity.item.setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
                            }

                            float motionMultiplier = 0.05F;
                            itemEntity.motionX = rand.nextGaussian() * motionMultiplier;
                            itemEntity.motionY = rand.nextGaussian() * motionMultiplier + 0.2F;
                            itemEntity.motionZ = rand.nextGaussian() * motionMultiplier;
                            world.spawnEntityInWorld(itemEntity);
                        }
                    }
                }
            }
		}
		super.breakBlock(world, x, y, z, blockId, meta);
	}

	public static Block rechargeStation;
	
	public static void createBlocks() {
		rechargeStation = new BlockRechargeStation(Crymod.conf.getBlock("rechargeStationId", 3956).getInt()).setBlockName("rechargeStation");
		GameRegistry.registerBlock(rechargeStation);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return CreativeTabs.tabMisc;
	}

	public BlockCryMod(int blockId, int texture, Material material) {
		super(blockId, texture, material);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
}