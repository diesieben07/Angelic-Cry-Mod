package demonmodders.Crymod.Common.Items;

public class ItemRecipePage extends ItemCryMod {

	public ItemRecipePage(String itemName, int defaultId) {
		super(itemName, defaultId);
		setHasSubtypes(true);
		setMaxStackSize(1);
		setIconIndex(15);
	}

}
