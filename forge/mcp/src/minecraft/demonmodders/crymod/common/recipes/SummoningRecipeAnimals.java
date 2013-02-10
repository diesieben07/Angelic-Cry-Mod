package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.LEAF;
import static demonmodders.crymod.common.items.CrystalType.LOVE;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.items.CrystalType;

public class SummoningRecipeAnimals extends SummoningRecipe {

	private final Class<? extends EntityAnimal> animal;
	private final int entityId;
	
	public SummoningRecipeAnimals(int id, Class<? extends EntityAnimal> animal, Object... specialItem) {
		super(id, CrymodUtils.getItemStacks(specialItem),
				LEAF, 
				LEAF, LEAF,
				LEAF, LOVE, LEAF,
				LEAF, LEAF,
				LEAF);
		this.animal = animal;
		this.entityId = ReflectionHelper.<HashMap<Class<? extends Entity>, Integer>,EntityList>getPrivateValue(EntityList.class, null, 3).get(animal);
	}

	@Override
	public EntityLiving summon(EntityPlayer player, ContainerSummoner container) {
		try {
			EntityLiving entity = animal.getConstructor(World.class).newInstance(player.worldObj);
			container.decreaseChargeOnPage(1);
			return entity;
		} catch (ReflectiveOperationException e) {
			return null;
		}
	}

	@Override
	public String getRecipeName() {
		return StringTranslate.getInstance().translateKey("entity." + EntityList.getStringFromID(entityId) + ".name");
	}
}