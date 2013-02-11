package demonmodders.crymod.common.recipes;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import demonmodders.crymod.common.items.CrystalType;
import static demonmodders.crymod.common.items.CrystalType.CORE;
import static demonmodders.crymod.common.items.CrystalType.GOLD;
import static demonmodders.crymod.common.items.CrystalType.MAGIC;

public class SummoningRecipeNetherMobs extends SummoningRecipeByClass {

	protected SummoningRecipeNetherMobs(int id, Class<? extends EntityLiving> mob, Object specialItem) {
		super(id, mob, specialItem, 
				CORE,
				CORE, CORE,
				CORE, MAGIC, CORE,
				CORE, CORE,
				CORE);
	}

	@Override
	public boolean canSummon(EntityPlayer player) {
		// blaze & ghast can only be summoned in the nether
		return super.canSummon(player) && (mob.isAssignableFrom(EntityBlaze.class) || mob.isAssignableFrom(EntityGhast.class) ? player.worldObj.provider.dimensionId == -1 : true);
	}

	@Override
	public int getColor() {
		return 0x202020;
	}

	@Override
	public String getChatColorCode() {
		return "e";
	}
}