package demonmodders.crymod.common.gui;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.inventory.InventorySummoner;
import demonmodders.crymod.common.inventory.SlotForItem;
import demonmodders.crymod.common.items.ItemCryMod;

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
				/*try {
					SummonableBase entity = SummoningEntityList.getSummonings(inventory.getShowAngels()).get(currentPage).getDemon().getConstructor(World.class).newInstance(player.worldObj);
					final int spawnRadiusX = 5;
					final int spawnRadiusY = 5;
					final int spawnRadiusZ = 5;
					
					List<ChunkCoordinates> validCoordinates = new ArrayList<ChunkCoordinates>(150);
					
					for (int x = (int)player.posX - spawnRadiusX; x < player.posX + spawnRadiusX; x++) {
						for (int y = (int)player.posY - spawnRadiusY; y < player.posY + spawnRadiusY; y++) {
							for (int z = (int)player.posZ - spawnRadiusZ; z < player.posZ + spawnRadiusZ; z++) {
								entity.setPosition(x, y, z);
								if (entity.getCanSpawnHere()) {
									validCoordinates.add(new ChunkCoordinates(x, y, z));
								}
							}
						}
					}
					
					if (!validCoordinates.isEmpty()) {
						Random rng = new Random();
						ChunkCoordinates spawnCoords = validCoordinates.get(rng.nextInt(validCoordinates.size()));
						entity.setPosition(spawnCoords.posX, spawnCoords.posY, spawnCoords.posZ);
						entity.setOwner(player);
						entity.initCreature();
						player.worldObj.spawnEntityInWorld(entity);
						new PacketClientEffect(inventory.getShowAngels() ? Type.SUMMON_GOOD : Type.SUMMON_BAD, entity.posX, entity.posY + entity.height + 0.5F, entity.posZ).sendToAllNear(entity, 64);
					}
				} catch (Exception e) {
					logger.warning("Invalid Entity for Summoning!");
				}*/
			}
			break;
		}
	}
	
	private boolean canEntitySpawn(EntityLiving ent) {
		return ent.worldObj.checkIfAABBIsClear(ent.boundingBox) && ent.worldObj.getCollidingBoundingBoxes(ent, ent.boundingBox).isEmpty() && !ent.worldObj.isAnyLiquid(ent.boundingBox);
	}
}