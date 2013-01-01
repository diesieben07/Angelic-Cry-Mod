package demonmodders.crymod.common.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.inventory.InventoryHelper;
import demonmodders.crymod.common.inventory.InventorySummonable;
import demonmodders.crymod.common.network.PacketHealthUpdate;
import demonmodders.crymod.common.network.PacketNameUpdate;

public abstract class SummonableBase extends EntityCreature implements IEntityAdditionalSpawnData {

	private static final String[]  RANDOM_NAMES = new String[] {
		"Name 1", "Name 2", "Name 3"
	};
	
	public static final int MAX_SPEED = 50;
	public static final int MAX_POWER = 50;
	public static final int MAX_CONTROL = 50;
	
	String owner = "";
	String name = "";
	private boolean playerUsing = false;
	private int healthLastTick;
	private String nameLastTick = null;
	
	private int speed;
	private int power;
	private int control;
	
	public SummonableBase(World world) {
		super(world);
		speed = MAX_SPEED;
		power = MAX_POWER;
		control = MAX_CONTROL;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getPower() {
		return power;
	}
	
	public int getControl() {
		return control;
	}
	
	public EntityPlayer getOwner() {
		return worldObj.getPlayerEntityByName(owner);
	}
	
	public SummonableBase setOwner(EntityPlayer owner) {
		this.owner = owner.username;
		return this;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("owner", owner);
		nbt.setString("name", name);
		nbt.setInteger("speed", speed);
		nbt.setInteger("power", power);
		nbt.setInteger("control", control);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		owner = nbt.getString("owner");
		name = nbt.getString("name");
		speed = nbt.getInteger("speed");
		power = nbt.getInteger("power");
		control = nbt.getInteger("control");
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
		if (player.username.equalsIgnoreCase(owner)) {
			playerUsing = true;
			player.openGui(Crymod.instance, GuiType.SUMMONED_ENTITY.getGuiId(), player.worldObj, entityId, 0, 0);
		}
		return true;
	}
	
	public void onPlayerCloseGui() {
		playerUsing = false;
	}
	
	@Override
	protected boolean isMovementBlocked() {
		return super.isMovementBlocked() || playerUsing;
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeByte(speed);
		data.writeByte(power);
		data.writeByte(control);
		data.writeUTF(name);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		speed = data.readByte();
		power = data.readByte();
		control = data.readByte();
		name = data.readUTF();
	}

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote) {
			if (healthLastTick != health) {
				new PacketHealthUpdate(entityId, health).sendToAllTracking(this);
				healthLastTick = health;
			}
			if (nameLastTick != name) {
				new PacketNameUpdate(entityId, name).sendToAllTracking(this);
				nameLastTick = name;
			}
		}
		super.onUpdate();
	}
}
