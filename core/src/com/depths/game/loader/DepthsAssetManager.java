package com.depths.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;

public class DepthsAssetManager {
	
	public final AssetManager manager = new AssetManager();
	
	// Textures
	public final String gameImages = "images/depthsGame.atlas";
	public final String loadingImages = "images/depthsLoading.atlas";
		
	public void queueAddImages(){
		manager.load(gameImages, TextureAtlas.class);
	}
		
	// a small set of images used by the loading screen
	public void queueAddLoadingImages(){
		manager.load(loadingImages, TextureAtlas.class);
	}
	
	// Sounds
	public final String boingSound = "sounds/boing.wav";
	public final String pingSound = "sounds/ping.wav";
		
	public void queueAddSounds(){
		manager.load(boingSound, Sound.class);
		manager.load(pingSound, Sound.class);
	}
	
	// Music
	public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";
		
	public void queueAddMusic(){
		manager.load(playingSong, Music.class);
	}
	
	// Skin
	public final String skin = "skin/uiskin.json";
		
	public void queueAddSkin(){
		manager.load(skin, Skin.class);
	}

	public void queueAddFonts() {
		// TODO Auto-generated method stub
		
	}

	public void queueAddParticleEffects() {
		// TODO Auto-generated method stub
		
	}
	
	
}
