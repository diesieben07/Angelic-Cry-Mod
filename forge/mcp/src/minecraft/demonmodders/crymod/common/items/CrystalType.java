package demonmodders.crymod.common.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

public class CrystalType {
	
	private final int damage;
	private int iconIndex;
	private String name;
	private int tier = 1;
	
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
	
	CrystalType setTier(int tier) {
		this.tier = tier;
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
	
	public int getTier() {
		return tier;
	}
	
	public ItemStack generateItemStack() {
		return new ItemStack(ItemCryMod.crystal, 1, damage);
	}
	
	public ItemStack generateChargedItemStack() {
		ItemStack stack = generateItemStack();
		ItemCrystal.setCharge(stack, ItemCrystal.MAX_CHARGE);
		return stack;
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
	
	public static CrystalType[] crystalTypes = new CrystalType[19];
	public static final List<CrystalType> orderedByTier;
	private static CrystalType defaultType;
	
	public static final CrystalType GOLD = new CrystalType(0).setIconIndex(2).setName("gold").setDefault();
	public static final CrystalType CORE = new CrystalType(1).setIconIndex(3).setName("core").setTier(3);
	public static final CrystalType SKY = new CrystalType(2).setIconIndex(4).setName("sky").setTier(4);
	public static final CrystalType MAGIC = new CrystalType(3).setIconIndex(5).setName("magic");
	public static final CrystalType LEAF = new CrystalType(4).setIconIndex(18).setName("leaf").setTier(2);
	public static final CrystalType FIERY = new CrystalType(5).setIconIndex(19).setName("fiery").setTier(2);
	public static final CrystalType OCEAN = new CrystalType(6).setIconIndex(20).setName("ocean").setTier(3);
	public static final CrystalType POWER = new CrystalType(7).setIconIndex(21).setName("power").setTier(3);
	public static final CrystalType WATER = new CrystalType(8).setIconIndex(34).setName("water").setTier(2);
	public static final CrystalType CLOUD = new CrystalType(9).setIconIndex(35).setName("cloud").setTier(3);
	public static final CrystalType FOREST = new CrystalType(10).setIconIndex(36).setName("forest").setTier(3);
	public static final CrystalType GREED = new CrystalType(11).setIconIndex(37).setName("greed").setTier(3);
	public static final CrystalType AIR = new CrystalType(12).setIconIndex(50).setName("air").setTier(2);
	public static final CrystalType EARTH = new CrystalType(13).setIconIndex(51).setName("earth").setTier(2);
	public static final CrystalType STRENGTH = new CrystalType(14).setIconIndex(52).setName("strength").setTier(2);
	public static final CrystalType EVIL = new CrystalType(15).setIconIndex(53).setName("evil").setTier(3);
	public static final CrystalType INSANITY = new CrystalType(16).setIconIndex(66).setName("insanity").setTier(4);
	public static final CrystalType CORRUPTION = new CrystalType(17).setIconIndex(67).setName("corruption").setTier(4);
	public static final CrystalType LOVE = new CrystalType(18).setIconIndex(68).setName("love").setTier(2);
	
	static {
		orderedByTier = new ArrayList<CrystalType>(Arrays.asList(crystalTypes));
		Collections.sort(orderedByTier, new CrystalComparator());
	}

}