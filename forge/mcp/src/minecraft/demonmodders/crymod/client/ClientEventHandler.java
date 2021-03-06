package demonmodders.crymod.client;

import java.net.URL;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ClientEventHandler {
	
	private static final String[] SOUNDS = {
		// these are just placeholders!
		// http://www.freesound.org/people/BristolStories/sounds/51713/
		"karmaup",
		// http://www.freesound.org/people/HerbertBoland/sounds/33637/
		"karmadown",
		//http://www.freesound.org/people/ryansnook/sounds/108012/
		"karmaerror"
	};
	
	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent evt) {
		for (String sound : SOUNDS) {
			evt.manager.soundPoolSounds.addSound("summoningmod/" + sound + ".ogg", getSound(sound));
		}
	}
		
	private URL getSound(String sound) {
		return getClass().getResource("/demonmodders/crymod/resource/sound/" + sound + ".ogg");
	}
}