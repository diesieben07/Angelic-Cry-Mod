package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.slots.SlotNoPickup;

public class ContainerEnderBook extends AbstractContainer<InventoryEnderBook> {

	public int currentPage;
	private final EntityPlayer player;
	private final PlayerInformation playerInfo;
	
	public ContainerEnderBook(InventoryEnderBook inventory) {
		super(inventory);
		player = inventory.getPlayer();
		playerInfo = PlayerInformation.forPlayer(player);
		
		addSlotToContainer(new SlotNoPickup(inventory, 0, 65, 44));
		addSlotToContainer(new SlotNoPickup(inventory, 1, 39, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 2, 89, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 3, 28, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 4, 65, 80));
		addSlotToContainer(new SlotNoPickup(inventory, 5, 100, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 6, 40, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 7, 89, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 8, 65, 113));
		
		setActivePage(0);
	}
	
	public void setActivePage(int page) {
		byte[] knownRecipes = playerInfo.getEnderBookRecipesRaw();
		
		if (page >= 0 && page < knownRecipes.length) {
			if (setRecipe(knownRecipes[page])) {
				currentPage = page;
				if (player instanceof EntityPlayerMP) {
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
	public boolean handleButtonClick(int buttonId) {
		return false;
	}
}