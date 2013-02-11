package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.CORE;
import static demonmodders.crymod.common.items.CrystalType.GOLD;
import static demonmodders.crymod.common.items.CrystalType.MAGIC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.items.ItemSummoner.Type;

public class SummoningRecipeMobs extends SummoningRecipeByClass {

	public SummoningRecipeMobs(int id, Class<? extends EntityLiving> mob, Object... specialItems) {
		super(id, mob, specialItems, 
				CORE,
				GOLD, GOLD,
				CORE, MAGIC, CORE,
				GOLD, GOLD,
				CORE);
		setSummonerType(Type.EVIL_TABLET);
	}

	@Override
	public EntityLiving summon(EntityPlayer player, ContainerSummoner container) {
		container.decreaseChargeOnPage(2);		
		return super.summon(player, container);
	}

	@Override
	public int getColor() {
		return 0xff0000;
	}

	@Override
	public String getChatColorCode() {
		return "4";
	}
}