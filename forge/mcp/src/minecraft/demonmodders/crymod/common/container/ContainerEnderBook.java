package demonmodders.crymod.common.container;

import net.minecraft.entity.player.EntityPlayer;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.inventory.slots.SlotNoPickup;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class ContainerEnderBook extends AbstractContainer<InventoryEnderBook> {

	public int currentPage;
	private final EntityPlayer player;
	private final PlayerInformation playerInfo;
	private int scrollingSlotIndex = 0;
	public SummoningRecipe currentRecipe;
	
	public ContainerEnderBook(InventoryEnderBook inventory) {
		super(inventory);
		player = inventory.getPlayer();
		playerInfo = PlayerInformation.forPlayer(player);
		
		addSlotToContainer(new SlotNoPickup(inventory, 0, 120, 9));
		
		addSlotToContainer(new SlotNoPickup(inventory, 1, 65, 44));
		addSlotToContainer(new SlotNoPickup(inventory, 2, 39, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 3, 89, 56));
		addSlotToContainer(new SlotNoPickup(inventory, 4, 28, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 5, 65, 80));
		addSlotToContainer(new SlotNoPickup(inventory, 6, 100, 79));
		addSlotToContainer(new SlotNoPickup(inventory, 7, 40, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 8, 89, 99));
		addSlotToContainer(new SlotNoPickup(inventory, 9, 65, 113));
		
		setActivePage(0);
	}
	
	public void setActivePage(int page) {
		byte[] knownRecipes = playerInfo.getEnderBookRecipesRaw();
		
		if (page >= 0 && page < knownRecipes.length) {
			if (setRecipe(knownRecipes[page])) {
				currentPage = page;
			}
		}
	}
	
	public boolean setRecipe(int recipeId) {
		currentRecipe = SummoningRecipe.byId(recipeId);
		if (currentRecipe != null) {
			int internalIndex = 1;
			for (int i = currentRecipe.getSizeInventory() - 9; i < currentRecipe.getSizeInventory(); i++) {
				inventory.setInventorySlotContents(internalIndex, currentRecipe.getStackInSlot(i));
				internalIndex++;
			}
			inventory.setInventorySlotContents(0, currentRecipe.getStackInSlot(0));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean handleButtonClick(int buttonId) {
		return false;
	}
	
	public void updateScrollingSlot() {
		if (currentRecipe != null) {
			scrollingSlotIndex++;
			if (scrollingSlotIndex == currentRecipe.getSizeInventory() - 9) {
				scrollingSlotIndex = 0;
			}
			inventory.setInventorySlotContents(0, currentRecipe.getStackInSlot(scrollingSlotIndex));
		}
	}

	@Override
	public void clientTick() {
		updateScrollingSlot();
	}
}