package demonmodders.crymod.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.tileentities.TileEntityInventory;

public class BlockCryMod extends Block {
	
	public static Block rechargeStation;
	public static Block enderBook;
	
	public static void createBlocks() {
		rechargeStation = new BlockRechargeStation("rechargeStation", 3956).setRequiresSelfNotify();
		enderBook = new BlockEnderBook("enderBook", 3957);
	}
	
	private final Random rand = new Random();

	public BlockCryMod(String blockName, int defaultId, int texture, Material material) {
		super(Crymod.conf.getBlock(blockName + "Id", defaultId).getInt(), texture, material);
		setBlockName("crymod_" + blockName);
		setTextureFile(Crymod.TEXTURE_FILE);
		setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(this, blockName);
	}
	
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
                                itemEntity.func_92014_d().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
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
}