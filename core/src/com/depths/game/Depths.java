package com.depths.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.depths.game.loader.DepthsAssetManager;
import com.depths.game.views.EndScreen;
import com.depths.game.views.LoadingScreen;
import com.depths.game.views.MainScreen;
import com.depths.game.views.MenuScreen;
import com.depths.game.views.PreferencesScreen;
 
public class Depths extends Game {
	
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	public DepthsAssetManager assetManager = new DepthsAssetManager();
	private Music playingSong;

	
	public static enum Screens
	{
		MENU, PREFERENCES, APPLICATION, ENDGAME;
	}
	
	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();
		setScreen(loadingScreen);
		
		// tells our asset manger that we want to load the images set in loadImages method
		assetManager.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading.
		assetManager.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assetManager.manager.get("music/Rolemusic_-_pl4y1ng.mp3");
		if (preferences.isMusicEnabled()) {
			playingSong.play();
		}
		
	}
	
	public void changeScreen(Screens screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
	                        this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}
	
	@Override
	public void dispose(){
		playingSong.dispose();
		assetManager.manager.dispose();
	}
}