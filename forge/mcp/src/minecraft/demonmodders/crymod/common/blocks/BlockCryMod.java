package demonmodders.crymod.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.tileentities.TileEntityInventory;

public class BlockCryMod extends Block {
	
	public static Block rechargeStation;
	public static Block enderBook;
	public static Block magiciser;
	public static Block plants;
	
	public static void createBlocks() {
		rechargeStation = new BlockRechargeStation("rechargeStation", 3956).setRequiresSelfNotify();
		enderBook = new BlockEnderBook("enderBook", 3957);
		magiciser = new BlockMagiciser("magiciser", 3958);
		plants = new BlockCrymodPlants("plants", 3959);
	}
	
	private final Random rand = new Random();

	public BlockCryMod(String blockName, int defaultId, int texture, Material material) {
		super(getBlockId(blockName, defaultId), texture, material);
		initCrymodBlock(this, blockName, ItemBlock.class);
	}
	
	public static void initCrymodBlock(Block block, String blockName, Class<? extends ItemBlock> itemClass) {
		block.setBlockName("crymod_" + blockName);
		block.setTextureFile(CrymodUtils.TEXTURE_FILE);
		block.setCreativeTab(Crymod.mainTab);
		GameRegistry.registerBlock(block, itemClass, blockName);
	}
	
	public static int getBlockId(String blockName, int defaultId) {
		return Crymod.conf.getBlock(blockName + "Id", defaultId).getInt();
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