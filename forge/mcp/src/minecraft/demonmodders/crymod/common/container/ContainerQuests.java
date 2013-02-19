package demonmodders.crymod.common.container;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.inventory.EmptyInventory;
import demonmodders.crymod.common.quest.Quest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerQuests extends AbstractContainer<IInventory> {

	private final EntityQuester quester;
	
	public ContainerQuests(EntityQuester quester) {
		super(new EmptyInventory());
		this.quester = quester;
	}
	
	public EntityQuester getQuester() {
		return quester;
	}

	@Override
	public boolean handleButtonClick(int buttonId) {
		return buttonId <= 0;
	}

	@Override
	public void buttonClick(int buttonId, Side side, EntityPlayer player) {
		System.out.println("click " + buttonId + " on" + side);
		if (side.isServer()) {
			buttonId = -buttonId;
			List<Quest> quests = quester.getQuests();
			if (buttonId >= 0 && buttonId < quests.size()) {
				Quest quest = quests.get(buttonId);
				if (quest.canAccept(player)) {
					quests.remove(buttonId);
					quest.onAccept(player, quester);
					player.closeScreen();
				}
			}
		}
	}
}