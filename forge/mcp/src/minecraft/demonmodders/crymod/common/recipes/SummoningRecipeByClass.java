package demonmodders.crymod.common.recipes;

import java.util.HashMap;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.items.CrystalType;

public abstract class SummoningRecipeByClass extends SummoningRecipe {

	protected final Class<? extends EntityLiving> mob;
	private int entityId;
	
	protected SummoningRecipeByClass(int id, Class<? extends EntityLiving> mob, Object specialItem, CrystalType... crystals) {
		super(id, specialItem, crystals);
		this.mob = mob;
		initId();
	}

	protected SummoningRecipeByClass(int id, Class<? extends EntityLiving> mob, Object[] specialItems, CrystalType... crystals) {
		super(id, specialItems, crystals);
		this.mob = mob;
		initId();
	}
	
	private void initId() {
		entityId = ReflectionHelper.<HashMap<Class<? extends Entity>, Integer>,EntityList>getPrivateValue(EntityList.class, null, 3).get(mob);
	}

	@Override
	public EntityLiving summon(EntityPlayer player, ContainerSummoner container) {
		try {
			EntityLiving entity = mob.getConstructor(World.class).newInstance(player.worldObj);
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