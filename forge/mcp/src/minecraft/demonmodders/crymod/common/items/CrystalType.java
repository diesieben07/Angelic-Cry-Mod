package demonmodders.crymod.common.items;

import net.minecraft.item.ItemStack;

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
	
	public boolean containsThis(ItemStack stack) {
		return stack != null && stack.itemID == ItemCryMod.crystal.itemID && stack.getItemDamage() == damage;
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
	public static final CrystalType GREEN = new CrystalType(4).setIconIndex(18).setName("green");
	public static final CrystalType ORANGE = new CrystalType(5).setIconIndex(19).setName("orange");
	public static final CrystalType BLUE = new CrystalType(6).setIconIndex(20).setName("blue");
}
