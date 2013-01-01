package demonmodders.crymod.common.tileentities;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMagiciser extends TileEntityInventory {

	@Override
	public int getSizeInventory() {
		return 7;
	}

	@Override
	public String getInvName() {
		return "crymod_magiciser";
	}

}
