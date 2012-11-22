package demonmodders.Crymod.Common.Items;

import net.minecraft.src.ItemStack;

public class CrystalType {
	
	private final int damage;
	private int iconIndex;
	private String name;
	
	CrystalType(int damage) {
		this.damage = damage;
		crystalTypes[damage] = this;
	}
	
	CrystalType setIconIndex(int iconIndex) {
		this.iconIndex = iconIndex;
		return this;
	}
	
	CrystalType setDefault() {
		defaultType = this;
		return this;
	}
	
	CrystalType setName(String name) {
		this.name = name;
		return this;
	}
	
	public int toItemDamage() {
		return damage;
	}
	
	public int getIconIndex() {
		return iconIndex;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack generateItemStack() {
		return new ItemStack(ItemCryMod.crystal, 1, damage);
	}
	
	public static CrystalType fromItemDamage(int damage) {
		if (damage > crystalTypes.length || damage < 0) {
			return getDefault();
		} else {
			return crystalTypes[damage];
		}
	}
	
	public static CrystalType fromItemDamage(ItemStack stack) {
		return fromItemDamage(stack.getItemDamage());
	}
	
	public static CrystalType getDefault() {
		return defaultType;
	}
	
	public static CrystalType[] crystalTypes = new CrystalType[15];
	private static CrystalType defaultType;
	
	public static final CrystalType LIGHT_BLUE = new CrystalType(0).setIconIndex(4).setName("lightBlue").setDefault();
	public static final CrystalType PURPLE = new CrystalType(1).setIconIndex(5).setName("purple");
	public static final CrystalType RED = new CrystalType(2).setIconIndex(3).setName("red");
	public static final CrystalType YELLOW = new CrystalType(3).setIconIndex(2).setName("yellow");
}
