package demonmodders.Crymod.Common.Items;

import demonmodders.Crymod.Common.Network.PacketClientEffect;
import demonmodders.Crymod.Common.Network.PacketClientEffect.Type;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;

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