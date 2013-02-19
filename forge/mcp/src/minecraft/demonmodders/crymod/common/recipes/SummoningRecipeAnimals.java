package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.*;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.container.ContainerSummoner;

public class SummoningRecipeAnimals extends SummoningRecipeByClass {

	public SummoningRecipeAnimals(int id, Class<? extends EntityLiving> animal, Object... specialItems) {
		super(id, animal, specialItems,
				LEAF, 
				LEAF, LEAF,
				LEAF, LOVE, LEAF,
				LEAF, LEAF,
				LEAF);
	}

	@Override
	public EntityLiving summon(EntityPlayer player, ContainerSummoner container) {
		container.decreaseChargeOnPage(1);
		return super.summon(player, container);
	}

	@Override
	public int getColor() {
		return 0x007700;
	}

	@Override
	public String getChatColorCode() {
		return "2";
	}
}