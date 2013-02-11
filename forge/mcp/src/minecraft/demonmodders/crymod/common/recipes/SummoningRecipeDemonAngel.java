package demonmodders.crymod.common.recipes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.entities.EntitySummonable;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.items.CrystalType;
import demonmodders.crymod.common.items.ItemSummoner;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketClientEffect.Type;

public class SummoningRecipeDemonAngel extends SummoningRecipe {
	private final Class<? extends EntitySummonable> demon;
	private final String demonName;
		
	public SummoningRecipeDemonAngel(int id, Class<? extends EntitySummonable> demon, String demonName, Object specialItem, CrystalType... crystals) {
		super(id, specialItem, crystals);
		
		this.demon = demon;
		this.demonName = demonName;
	}
	
	public Class<? extends EntitySummonable> getDemon() {
		return demon;
	}
	
	@Override
	public String getRecipeName() {
		return demonName;
	}

	@Override
	public int getColor() {
		return summonerType == ItemSummoner.Type.SUMMONING_BOOK ? 0x400040 : 0x660000;
	}

	@Override
	public String getChatColorCode() {
		return summonerType == ItemSummoner.Type.SUMMONING_BOOK ? "5" : "4";
	}

	@Override
	public EntityLiving summon(EntityPlayer player, ContainerSummoner container) {
		try {
			EntitySummonable entity = demon.getConstructor(World.class).newInstance(player.worldObj);
			entity.setOwner(player);
			
			container.decreaseChargeOnPage(entity.getRank());
			return entity;
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}
}
