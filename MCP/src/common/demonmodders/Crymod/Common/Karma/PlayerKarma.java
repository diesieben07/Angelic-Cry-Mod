package demonmodders.Crymod.Common.Karma;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.src.EntityPlayer;

public class PlayerKarma {
	
	private float karma = 0;
	private final EntityPlayer player;
	
	public PlayerKarma(EntityPlayer player) {
		this.player = player;
	}
	
	public PlayerKarma() {
		this(null);
	}
	
	public float getKarma() {
		return karma;
	}
	
	public void setKarma(float karma) {
		this.karma = karma;
		if (this.karma > PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		if (this.karma < -PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = -PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		PlayerKarmaManager.instance().updateClientKarma(player);
	}
	
	public float modifyKarma(float modifier) {
		player.worldObj.playSoundAtEntity(player, "summoningmod.karma" + (modifier < 0 ? "down" : "up"), 1, 1);
		
		setKarma(karma + modifier);
		return karma;
	}
	
	public float modifyKarmaWithMax(float modifier, float max) {
		if (karma < max) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public float modifyKarmaWithMin(float modifier, float min) {
		if (karma > min) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public void write(ByteArrayDataOutput out) {
		out.writeFloat(karma);
	}
	
	public PlayerKarma read(ByteArrayDataInput in) {
		karma = in.readFloat();
		return this;
	}
	
	public static PlayerKarma create(ByteArrayDataInput in) {
		return new PlayerKarma().read(in);
	}
}