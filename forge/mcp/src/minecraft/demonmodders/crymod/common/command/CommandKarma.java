package demonmodders.crymod.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public class CommandKarma extends CommandBase {

	@Override
	public String getCommandName() {
		return "karma";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			int karma = parseIntBounded(sender, args[0], -PlayerInformation.MAX_KARMA_VALUE, PlayerInformation.MAX_KARMA_VALUE);
			EntityPlayerMP player = args.length >= 2 ? func_82359_c(sender, args[1]) : getCommandSenderAsPlayer(sender);
			PlayerInformation.forPlayer(player).setKarma(karma);
		} else {
			throw new WrongUsageException("commands.crymod_karma.usage", new Object[0]);
		}
	}
	
	
}