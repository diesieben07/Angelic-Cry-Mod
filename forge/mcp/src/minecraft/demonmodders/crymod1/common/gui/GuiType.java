package demonmodders.crymod1.common.gui;

public enum GuiType {
	SUMMONING_BOOK, EVIL_TABLET, RECHARGE_STATION, CRYSTAL_BAG;
	
	public int getGuiId() {
		return ordinal();
	}
	
	public static GuiType fromGuiId(int guiId) {
		if (guiId < 0 || guiId > values().length) {
			throw new IllegalArgumentException();
		} else {
			return values()[guiId];
		}
	}
}
