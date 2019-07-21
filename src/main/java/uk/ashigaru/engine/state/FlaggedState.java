package uk.ashigaru.engine.state;

import java.util.HashSet;
import java.util.Set;

public abstract class FlaggedState implements IState {

	private Set<String> flags = new HashSet	<String>();
	
	@Override
	public void setFlag(String key, boolean bool) {
		if(bool) {
			flags.add(key);
		} else {
			if(flags.contains(key)) flags.remove(key);
		}
	}

	@Override
	public boolean getFlag(String key) {
		return flags.contains(key);
	}
}
