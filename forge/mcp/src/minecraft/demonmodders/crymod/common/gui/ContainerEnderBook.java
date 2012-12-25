package demonmodders.crymod.common.gui;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.inventory.SlotNoPickup;
import demonmodders.crymod.common.network.PacketEnderBookRecipe;
import demonmodders.crymod.common.playerinfo.PlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class ContainerEnderBook extends AbstractContainer<InventoryEnderBook> {

	public static final int NEXT_PAGE = 0;
	public static final int PREV_PAGE = 1;
	
	private int currentPage;
	private final EntityPlayer player;
	
	public ContainerEnderBook(InventoryEnderBook inventory) {
		super(inventory);
		player = inventory.getPlayer();
		
		addSlotToContainer(new SlotNoPickup(inventory, 0, 65, 44));
		addSlotToContainer(new SlotNoPickup(inventory, 1, 39, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 2, 89, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 3, 28, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 4, 65, 80));
		addSlotToContainer(new SlotNoPickup(inventory, 5, 100, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 6, 40, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 7, 89, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 8, 65, 113));
	}
	
	public void setActivePage(int page) {
		byte[] knownRecipes = inventory.getKnownRecipes();
		
		if (page >= 0 && page < knownRecipes.length) {
			if (setRecipe(knownRecipes[page])) {
				currentPage = page;
				if (player instanceof EntityPlayerMP) {
					new PacketEnderBookRecipe(knownRecipes[page], windowId).sendToPlayer(player);
				}
			}
		}
	}
	
	public boolean setRecipe(int recipeId) {
		SummoningRecipe recipe = SummoningRecipe.byId(recipeId);
		if (recipe != null) {
			for (int i = 0; i < recipe.getSizeInventory(); i++) {
				inventory.setInventorySlotContents(i, recipe.getStackInSlot(i));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void buttonClick(int buttonId, Side side, EntityPlayer player) {
		if (side.isServer()) {
			switch (buttonId) {
			case NEXT_PAGE:
				setActivePage(currentPage + 1);
				break;
			case PREV_PAGE:
				setActivePage(currentPage - 1);
				break;
			}
		}
	}
}