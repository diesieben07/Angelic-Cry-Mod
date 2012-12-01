package demonmodders.Crymod.Client;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import demonmodders.Crymod.Common.Network.PacketClientRequest;
import demonmodders.Crymod.Common.Network.PacketClientRequest.Action;

public class CrymodKeyHandler extends KeyHandler {
	
	public CrymodKeyHandler() {
		super(new KeyBinding[] {new KeyBinding("crymod.key.invisible", Keyboard.KEY_F)}, new boolean[] {false});
	}

	@Override
	public String getLabel() {
		return "SummoningmodKeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {

	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		new PacketClientRequest(Action.INVISIBLE).sendToServer();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
