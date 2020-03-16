package com.depths.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.depths.game.Depths;

public class EndScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private Skin skin;
	private Stage stage;

	// our constructor with a Box2DTutorial argument
	public EndScreen(Depths depths) {
		parent = depths; // setting the argument to our field.
	}

	@Override
	public void show() {
		skin = parent.assetManager.manager.get("skin/uiskin.json");
		// create button to go back to manu
		TextButton menuButton = new TextButton("Back", skin);
		
		// create button listener
		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.log(this.getClass().getSimpleName(), "To the MENU");
				parent.changeScreen(Depths.Screens.MENU);			
			}
		});
		
		// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage); 
		
		// create table to layout items we will add
		Table table = new Table(skin);
		table.setFillParent(true);
		table.setBackground("window");
		
		//create a Labels showing the score and some credits
		Label labelScore = new Label("You score was "+parent.lastScore+" Meters", skin);
		Label labelCredits = new Label("Credits:", skin);
		Label labelCredits1 = new Label("Game Design by ", skin);
		Label labelCredits2 = new Label("Cyb3r", skin);
		Label labelCredits3 = new Label("Art Design by ", skin);
		Label labelCredits4 = new Label("The internet", skin);
		
		// add items to table
		table.add(labelScore).colspan(2);
		table.row().padTop(10);
		table.add(labelCredits).colspan(2);
		table.row().padTop(10);
		table.add(labelCredits1).uniformX().align(Align.left);
		table.add(labelCredits2).uniformX().align(Align.left);
		table.row().padTop(10);
		table.add(labelCredits3).uniformX().align(Align.left);
		table.add(labelCredits4).uniformX().align(Align.left);
		table.row().padTop(50);
		table.add(menuButton).colspan(2);
		
		//add table to stage
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// change the stage's viewport when teh screen size is changed
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
	}

}
