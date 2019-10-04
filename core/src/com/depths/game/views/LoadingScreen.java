package com.depths.game.views;

import com.badlogic.gdx.Screen;
import com.depths.game.Depths;

public class LoadingScreen implements Screen {
		
	private Depths parent; // a field to store our orchestrator
	 
	// our constructor with a Box2DTutorial argument
	public LoadingScreen(Depths box2dTutorial){
		parent = box2dTutorial;     // setting the argument to our field.
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		parent.changeScreen(Depths.Screens.MENU);

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
