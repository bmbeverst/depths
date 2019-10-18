package com.depths.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.depths.game.Depths;
import com.depths.game.controllers.KeyboardController;
import com.depths.game.physics.B2dModel;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private B2dModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;
	private AtlasRegion playerTex;
	private SpriteBatch sb;
	 
	// our constructor with a Box2DTutorial argument
	public MainScreen(Depths box2dTutorial){
		parent = box2dTutorial;     // setting the argument to our field.	
		controller = new KeyboardController();
		cam = new OrthographicCamera(32,24);
		model = new B2dModel(controller, cam, parent.assetManager);
		debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
		
		// gets the images as a texture
		TextureAtlas atlas = parent.assetManager.manager.get("images/depthsGame.atlas"); // new
		playerTex = atlas.findRegion("player"); // updated
		
		// add a player
		sb = new SpriteBatch();
		sb.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(controller);	
	}

	@Override
	public void render(float delta) {
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(model.world, cam.combined);
		
		sb.begin();
		sb.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
