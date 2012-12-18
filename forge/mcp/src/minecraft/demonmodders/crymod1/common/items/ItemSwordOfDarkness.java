package demonmodders.crymod1.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import demonmodders.crymod1.common.network.PacketClientEffect;
import demonmodders.crymod1.common.network.PacketClientEffect.Type;

public class ItemSwordOfDarkness extends ItemCrymodSword {

	public ItemSwordOfDarkness(String itemName, int defaultId) {
		super(itemName, defaultId, EnumToolMaterial.EMERALD);
		setIconCoord(9, 2);
	}

	@Override
	public boolean hitEntity(ItemStack sword, EntityLiving damagee, EntityLiving swordHolder) {
		new PacketClientEffect(Type.SWORD_OF_DARKNESS, damagee.posX, damagee.posY, damagee.posZ).sendToAllNear(damagee, 64);
		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		// this should probably insta-kill everything :D
		return 20000;
	}
}