package demonmodders.crymod.common;

import java.util.List;

import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.worldgen.StructurePosition;

public class EventHandler {
	
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
}