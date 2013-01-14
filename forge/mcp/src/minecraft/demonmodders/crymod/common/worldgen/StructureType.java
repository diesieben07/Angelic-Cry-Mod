package demonmodders.crymod.common.worldgen;

public enum StructureType {
	DUNGEON_LARGE, PILLAR, RUIN;
	
	public final byte getId() {
		return (byte)ordinal();
	}
	
	public static StructureType byId(int id) {
		if (id < 0 || id > values().length) {
			return null;
		} else {
			return values()[id];
		}
	}
}