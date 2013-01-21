package demonmodders.crymod.common.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.UpdateChecker.UpdateStatus;

public class PacketUpdateInformation extends CrymodPacket {

	private UpdateStatus status;
	private List<String> updateInformation;
	
	public PacketUpdateInformation(UpdateStatus status, List<String> updateInformation) {
		this.status = status;
		this.updateInformation = updateInformation;
	}

	public PacketUpdateInformation() { }

	@Override
	void writeData(ByteArrayDataOutput out) {
		status.writeTo(out);
		out.writeBoolean(updateInformation != null);
		if (updateInformation != null) {
			for (int i = 0; i < 4; i++) {
				out.writeUTF(updateInformation.get(i));
			}
		}
	}

	@Override
	void readData(ByteArrayDataInput in) {
		status = UpdateStatus.readFrom(in);
		boolean hasInfo = in.readBoolean();
		if (hasInfo) {
			updateInformation = new ArrayList(4);
			for (int i = 0; i < 4; i++) {
				updateInformation.add(i, in.readUTF());
			}
		} else {
			updateInformation = null;
		}
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.handleUpdateInformation(this);
	}

	public UpdateStatus getStatus() {
		return status;
	}

	public List<String> getUpdateInformation() {
		return updateInformation;
	}
}