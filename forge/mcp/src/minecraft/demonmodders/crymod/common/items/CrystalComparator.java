package demonmodders.crymod.common.items;

import java.util.Comparator;

public class CrystalComparator implements Comparator<CrystalType> {

	@Override
	public int compare(CrystalType t1, CrystalType t2) {
		return t1.getTier() == t2.getTier() ? 0 : t1.getTier() < t2.getTier() ? -1 : 1;
	}

}
