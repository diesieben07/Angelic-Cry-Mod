package demonmodders.crymod1.common.tileentities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import demonmodders.crymod1.common.items.ItemCryMod;
import demonmodders.crymod1.common.items.ItemCrystal;

public class TileEntityRechargeStation extends TileEntityInventory {

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("rechargeTime", (byte) rechargeTime);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		rechargeTime = nbt.getByte("rechargeTime");
	}

	private static final int RECHARGE_SPEED = 20;
	private static final int RECHARGE_DURATION = 200;
	private int rechargeTime = 0;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote) {
			return;
		}
		
		if (rechargeTime == 0 && (getBlockMetadata() & 8) != 0) {
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() & 7);
		} else if (rechargeTime != 0 && (getBlockMetadata() & 8) != 8) {
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() | 8);
		}
		
		if (rechargeTime > 0) {
			rechargeTime--;
			
			if (rechargeTime % RECHARGE_SPEED == 0) {
				// loop through all possible crystals
				for (int i = 0; i < 9; i++) {
					ItemStack crystal = getStackInSlot(i);
					if (crystal != null && crystal.itemID == ItemCryMod.crystal.shiftedIndex) {
						int charge = ItemCrystal.getCharge(crystal);
						if (charge < ItemCrystal.MAX_CHARGE) {
							charge++;
						}
						ItemCrystal.setCharge(crystal, charge);
						onInventoryChanged();
					}
				}
			}
		} else {
			boolean canCharge = false;
			for (int i = 0; i < 9; i++) {
				ItemStack crystal = getStackInSlot(i);
				if (crystal != null && crystal.itemID == ItemCryMod.crystal.shiftedIndex && ItemCrystal.getCharge(crystal) != ItemCrystal.MAX_CHARGE) {
					canCharge = true;
				}
			}
			if (canCharge) {
				ItemStack gold = getStackInSlot(9);
				if (gold != null && gold.itemID == Item.ingotGold.shiftedIndex) {
					decrStackSize(9, 1);
					rechargeTime = RECHARGE_DURATION;
				}
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public String getInvName() {
		return "rechargeStation";
	}
}