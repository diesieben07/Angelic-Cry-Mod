package demonmodders.crymod.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.tileentities.TileEntityMagiciser;

public class BlockMagiciser extends BlockCryMod {

	public BlockMagiciser(String blockName, int defaultId) {
		super(blockName, defaultId, 0, Material.rock);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMagiciser();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		player.openGui(Crymod.instance, GuiType.MAGICISER.getGuiId(), world, x, y, z);
		return true;
	}
}