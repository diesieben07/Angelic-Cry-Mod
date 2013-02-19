package demonmodders.crymod.common.handlers.event;

import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.quest.Quest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public final class QuestEventHandler {

	private QuestEventHandler() { }
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new QuestEventHandler());
	}
	
	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent event) {
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource)event.source;
			if (source.getEntity() instanceof EntityPlayerMP && !(event.entity instanceof EntityPlayer)) {
				EntityPlayerMP player = (EntityPlayerMP)source.getEntity();
				PlayerInformation info = PlayerInformation.forPlayer(player);
				for (Quest quest : info.getActiveQuests()) {
					quest.onKillEntity(event.entityLiving);
				}
			}
		}
	}
	
}