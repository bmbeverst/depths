package com.depths.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
	private static final String PREF_MUSIC_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_VOLUME = "sound";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String PREF_DEBUG_ENABLED = "debug.enabled";
	private static final String PREFS_NAME = "lappel-du-vibe";

	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}

	public boolean isSoundEffectsEnabled() {
		return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
	}

	public void setSoundEffects(boolean soundEffectsEnabled) {
		getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}
	

	public boolean isDebugEnabled() {
		return getPrefs().getBoolean(PREF_DEBUG_ENABLED, false);
	}

	public void setDebug(boolean soundEffectsEnabled) {
		getPrefs().putBoolean(PREF_DEBUG_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}

	public boolean isMusicEnabled() {
		return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
	}

	public void setMusic(boolean musicEnabled) {
		getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}

	public float getMusicVolume() {
		return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
	}

	public void setMusicVolume(float volume) {
		getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
		getPrefs().flush();
	}

	public float getSoundVolume() {
		return getPrefs().getFloat(PREF_SOUND_VOLUME, 0.5f);
	}

	public void setSoundVolume(float volume) {
		getPrefs().putFloat(PREF_SOUND_VOLUME, volume);
		getPrefs().flush();
	}
}