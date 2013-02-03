package demonmodders.crymod.common;

import java.io.File;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.UpdateChecker.UpdateStatus;
import demonmodders.crymod.common.UpdateChecker.UpdateStatusHandler;
import demonmodders.crymod.common.network.PacketClientAction;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketUpdateInformation;

public class ServerProxy implements CrymodProxy, IPlayerTracker, UpdateStatusHandler {
	
	private static final int SECS_IN_24H = 60 * 60 * 24;
	
	public UpdateStatus lastStatus = UpdateStatus.LOADING;
	public List<String> lastUpdateInformation = null;

	@Override
	public void preInit() {
		GameRegistry.registerPlayerTracker(this);
		Crymod.updater.registerHandler(this);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void postInit() {
		
	}

	@Override
	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		// NO OP on server
		return null;
	}
	
	@Override
	public void handleClientEffect(PacketClientEffect clientEffect) {
		// NO OP on server
	}
	
	@Override
	public File getMinecraftDir() {
		return MinecraftServer.getServer().getFile(".");
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		if (MinecraftServer.getServer().getConfigurationManager().getOps().contains(player.username.toLowerCase())) {
			Crymod.updater.startIfNotRunning();
			player.sendChatToPlayer(CrymodUtils.getChatMessage("Use " + CrymodUtils.color("/summoners update", "6", "f") +  " to check for updates."));
		}
	}
	
	@Override
	public void handleStatus(UpdateStatus newStatus, List<String> updateInfo) {
		lastStatus = newStatus;
		lastUpdateInformation = updateInfo;
		
		new PacketUpdateInformation(newStatus, updateInfo).sendToOps();
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) { }

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) { }

	@Override
	public void onPlayerRespawn(EntityPlayer player) { }

	@Override
	public void handleUpdateInformation(PacketUpdateInformation packet) { }

	@Override
	public void handleClientAction(PacketClientAction packet) { }
}