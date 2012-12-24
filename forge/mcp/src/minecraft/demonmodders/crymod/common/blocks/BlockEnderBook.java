package demonmodders.crymod.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.tileentities.TileEntityEnderbook;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderBook extends BlockCryMod {

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityEnderbook();
	}

	private static final int TOP_TEXTURE = 253;
	private static final int DEF_TEXTURE = 252;
	
	public BlockEnderBook(String blockName, int defaultId) {
		super(blockName, defaultId, 0, Material.wood);
	}
	
	@Override
	public int getBlockTextureFromSide(int side) {
		return side == 1 ? TOP_TEXTURE : DEF_TEXTURE;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		player.openGui(Crymod.instance, GuiType.ENDER_BOOK.getGuiId(), world, x, y, z);
		return true;
	}
}