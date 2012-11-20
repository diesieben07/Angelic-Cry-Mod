package demonmodders.Crymod.Common.Items;

import demonmodders.Crymod.Common.Crymod;
import net.minecraft.src.Item;

public class ItemCryMod extends Item {

	public ItemCryMod(int itemId) {
		super(itemId);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
}
