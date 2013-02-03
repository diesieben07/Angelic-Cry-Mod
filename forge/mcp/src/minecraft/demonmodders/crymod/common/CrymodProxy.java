package demonmodders.crymod.common;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import demonmodders.crymod.common.network.PacketClientAction;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketUpdateInformation;

public interface CrymodProxy {
	public void preInit();

	public void init();

	public void postInit();

	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z);

	public void handleClientEffect(PacketClientEffect clientEffect);

	public File getMinecraftDir();
	
	public void handleUpdateInformation(PacketUpdateInformation packet);
	
	public void handleClientAction(PacketClientAction packet);
}