package demonmodders.crymod.common.inventory;

import demonmodders.crymod.common.entities.EntitySummonable;
import net.minecraft.entity.player.EntityPlayer;

public class InventorySummonable extends AbstractInventory {

	private final EntitySummonable entity;
	
	public InventorySummonable(EntitySummonable entity) {
		super(true);
		this.entity = entity;
	}
	
	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public String getInvName() {
		return entity.getEntityName();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return entity.getDistanceSqToEntity(player) <= 64;
	}

}
