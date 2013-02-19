package demonmodders.crymod.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.container.ContainerEnderBook;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.tileentities.TileEntityEnderbook;

public class BlockEnderBook extends BlockCryMod {

	private static final int TOP_TEXTURE = 235;
	private static final int BOTTOM_TEXTURE = 236;
	private static final int DEF_TEXTURE = 251;
	
	public BlockEnderBook(String blockName, int defaultId) {
		super(blockName, defaultId, 0, Material.rock);
		setBlockBounds(0, 0, 0, 1, 0.75F, 1);
		setLightOpacity(0);
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityEnderbook();
	}
	
	@Override
	public int getBlockTextureFromSide(int side) {
		return side == 1 ? TOP_TEXTURE : side == 0 ? BOTTOM_TEXTURE : DEF_TEXTURE;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			player.openGui(Crymod.instance, GuiType.ENDER_BOOK.getGuiId(), world, x, y, z);
		}
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}