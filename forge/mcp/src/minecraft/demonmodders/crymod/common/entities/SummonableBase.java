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

public abstract class SummonableBase extends EntityCreature implements IEntityAdditionalSpawnData {

	String owner = "";
	String name = "";
	private boolean playerUsing = false;
	
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
		data.writeUTF(name);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		name = data.readUTF();
	}
	
	@Override
	protected void damageEntity(DamageSource source, int amount) {
		super.damageEntity(source, amount);
		new PacketHealthUpdate(entityId, health).sendToAllTracking(this);
	}
	
	private static final String[]  RANDOM_NAMES = new String[] {
		"Name 1", "Name 2", "Name 3"
	};
}
