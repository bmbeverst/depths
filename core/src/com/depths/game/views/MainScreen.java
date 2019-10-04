package com.depths.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.depths.game.Depths;
import com.depths.game.physics.B2dModel;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private B2dModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	 
	// our constructor with a Box2DTutorial argument
	public MainScreen(Depths box2dTutorial){
		parent = box2dTutorial;     // setting the argument to our field.
		model = new B2dModel();
		cam = new OrthographicCamera(32,24);
		debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(model.world, cam.combined);

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
