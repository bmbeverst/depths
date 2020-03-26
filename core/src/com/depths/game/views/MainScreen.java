package com.depths.game.views;

import com.badlogic.ashley.core.ComponentMapper;
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
import com.depths.game.ecs.components.TransformComponent;
import com.depths.game.ecs.systems.AnimationSystem;
import com.depths.game.ecs.systems.BulletSystem;
import com.depths.game.ecs.systems.CollisionSystem;
import com.depths.game.ecs.systems.EnemySystem;
import com.depths.game.ecs.systems.LevelGenerationSystem;
import com.depths.game.ecs.systems.ParticleEffectSystem;
import com.depths.game.ecs.systems.PhysicsDebugSystem;
import com.depths.game.ecs.systems.PhysicsSystem;
import com.depths.game.ecs.systems.PlayerControlSystem;
import com.depths.game.ecs.systems.RenderingSystem;
import com.depths.game.loader.DepthsAssetManager;
import com.depths.game.physics.factory.LevelFactory;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private OrthographicCamera cam;
	private KeyboardController controller;
	private SpriteBatch sb;
	private PooledEngine engine;
	private DepthsAssetManager atlas;
	private LevelFactory lvlFactory;
	private Entity player;	

	// our constructor with a depths argument
	public MainScreen(Depths depths) {
		parent = depths; // setting the argument to our field.

		// gets the images as a texture
		atlas = parent.assetManager;

		controller = new KeyboardController();

		// create a pooled engine
		engine = new PooledEngine();
		lvlFactory = new LevelFactory(engine, atlas);

		sb = new SpriteBatch();
		// Create our new rendering system
		RenderingSystem renderingSystem = new RenderingSystem(sb);
		cam = renderingSystem.getCamera();
		ParticleEffectSystem particleSystem = new ParticleEffectSystem(sb,cam);
		sb.setProjectionMatrix(cam.combined);

		// add all the relevant systems our engine should run
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsSystem(lvlFactory.world));
		engine.addSystem(renderingSystem);        // not a fan of splitting batch into rendering and particles but I like the separation of the systems
        engine.addSystem(particleSystem); // particle get drawns on top so should be placed after normal rendering
		engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera(), this.parent));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller, lvlFactory));
		engine.addSystem(new EnemySystem());
		player = lvlFactory.createPlayer(cam);
		engine.addSystem(new BulletSystem(player));
		engine.addSystem(new LevelGenerationSystem(lvlFactory));

		lvlFactory.createFloor();
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
			Gdx.app.log(this.getClass().getSimpleName(), "X " + cam.position.x + " Y " + cam.position.y);
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

	public void resetWorld() {

		System.out.println("Resetting world");
		engine.removeAllEntities();
		lvlFactory.resetWorld();
		
		player = lvlFactory.createPlayer(cam);
		lvlFactory.createFloor();
        
        // reset controller controls (fixes bug where controller stuck on directrion if died in that position)
        controller.left = false;
        controller.right = false;
        controller.up = false;
        controller.down = false;
        controller.isMouse1Down = false;
        controller.isMouse2Down = false;
        controller.isMouse3Down = false;
		
	}

}
