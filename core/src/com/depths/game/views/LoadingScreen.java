package com.depths.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.depths.game.Depths;
import com.depths.game.actors.LoadingBarPart;
import com.depths.game.actors.Title;

public class LoadingScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private TextureAtlas atlas;
	private AtlasRegion dash;
	private SpriteBatch sb;

	public static enum Progress {
		IMAGE, FONT, PARTI, SOUND, MUSIC, FINISHED;
	}

	private Progress currentLoadingStage = Progress.IMAGE;

	// timer for exiting loading screen
	public float countDown = 3f; // 5 seconds of waiting before menu screen
	private BitmapFont font;
	private Animation<TextureRegion> flameAnimation;
	private Stage stage;
	private Table loadingTable;
	private AtlasRegion titleImage;
	private Title title;
	private String status_text;

	// our constructor with a Box2DTutorial argument
	public LoadingScreen(Depths box2dTutorial) {
		parent = box2dTutorial; // setting the argument to our field.
		stage = new Stage(new ScreenViewport());

		loadAssets();
		// initiate queueing of images but don't start loading
		parent.assetManager.queueAddImages();
		Gdx.app.log(this.getClass().getSimpleName(), "Loading loader images....");
	}

	@Override
	public void show() {

		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(false);

		loadingTable = new Table();
		loadingTable.add(new LoadingBarPart(dash, flameAnimation));
		loadingTable.add(new LoadingBarPart(dash, flameAnimation));
		loadingTable.add(new LoadingBarPart(dash, flameAnimation));
		loadingTable.add(new LoadingBarPart(dash, flameAnimation));
		loadingTable.add(new LoadingBarPart(dash, flameAnimation));

		table.add(title).align(Align.center).pad(0, 0, 30, 0).colspan(10);
		table.row(); // move to next row
		table.add(loadingTable).width(400);

		stage.addActor(table);

	}

	private void loadAssets() {
		// load loading images and wait until finished
		parent.assetManager.queueAddLoadingImages();
		parent.assetManager.manager.finishLoading();

		// get images used to display loading progress
		atlas = parent.assetManager.manager.get("images/depthsLoading.atlas");
		titleImage = atlas.findRegion("badlogic");
		dash = atlas.findRegion("cyber_bar");

		flameAnimation = new Animation<TextureRegion>(0.07f, atlas.findRegions("fireball/fireball"), PlayMode.LOOP);
		title = new Title(titleImage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); // clear the screen
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// check if the asset manager has finished loading
		if (parent.assetManager.manager.update()) { // Load some, will return true if done loading
			status_text = "Loading...";
			switch (currentLoadingStage) {
			case IMAGE:
				status_text = "Loading images....";
				parent.assetManager.queueAddImages(); // first load done, now start fonts
				break;
			case FONT:
				status_text = "Loading fonts....";
				parent.assetManager.queueAddFonts(); // first load done, now start fonts
				break;
			case PARTI:
				status_text = "Loading Particle Effects....";
				parent.assetManager.queueAddParticleEffects(); // fonts are done now do party effects
				break;
			case SOUND:
				status_text = "Loading Sounds....";
				parent.assetManager.queueAddSounds();
				break;
			case MUSIC:
				status_text = "Loading fonts....";
				parent.assetManager.queueAddMusic();
				break;
			case FINISHED:
				status_text = "Finished";
				// all done
				break;
			}
			title.setText(status_text);
			if (currentLoadingStage == Progress.FINISHED) {
				countDown -= delta; // timer to stay on loading screen for short preiod once done loading
				if (countDown < 0) { // countdown is complete
					parent.changeScreen(Depths.Screens.MENU); /// go to menu screen
				}
			} else {
				Gdx.app.log(this.getClass().getSimpleName(), status_text);
				loadingTable.getCells().get(currentLoadingStage.ordinal()).getActor().setVisible(true);
				currentLoadingStage = Progress.values()[currentLoadingStage.ordinal() + 1];
			}
		}

		stage.act();
		stage.draw();
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
