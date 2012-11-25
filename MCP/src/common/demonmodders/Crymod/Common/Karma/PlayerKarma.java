package demonmodders.Crymod.Common.Karma;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.src.EntityPlayer;

public class PlayerKarma {
	
	private int karma = 0;
	private final EntityPlayer player;
	
	public PlayerKarma(EntityPlayer player) {
		this.player = player;
	}
	
	public PlayerKarma() {
		this(null);
	}
	
	public int getKarma() {
		return karma;
	}
	
	public void setKarma(int karma) {
		this.karma = karma;
		if (this.karma > PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		if (this.karma < -PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = -PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		PlayerKarmaManager.instance().updateClientKarma(player);
	}
	
	public int modifyKarma(int modifier) {
		setKarma(karma + modifier);
		return karma;
	}
	
	public int modifyKarmaWithMax(int modifier, int max) {
		if (karma < max) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public int modifyKarmaWithMin(int modifier, int min) {
		if (karma > min) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public void write(ByteArrayDataOutput out) {
		out.writeByte(karma);
	}
	
	public PlayerKarma read(ByteArrayDataInput in) {
		karma = in.readByte();
		return this;
	}
	
	public static PlayerKarma create(ByteArrayDataInput in) {
		return new PlayerKarma().read(in);
	}
}