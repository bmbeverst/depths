package com.depths.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.depths.game.Depths;

public class PreferencesScreen implements Screen {

	private Depths parent;
	private Stage stage;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label volumeSoundLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
	private Label debugOnOffLabel;

	public PreferencesScreen(Depths depths) {
		parent = depths;
		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());

	}

	@Override
	public void show() {
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		Skin skin = parent.assetManager.manager.get("skin/uiskin.json");

		// music volume
		final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
		volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
		volumeMusicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
				// updateVolumeLabel();
				return false;
			}
		});

		// sound volume
		final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
		soundMusicSlider.setValue(parent.getPreferences().getSoundVolume());
		soundMusicSlider.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				parent.getPreferences().setSoundVolume(soundMusicSlider.getValue());
				// updateVolumeLabel();
				return false;
			}
		});

		// music on/off
		final Button musicCheckbox = new Button(null, skin, "music");
		musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
		musicCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = musicCheckbox.isChecked();
				parent.getPreferences().setMusic(enabled);
				return false;
			}
		});

		// sound on/off
		final Button soundEffectsCheckbox = new Button(null, skin, "sound");
		soundEffectsCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
		soundEffectsCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = soundEffectsCheckbox.isChecked();
				parent.getPreferences().setSoundEffects(enabled);
				return false;
			}
		});
		

		// sound on/off
		final Button debugCheckbox = new Button(null, skin, "toggle");
		debugCheckbox.setChecked(parent.getPreferences().isDebugEnabled());
		debugCheckbox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				boolean enabled = debugCheckbox.isChecked();
				parent.getPreferences().setDebug(enabled);
				return false;
			}
		});

		// return to main screen button
		final TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Depths.Screens.MENU);

			}
		});

		titleLabel = new Label("Preferences", skin);
		volumeMusicLabel = new Label("Music Volume", skin);
		volumeSoundLabel = new Label("Sound Volume", skin);
		musicOnOffLabel = new Label("Music", skin);
		soundOnOffLabel = new Label("Sound Effect", skin);
		debugOnOffLabel = new Label("Debug", skin);


		// Create a table that fills the screen. Everything else will go inside
		// this table.
		Table table = new Table(skin);
		table.setFillParent(true);
		table.setBackground("window");
		stage.addActor(table);

		// Title
		table.add(titleLabel).colspan(2);
		table.row().pad(10, 0, 0, 10);
		table.add(volumeMusicLabel).left();
		table.add(volumeMusicSlider);
		table.row().pad(10, 0, 0, 10);
		table.add(musicOnOffLabel).left();
		table.add(musicCheckbox);
		table.row().pad(10, 0, 0, 10);
		table.add(volumeSoundLabel).left();
		table.add(soundMusicSlider);
		table.row().pad(10, 0, 0, 10);
		table.add(soundOnOffLabel).left();
		table.add(soundEffectsCheckbox);
		table.row().pad(10, 0, 0, 10);
		table.add(debugOnOffLabel).left();
		table.add(debugCheckbox);
		table.row().pad(10, 0, 0, 10);
		table.add(backButton).colspan(2);

	}

	@Override
	public void render(float delta) {
		// clear the screen ready for next set of images to be drawn
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell our stage to do actions and draw itself
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// change the stage's viewport when the screen size is changed
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
