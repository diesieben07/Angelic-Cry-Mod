package demonmodders.crymod.common.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.inventory.InventorySummoner;
import demonmodders.crymod.common.inventory.SlotForItem;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class ContainerSummoner extends AbstractContainer<InventorySummoner> {

	private static final int[] SLOT_X_POSTITIONS = new int[] {
		80, 80, 58, 103, 80, 38, 121, 80, 58, 103
	};
	
	public static final int BUTTON_NEXT_PAGE = 0;
	public static final int BUTTON_PREV_PAGE = 1;
	public static final int BUTTON_SUMMON = 2;
	public static final int NUM_PAGES = 3;
	
	private int currentPage = 0;
	
	public ContainerSummoner(InventorySummoner inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		for (int i = 0; i < NUM_PAGES; i++) {
			addSlotsForPage(i);
		}		
		
		addPlayerInventoryToContainer(inventoryPlayer, 8, 174, true);
		
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
		if (page >= NUM_PAGES || page < 0) {
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
	
	private List<ItemStack> getRecipeOnPage(int page) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		int slotNumStart = page * 10 + 1;
		for (int i = slotNumStart; i < slotNumStart + 9; i++) {
			stacks.add(((Slot)inventorySlots.get(i)).getStack());
		}
		return stacks;
	}
	
	public SummoningRecipe currentMatchingRecipe() {
		List<ItemStack> stacks = getRecipeOnPage(currentPage);
		return SummoningRecipe.findMatchingRecipe(stacks);
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
				SummoningRecipe recipe = currentMatchingRecipe();
				if (recipe != null && recipe.isAngel() == inventory.getShowAngels()) {
					try {
						SummonableBase entity = recipe.getDemon().getConstructor(World.class).newInstance(player.worldObj);
						entity.setOwner(player);
						entity.setPosition(player.posX, player.posY, player.posZ);
						entity.initCreature();
						player.worldObj.spawnEntityInWorld(entity);
					} catch (Exception e) {
						Crymod.logger.warning("Invalid entity for summoning!");
					}
				}
			}
			break;
		}
	}
	
	private boolean canEntitySpawn(EntityLiving ent) {
		return ent.worldObj.checkIfAABBIsClear(ent.boundingBox) && ent.worldObj.getCollidingBoundingBoxes(ent, ent.boundingBox).isEmpty() && !ent.worldObj.isAnyLiquid(ent.boundingBox);
	}
}