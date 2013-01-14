package demonmodders.crymod.common.worldgen;

import java.util.Random;

public enum Rotation {
	
	NONE, R90, R180, R270;
	
	public final byte getId() {
		return (byte)ordinal();
	}
	
	public static Rotation byId(int id) {
		if (id < 0 || id > values().length) {
			return null;
		} else {
			return values()[id];
		}
	}
	
	public static Rotation random(Random rand) {
		return values()[rand.nextInt(4)];
	}
}