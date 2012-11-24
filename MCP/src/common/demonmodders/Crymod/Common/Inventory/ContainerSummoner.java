package demonmodders.Crymod.Common.Inventory;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import demonmodders.Crymod.Common.Items.ItemCryMod;
import demonmodders.Crymod.Common.Network.PacketPageChange;
import demonmodders.Crymod.Common.Recipes.SummoningRecipeRegistry;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;

public class ContainerSummoner extends AbstractContainer {

	private static final int[] SLOT_X_POSTITIONS = new int[] {
		80, 80, 58, 103, 80, 38, 121, 80, 58, 103
	};
	
	private int currentPage = 0;
	
	public ContainerSummoner(InventorySummoner inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		for (int i = 0; i < SummoningRecipeRegistry.getNumRecipes(); i++) {
			addSlotsForPage(i);
		}		
		
		InventoryHelper.addPlayerInventoryToContainer(this, inventoryPlayer, 8, 174);
		
		setCurrentPage(0);
	}

	private void addSlotsForPage(int page) {
		int slotNumStart = page * 10;
		
		addSlotToContainer(new Slot(inventory, slotNumStart + 0, 80, 33));
		
		
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 1, 80, 70, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 2, 58, 79, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 3, 103, 79, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 4, 80, 100, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 5, 38, 100, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 6, 121, 100, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 7, 80, 130, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 8, 58, 121, ItemCryMod.crystal.shiftedIndex));
		addSlotToContainer(new SlotForItem(inventory, slotNumStart + 9, 103, 121, ItemCryMod.crystal.shiftedIndex));
	}
	
	public int page() {
		return currentPage;
	}
	
	public void setCurrentPage(int page) {
		if (page >= SummoningRecipeRegistry.getNumRecipes() || page < 0) {
			return;
		}
		
		currentPage = page;
		
		// move all slots out of the screen
		for (Slot slot : (List<Slot>)inventorySlots) {
			if (!(slot.inventory instanceof InventoryPlayer)) {
				slot.xDisplayPosition = Integer.MIN_VALUE;
			}
		}
		
		// "reactivate" the ones for the current page
		int slotNumStart = page * 10;
		for (int i = 0; i < 10; i++) {
			((Slot)inventorySlots.get(i + slotNumStart)).xDisplayPosition = SLOT_X_POSTITIONS[i];
		}
	}
	
	public void nextPage() {
		setCurrentPage(currentPage + 1);
		new PacketPageChange(currentPage, this).sendToServer();
	}
	
	public void prevPage() {
		setCurrentPage(currentPage - 1);
		new PacketPageChange(currentPage, this).sendToServer();
	}
}