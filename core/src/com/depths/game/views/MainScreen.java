package com.depths.game.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.depths.game.Depths;
import com.depths.game.controllers.KeyboardController;
import com.depths.game.ecs.components.PlayerComponent;
import com.depths.game.ecs.systems.AnimationSystem;
import com.depths.game.ecs.systems.BulletSystem;
import com.depths.game.ecs.systems.CollisionSystem;
import com.depths.game.ecs.systems.EnemySystem;
import com.depths.game.ecs.systems.LevelGenerationSystem;
import com.depths.game.ecs.systems.PhysicsDebugSystem;
import com.depths.game.ecs.systems.PhysicsSystem;
import com.depths.game.ecs.systems.PlayerControlSystem;
import com.depths.game.ecs.systems.RenderingSystem;
import com.depths.game.physics.factory.LevelFactory;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private OrthographicCamera cam;
	private KeyboardController controller;
	private SpriteBatch sb;
	private PooledEngine engine;
	private TextureAtlas atlas;
	private LevelFactory lvlFactory;
	private Entity player;	

	// our constructor with a depths argument
	public MainScreen(Depths depths) {
		parent = depths; // setting the argument to our field.

		// gets the images as a texture
		atlas = parent.assetManager.manager.get("images/depthsGame.atlas"); // new

		controller = new KeyboardController();

		// create a pooled engine
		engine = new PooledEngine();
		lvlFactory = new LevelFactory(engine, atlas);

		sb = new SpriteBatch();
		// Create our new rendering system
		RenderingSystem renderingSystem = new RenderingSystem(sb);
		cam = renderingSystem.getCamera();
		sb.setProjectionMatrix(cam.combined);

		// add all the relevant systems our engine should run
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsSystem(lvlFactory.world));
		engine.addSystem(renderingSystem);
		engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera(), this.parent));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller, lvlFactory));
		engine.addSystem(new EnemySystem());
		player = lvlFactory.createPlayer(cam);
		engine.addSystem(new BulletSystem(player));
		engine.addSystem(new LevelGenerationSystem(lvlFactory));

		lvlFactory.createFloor(atlas.findRegion("player"));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(delta);
		
		//check if player is dead. if so show end screen
		PlayerComponent pc = (player.getComponent(PlayerComponent.class));
		if(pc.isDead){
			Gdx.app.log(this.getClass().getSimpleName(), "YOU DIED : back to menu you go!");
			parent.lastScore = (int) pc.cam.position.y;
			parent.changeScreen(Depths.Screens.ENDGAME);	
		}

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
