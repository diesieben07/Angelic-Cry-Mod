package demonmodders.crymod.common.handlers.event;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.worldgen.StructurePosition;

public final class GenericEventHandler implements IPlayerTracker {
	
	private GenericEventHandler() { }
	
	public static void init() {
		GenericEventHandler handler = new GenericEventHandler();
		
		MinecraftForge.EVENT_BUS.register(handler);
		GameRegistry.registerPlayerTracker(handler);
	}
	
	@ForgeSubscribe
	public void onItemToss(ItemTossEvent evt) {
		if (evt.entityItem.func_92014_d().itemID == ItemCryMod.recipePage.itemID) {
			CrymodUtils.getEntityData(evt.entityItem).setString("tossedBy", evt.player.username);
		}
	}
	
	@ForgeSubscribe
	public void onChunkDataLoad(ChunkDataEvent.Load evt) {
		StructurePosition.loadChunkStructures(evt.getChunk(), evt.getData());
	}
	
	@ForgeSubscribe
	public void onChunkDataSave(ChunkDataEvent.Save evt) {
		StructurePosition.saveChunkStructures(evt.getChunk(), evt.getData());
	}
	
	@ForgeSubscribe
	public void onChunkUnload(ChunkEvent.Unload evt) {
		if (!evt.world.isRemote) {
			StructurePosition.unloadChunkStructures(evt.getChunk());
		}
	}
	
	@ForgeSubscribe
	public void onWorldUnload(WorldEvent.Unload evt) {
		if (evt.world instanceof WorldServer) {
			WorldServer world = (WorldServer)evt.world;
			for (Chunk chunk : ReflectionHelper.<List<Chunk>,ChunkProviderServer>getPrivateValue(ChunkProviderServer.class, world.theChunkProviderServer, 6)) {
				StructurePosition.unloadChunkStructures(chunk);
			}
		}
	}
	
	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		PlayerInformation.forPlayer(player).setDirty();System.out.println("changed dimension: " + player);
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		PlayerInformation.forPlayer(player).setDirty();System.out.println("respawn: " + player);
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) { }

	@Override
	public void onPlayerLogout(EntityPlayer player) { }
}