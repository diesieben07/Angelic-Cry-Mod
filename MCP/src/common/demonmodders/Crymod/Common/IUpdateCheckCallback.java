package demonmodders.Crymod.Common;

import demonmodders.Crymod.Common.UpdateChecker.State;

public interface IUpdateCheckCallback {
	public void updateCheckFinished(State state);
}
