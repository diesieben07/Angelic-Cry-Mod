package demonmodders.crymod.common.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.network.PacketHealthUpdate;
import demonmodders.crymod.common.network.PacketNameUpdate;

public abstract class EntitySummonable extends EntityCreature implements IEntityAdditionalSpawnData {

	private static final String[]  RANDOM_NAMES = new String[] {
		"Name 1", "Name 2", "Name 3"
	};
	
	protected String owner = "";
	protected String name = "";
	private boolean playerUsing = false;
	private int healthLastTick;
	private String nameLastTick = null;
	
	private int speed;
	private int power;
	private int control;
	
	public EntitySummonable(World world) {
		super(world);
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
	
	public EntitySummonable setOwner(EntityPlayer owner) {
		this.owner = owner.username;
		return this;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getEntityName() {
		return name;
	}
	
	public int getMinPower() {
		return 10;
	}
	
	public int getMaxPower() {
		return 40;
	}
	
	public int getMinSpeed() {
		return 10;
	}
	
	public int getMaxSpeed() {
		return 40;
	}
	
	public int getMinControl() {
		return 10;
	}
	
	public int getMaxControl() {
		return 40;
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
	public void initCreature() {
		name = RANDOM_NAMES[rand.nextInt(RANDOM_NAMES.length)];
		power = randomBetween(getMinPower(), getMaxPower());
		speed = randomBetween(getMinSpeed(), getMaxSpeed());
		control = randomBetween(getMinControl(), getMaxControl());
	}
	
	private int randomBetween(int lower, int upper) {
		return lower + rand.nextInt(upper - lower + 1);
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

	@Override
	protected void dropEquipment(boolean par1, int par2) {
		for (ItemStack stack : getLastActiveItems()) {
			if (stack != null) {
				entityDropItem(stack, 0);
			}
		}
	}
	
	public abstract int getRank();
}