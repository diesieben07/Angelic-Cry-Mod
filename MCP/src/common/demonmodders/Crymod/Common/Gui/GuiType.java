package demonmodders.Crymod.Common.Gui;

public enum GuiType {
	SUMMONING_BOOK;
	
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
