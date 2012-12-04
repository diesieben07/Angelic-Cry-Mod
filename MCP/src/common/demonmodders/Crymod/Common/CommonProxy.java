package demonmodders.Crymod.Common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import demonmodders.Crymod.Common.Network.PacketClientEffect;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

public class CommonProxy {
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}

	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		// NO OP on server
		return null;
	}
	
	public void setClientPlayerInfo(PlayerInfo info) {
		// NO OP on server
	}

	public void handleClientEffect(PacketClientEffect clientEffect) {
		// NO OP on server
	}
}
