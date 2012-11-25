package demonmodders.Crymod.Common;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.Crymod.Common.Network.PacketPlayerKarma;

import net.minecraft.src.EntityPlayer;

/**
 * Class that manages player karma on the server
 * @author Take
 *
 */
public class PlayerKarmaManager implements IPlayerTracker {
	
	private PlayerKarmaManager() {
		GameRegistry.registerPlayerTracker(this);
	}
	
	private static final PlayerKarmaManager instance = new PlayerKarmaManager();
	
	public static PlayerKarmaManager instance() {
		return instance;
	}
	
	public static void init() {}
	
	public static final int MAX_KARMA_VALUE = 50;
	
	private Map<EntityPlayer, PlayerKarma> karmas = new HashMap<EntityPlayer, PlayerKarma>();
	
	public PlayerKarma getPlayerKarma(EntityPlayer player) {
		return karmas.get(player);
	}
	
	public void updateClientKarma(EntityPlayer player) {
		if (player == null) {
			return;
		}
		new PacketPlayerKarma(karmas.get(player)).sentToPlayer(player);
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {		
		karmas.put(player, new PlayerKarma());
		// TODO: load it
		updateClientKarma(player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		karmas.remove(player);
		// TODO: save it
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) { }

	@Override
	public void onPlayerRespawn(EntityPlayer player) { }
}