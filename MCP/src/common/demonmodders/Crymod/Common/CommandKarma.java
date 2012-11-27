package demonmodders.Crymod.Common;

import demonmodders.Crymod.Common.Karma.PlayerKarmaManager;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;

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
			int karma = parseIntBounded(sender, args[0], -PlayerKarmaManager.MAX_KARMA_VALUE, PlayerKarmaManager.MAX_KARMA_VALUE);
			EntityPlayerMP player = args.length >= 2 ? func_82359_c(sender, args[1]) : getCommandSenderAsPlayer(sender);
			PlayerKarmaManager.playerKarma(player).setKarma(karma);
		} else {
			throw new WrongUsageException("commands.karma.usage", new Object[0]);
		}
	}

}
