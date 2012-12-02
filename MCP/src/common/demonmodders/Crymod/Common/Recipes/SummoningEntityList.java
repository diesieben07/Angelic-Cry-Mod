package demonmodders.Crymod.Common.Recipes;

import java.util.ArrayList;
import java.util.List;

import demonmodders.Crymod.Common.Entities.EntityHeavenZombie;
import demonmodders.Crymod.Common.Entities.EntityHellZombie;

public abstract class SummoningEntityList {
	private static List<SummoningEntityListEntry> angelList = new ArrayList<SummoningEntityListEntry>();
	private static List<SummoningEntityListEntry> demonList = new ArrayList<SummoningEntityListEntry>();
	
	public static List<SummoningEntityListEntry> getSummonings(boolean angels) {
		return angels ? angelList : demonList;
	}
	
	public static int getNumSummonings(boolean angels) {
		return (angels ? angelList : demonList).size();
	}
	
	static {
		angelList.add(new SummoningEntityListEntry(null, EntityHeavenZombie.class, "Heaven Zombie"));
		
		demonList.add(new SummoningEntityListEntry(null, EntityHellZombie.class, "Hell Zombie"));
	}
}
