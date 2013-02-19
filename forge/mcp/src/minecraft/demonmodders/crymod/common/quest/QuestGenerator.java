package demonmodders.crymod.common.quest;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import demonmodders.crymod.common.entities.EntityQuester;

public final class QuestGenerator {
	
	private QuestGenerator() { }
	
	private static final QuestGenerator instance = new QuestGenerator();
	
	public static QuestGenerator instance() {
		return instance;
	}
	
	public void generateQuests(EntityQuester quester, List<Quest> quests, int numQuests) {
		quests.clear();
		for (int i = 0; i < numQuests; i++) {
			quests.add(generateQuest(quester));
		}
	}

	public Quest generateQuest(EntityQuester quester) {
		int questType = quester.getRNG().nextInt(1);
		Quest quest = null;
		
		switch (questType) {
		case 0:
			quest = new QuestKillEntity(quester.getPersistentID());
			break;
		}
		quest.makeRandom(quester.getRNG());
		return quest;
	}
}