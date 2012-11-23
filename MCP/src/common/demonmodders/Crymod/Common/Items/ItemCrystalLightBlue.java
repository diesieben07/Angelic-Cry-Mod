package demonmodders.Crymod.Common.Items;

import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCrystalLightBlue extends ItemCryMod {

	public ItemCrystalLightBlue(int itemId) {
		super(itemId);
		setIconIndex(4);
		setItemName("crystalLightBlue");
		System.out.println(getLocalItemName(null));
		this.setMaxStackSize(1);
	}

	
	
	
		
}