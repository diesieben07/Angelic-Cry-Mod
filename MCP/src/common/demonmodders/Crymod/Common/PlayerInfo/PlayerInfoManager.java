package demonmodders.Crymod.Common.PlayerInfo;

import net.minecraft.src.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public final class PlayerInfoManager implements IPlayerTracker {
	private PlayerInfoManager() { }
	
	public static void init() {
		
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
	}
	
	@Override
	public void onPlayerChangedDimension(EntityPlayer player) { }
}
