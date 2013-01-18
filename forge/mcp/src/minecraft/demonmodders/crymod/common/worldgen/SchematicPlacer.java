package demonmodders.crymod.common.worldgen;

import java.io.InputStream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import demonmodders.crymod.common.core.CrymodCore;

/**
 * Can place and rotate a .schematic file at a specified position in a minecraft world
 *
 */
public class SchematicPlacer {

	private final byte[] blockIds;
	private final byte[] blockMeta;
	private final short width;
	private final short length;
	private final short height;
	private final NBTTagList tileEntities;
	
	public SchematicPlacer(InputStream inStream) {
		try {
			NBTTagCompound nbt = CompressedStreamTools.readCompressed(inStream);
			blockIds = nbt.getByteArray("Blocks");
			blockMeta = nbt.getByteArray("Data");
			width = nbt.getShort("Width");
			length = nbt.getShort("Length");
			height = nbt.getShort("Height");
			tileEntities = nbt.getTagList("TileEntities");
		} catch (Exception e) {
			throw new RuntimeException("Failed to load Schematic!", e);
		}
	}
	
	public int getSizeY() {
		return height;
	}
	
	public int getRotatedSizeX(Rotation rotation) {
		return rotation == Rotation.NONE || rotation == Rotation.R180 ? width : length;
	}
	
	public int getRotatedSizeZ(Rotation rotation) {
		return rotation == Rotation.NONE || rotation == Rotation.R180 ? length : width;
	}
	
	public AxisAlignedBB getBoundingBox(int x, int y, int z, Rotation rotation) {
		return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(x, y, z, getRotatedSizeX(rotation), getSizeY(), getRotatedSizeZ(rotation));
	}
	
	public void place(Rotation rotation, World world, int posX, int posY, int posZ, boolean spawnairblocks) {
		placeBlockType(rotation, world, posX, posY, posZ, spawnairblocks, false);
		placeBlockType(rotation, world, posX, posY, posZ, spawnairblocks, true);
	}

	private void placeBlockType(Rotation rotation, World world, int posX, int posY, int posZ, boolean spawnairblocks, boolean spawnFullBlocks) {
		int xOffset = 0;
		int yOffset = 0;
		int zOffset = 0;
		for (int i = 0; i < blockIds.length; i++) {
			
			if (!isBlockType(spawnFullBlocks, blockIds[i])) {
				return;
			}
			
			int[] rotXz = rotateXZ(xOffset, zOffset, rotation);
			
			if (blockIds[i] != 0 && !spawnairblocks || spawnairblocks) {
				world.setBlockAndMetadata(posX + rotXz[0], posY + yOffset, posZ + rotXz[1], blockIds[i], applyRotationToMeta(blockIds[i], blockMeta[i], rotation));
			}
			
			if (xOffset < width - 1) {
				xOffset++;
			} else if (xOffset >= width - 1 && zOffset < length - 1) {
				xOffset = 0;
				zOffset++;
			} else if (xOffset >= width - 1 && zOffset >= length - 1 && yOffset < height - 1) {
				xOffset = 0;
				zOffset = 0;
				yOffset++;
			}
		}
		
		// tile entities
		for (int i = 0; i < tileEntities.tagCount(); i++) {
			NBTTagCompound entityCompound = (NBTTagCompound)tileEntities.tagAt(i).copy();
			int[] rotXz = rotateXZ(entityCompound.getInteger("x"), entityCompound.getInteger("z"), rotation);
			int y = entityCompound.getInteger("y");
			y += posY;
			rotXz[0] += posX;
			rotXz[1] += posZ;
			entityCompound.setInteger("y", y);
			entityCompound.setInteger("x", rotXz[0]);
			entityCompound.setInteger("z", rotXz[1]);
			world.getBlockTileEntity(rotXz[0], y, rotXz[1]).readFromNBT(entityCompound);
		}
	}
	
	private static final boolean[] BLOCKS_2ND_PASS = new boolean[256];
	
