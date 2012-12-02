package demonmodders.Crymod.Common.Gui;

import java.util.List;
import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import demonmodders.Crymod.Common.Entities.SummonableEntity;
import demonmodders.Crymod.Common.Inventory.InventoryHelper;
import demonmodders.Crymod.Common.Inventory.InventorySummoner;
import demonmodders.Crymod.Common.Inventory.SlotForItem;
import demonmodders.Crymod.Common.Items.ItemCryMod;
import demonmodders.Crymod.Common.Recipes.SummoningEntityList;

import static demonmodders.Crymod.Common.Crymod.logger;

public class ContainerSummoner extends AbstractContainer<InventorySummoner> {

	private static final int[] SLOT_X_POSTITIONS = new int[] {
		80, 80, 58, 103, 80, 38, 121, 80, 58, 103
	};
	
	public static final int BUTTON_NEXT_PAGE = 0;
	public static final int BUTTON_PREV_PAGE = 1;
	public static final int BUTTON_SUMMON = 2;
	
	private int currentPage = 0;
	
	public ContainerSummoner(InventorySummoner inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		for (int i = 0; i < SummoningEntityList.getNumSummonings(inventory.getShowAngels()); i++) {
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
		if (page >= SummoningEntityList.getNumSummonings(inventory.getShowAngels()) || page < 0) {
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

	@Override
	public void buttonClick(int buttonId, Side side, EntityPlayer player) {
		switch (buttonId) {
		case BUTTON_NEXT_PAGE:
			setCurrentPage(currentPage + 1);
			break;
		case BUTTON_PREV_PAGE:
			setCurrentPage(currentPage - 1);
			break;
		case BUTTON_SUMMON:
			if (side.isServer()) {
				try {
					SummonableEntity entity = SummoningEntityList.getSummonings(inventory.getShowAngels()).get(currentPage).getDemon().getConstructor(World.class).newInstance(player.worldObj);
					Random rng = new Random();
					entity.setPosition(player.posX + 1.5F + rng.nextFloat() * 3F, player.posY, player.posZ + 1.5F + rng.nextFloat() * 3F);
					player.worldObj.spawnEntityInWorld(entity);
				} catch (Exception e) {
					logger.warning("Invalid Entity for Summoning!");
				}
			}
			break;
		}
	}
}