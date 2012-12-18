package demonmodders.crymod.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import demonmodders.crymod.common.karma.PlayerKarma;
import demonmodders.crymod.common.playerinfo.PlayerInfo;

public class CommandKarma extends CommandBase {

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayerMP ? ((EntityPlayerMP)sender).mcServer.getConfigurationManager().areCommandsAllowed(sender.getCommandSenderName()) : false;
	}

	@Override
	public String getCommandName() {
		return "karma";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			int karma = parseIntBounded(sender, args[0], -PlayerKarma.MAX_KARMA_VALUE, PlayerKarma.MAX_KARMA_VALUE);
			EntityPlayerMP player = args.length >= 2 ? func_82359_c(sender, args[1]) : getCommandSenderAsPlayer(sender);
			PlayerInfo.playerKarma(player).setKarma(karma);
		} else {
			throw new WrongUsageException("commands.karma.usage", new Object[0]);
		}
	}

}
