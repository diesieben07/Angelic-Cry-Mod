package demonmodders.Crymod.Common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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
		PlayerKarmaManager.instance().updateClientKarma(player);
	}
	
	public int modifyKarma(int modifier) {
		karma += modifier;
		PlayerKarmaManager.instance().updateClientKarma(player);
		return karma;
	}
	
	public void write(DataOutput out) throws IOException {
		out.writeByte(karma);
	}
	
	public PlayerKarma read(DataInput in) throws IOException {
		karma = in.readByte();
		return this;
	}
	
	public static PlayerKarma create(DataInput in) throws IOException {
		return new PlayerKarma().read(in);
	}
}