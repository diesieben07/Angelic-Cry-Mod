package demonmodders.crymod.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.ServerProxy;
import demonmodders.crymod.common.network.PacketClientAction;
import demonmodders.crymod.common.network.PacketClientAction.Action;
import demonmodders.crymod.common.network.PacketUpdateInformation;

public class CommandSummonersAdminTools extends CommandBase {

	@Override
	public String getCommandName() {
		return "summoners";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayerMP && args.length == 1 && args[0].trim().equalsIgnoreCase("update")) {
			EntityPlayerMP player = (EntityPlayerMP)sender;
			ServerProxy proxy = ((ServerProxy)Crymod.proxy);
			new PacketUpdateInformation(proxy.lastStatus, proxy.lastUpdateInformation).sendToPlayer(player);
			new PacketClientAction(Action.OPEN_UPDATES).sendToPlayer(player);
		} else {
			throw new WrongUsageException("commands.crymod_summoners.usage", new Object[0]);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return MinecraftServer.getServer().isDedicatedServer() && sender instanceof EntityPlayerMP && super.canCommandSenderUseCommand(sender);
	}
}