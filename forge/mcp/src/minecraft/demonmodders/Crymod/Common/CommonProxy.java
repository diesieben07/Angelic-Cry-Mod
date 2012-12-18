package demonmodders.crymod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.playerinfo.PlayerInfo;

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
