package demonmodders.crymod.common.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.inventory.InventoryHelper;

public abstract class SummonableBase extends EntityCreature implements IEntityAdditionalSpawnData, IInventory {

	String owner = "";
	String name = "";
	
	public SummonableBase(World world) {
		super(world);
	}
	
	public EntityPlayer getOwner() {
		return worldObj.getPlayerEntityByName(owner);
	}
	
	public SummonableBase setOwner(EntityPlayer owner) {
		this.owner = owner.username;
		return this;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("owner", owner);
		nbt.setString("name", name);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		owner = nbt.getString("owner");
		name = nbt.getString("name");
	}

	@Override
	public String getEntityName() {
		return name;
	}

	@Override
	public void initCreature() {
		name = RANDOM_NAMES[rand.nextInt(RANDOM_NAMES.length)];
	}
	
	@Override
	public boolean interact(EntityPlayer player) {
		player.openGui(Crymod.instance, GuiType.SUMMONED_ENTITY.getGuiId(), player.worldObj, entityId, 0, 0);
		return true;
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeUTF(name);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		name = data.readUTF();
	}
	
	private static final String[]  RANDOM_NAMES = new String[] {
		"Name 1", "Name 2", "Name 3"
	};

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return getCurrentItemOrArmor(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int numDecrease) {
		return InventoryHelper.genericStackDecrease(this, slot, numDecrease);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		ItemStack stack = getStackInSlot(var1);
		setInventorySlotContents(var1, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		setCurrentItemOrArmor(slot, stack);
	}

	@Override
	public String getInvName() {
		return getEntityName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() { }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSqToEntity(this) <= 64;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
}