	static {
		BLOCKS_2ND_PASS[Block.torchRedstoneActive.blockID] = true;
		BLOCKS_2ND_PASS[Block.torchRedstoneIdle.blockID] = true;
		BLOCKS_2ND_PASS[Block.torchWood.blockID] = true;
		BLOCKS_2ND_PASS[Block.ladder.blockID] = true;
		BLOCKS_2ND_PASS[Block.rail.blockID] = true;
		BLOCKS_2ND_PASS[Block.railDetector.blockID] = true;
		BLOCKS_2ND_PASS[Block.railPowered.blockID] = true;
		BLOCKS_2ND_PASS[Block.lever.blockID] = true;
		BLOCKS_2ND_PASS[Block.tripWireSource.blockID] = true;
		BLOCKS_2ND_PASS[Block.redstoneWire.blockID] = true;
		BLOCKS_2ND_PASS[Block.redstoneRepeaterIdle.blockID] = true;
		BLOCKS_2ND_PASS[Block.redstoneRepeaterActive.blockID] = true;
		BLOCKS_2ND_PASS[Block.trapdoor.blockID] = true;
		BLOCKS_2ND_PASS[Block.plantYellow.blockID] = true;
		BLOCKS_2ND_PASS[Block.plantRed.blockID] = true;
		BLOCKS_2ND_PASS[Block.mushroomBrown.blockID] = true;
		BLOCKS_2ND_PASS[Block.mushroomRed.blockID] = true;
		BLOCKS_2ND_PASS[Block.tallGrass.blockID] = true;
		BLOCKS_2ND_PASS[Block.cactus.blockID] = true;
	}
	
	private boolean isBlockType(boolean spawnFullBlocks, byte blockId) {
		return BLOCKS_2ND_PASS[blockId] == !spawnFullBlocks;
	}

	private final int[] rotateXZ(int xOffset, int zOffset, Rotation rotation) {
		int[] xzRot = new int[2];
		if (rotation == Rotation.R180) {
			xzRot[0] = width - 1 - xOffset;
			xzRot[1] = length - 1 - zOffset;
		} else if (rotation == Rotation.R90) {
			xzRot[0] = length - 1 - zOffset;
			xzRot[1] = xOffset;
		} else if (rotation == Rotation.R270) {
			xzRot[0] = length - 1 - (length - 1 - zOffset);
			xzRot[1] = width - 1 - xOffset;
		} else {
			xzRot[0] = xOffset;
			xzRot[1] = zOffset;
		}
		return xzRot;
	}
	
	private final int applyRotationToMeta(int blockId, int meta, Rotation rotation) {
		switch (rotation) {
		case R180:
			if (blockId == Block.vine.blockID) {
				return (meta & 1) * 4 + (meta & 2) * 4 + (meta & 4) / 4 + (meta & 8) / 4;
			} else if (Block.blocksList[blockId] != null && Block.blocksList[blockId] instanceof BlockStairs) {
				int metaDirection = meta & 3;
				int metaUpside = meta & 4;
				return (metaDirection == 0 ? 1 : metaDirection == 1 ? 0 : metaDirection == 2 ? 3 : 2) + metaUpside;
			} else if (blockId == Block.signWall.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.ladder.blockID || blockId == Block.dispenser.blockID || blockId == Block.chest.blockID) {
				return meta == 2 ? 3 : meta == 3 ? 2 : meta == 5 ? 4 : 5;
			}
			break;
		case R90:
			if (blockId == Block.vine.blockID) {
				return (meta & 1) * 2 + (meta & 2) * 2 + (meta & 4) * 2 + (meta & 8) / 8;
			} else if (Block.blocksList[blockId] != null && Block.blocksList[blockId] instanceof BlockStairs) {
				int metaDirection = meta & 3;
				int metaUpside = meta & 4;
				return (metaDirection == 0 ? 2 : metaDirection == 1 ? 3 : metaDirection == 2 ? 1 : 0) + metaUpside;
			} else if (blockId == Block.signWall.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.ladder.blockID || blockId == Block.dispenser.blockID || blockId == Block.chest.blockID) {
				return meta == 2 ? 5 : meta == 3 ? 4 : meta == 4 ? 3 : 2;
			}
			break;
		case R270:
			if (blockId == Block.vine.blockID) {
				return (meta & 1) * 8 + (meta & 2) / 2 + (meta & 4) / 2 + (meta & 8) / 2;
			} else if (Block.blocksList[blockId] != null && Block.blocksList[blockId] instanceof BlockStairs) {
				int metaDirection = meta & 3;
				int metaUpside = meta & 4;
				return (metaDirection == 0 ? 3 : metaDirection == 1 ? 2 : metaDirection == 2 ? 0 : 1) + metaUpside;
			} else if (blockId == Block.signWall.blockID || blockId == Block.stoneOvenIdle.blockID || blockId == Block.ladder.blockID || blockId == Block.dispenser.blockID || blockId == Block.chest.blockID) {
				return meta == 2 ? 4 : meta == 3 ? 5 : meta == 4 ? 2 : 3;
			}
			break;
		default:
			break;
		}
		return meta;
	}
}