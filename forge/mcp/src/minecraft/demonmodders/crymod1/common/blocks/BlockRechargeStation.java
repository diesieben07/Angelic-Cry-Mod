package demonmodders.crymod1.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod1.common.Crymod;
import demonmodders.crymod1.common.gui.GuiType;
import demonmodders.crymod1.common.tileentities.TileEntityRechargeStation;

public class BlockRechargeStation extends BlockCryMod {

	private static final int TOP_BOTTOM_TEXTURE = 14 * 16 + 13;
	private static final int FRONT_TEXTURE = 14 * 16 + 15;
	private static final int FRONT_TEXTURE_ACTIVE = 14 * 16 + 14;
	private static final int SIDES_TEXTURE = 15 * 16 + 15;
	
	public BlockRechargeStation(String blockName, int defaultId) {
		super(blockName, defaultId, 0, Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if (side < 2) {
			return TOP_BOTTOM_TEXTURE;
		}
		boolean isActive = (blockAccess.getBlockMetadata(x, y, z) & 8) == 8;
		int meta = (blockAccess.getBlockMetadata(x, y, z) & 7);
		return meta == side ? (isActive ? FRONT_TEXTURE_ACTIVE : FRONT_TEXTURE) : SIDES_TEXTURE;
	}

	@Override
	public int getBlockTextureFromSide(int side) {
		if (side < 2) {
			return TOP_BOTTOM_TEXTURE;
		}
		return side == 3 ? FRONT_TEXTURE : SIDES_TEXTURE;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living) {
		int direction = MathHelper.floor_double((living.rotationYaw * 4 / 360) + 0.5) & 3;
		int meta = 0;
		switch (direction) {
		case 0: // player is facing south
			meta = 2;
			break;
		case 1: // player is facing west
			meta = 5;
			break;
		case 2: // player is facing north
			meta = 3;
			break;
		case 3: // player is facing east
			meta = 4;
			break;
		}
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!player.isSneaking()) {
			player.openGui(Crymod.instance, GuiType.RECHARGE_STATION.getGuiId(), world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityRechargeStation();
	}
}