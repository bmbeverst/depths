package com.depths.game.views;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.depths.game.Depths;
import com.depths.game.controllers.KeyboardController;
import com.depths.game.ecs.componet.B2dBodyComponent;
import com.depths.game.ecs.componet.CollisionComponent;
import com.depths.game.ecs.componet.PlayerComponent;
import com.depths.game.ecs.componet.StateComponent;
import com.depths.game.ecs.componet.TextureComponent;
import com.depths.game.ecs.componet.TransformComponent;
import com.depths.game.ecs.componet.TypeComponent;
import com.depths.game.ecs.system.AnimationSystem;
import com.depths.game.ecs.system.CollisionSystem;
import com.depths.game.ecs.system.LevelGenerationSystem;
import com.depths.game.ecs.system.PhysicsDebugSystem;
import com.depths.game.ecs.system.PhysicsSystem;
import com.depths.game.ecs.system.PlayerControlSystem;
import com.depths.game.ecs.system.RenderingSystem;
import com.depths.game.physics.B2dContactListener;
import com.depths.game.physics.B2dModel;
import com.depths.game.physics.BodyFactory;
import com.depths.game.physics.factory.LevelFactory;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private OrthographicCamera cam;
	private KeyboardController controller;
	private SpriteBatch sb;
	private PooledEngine engine;
	private TextureAtlas atlas;
	private LevelFactory lvlFactory;
	 
	// our constructor with a depths argument
	public MainScreen(Depths depths){
		parent = depths;     // setting the argument to our field.

		// gets the images as a texture
		atlas = parent.assetManager.manager.get("images/depthsGame.atlas"); // new
		
		controller = new KeyboardController();

		//create a pooled engine
		engine = new PooledEngine();
		lvlFactory = new LevelFactory(engine,atlas.findRegion("player"));

		sb = new SpriteBatch();
	    // Create our new rendering system
		RenderingSystem renderingSystem = new RenderingSystem(sb);
		cam = renderingSystem.getCamera();
		sb.setProjectionMatrix(cam.combined);
			

        // add all the relevant systems our engine should run
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new PhysicsSystem(lvlFactory.world));
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsDebugSystem(lvlFactory.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
        engine.addSystem(new LevelGenerationSystem(lvlFactory));
        
        lvlFactory.createPlayer(atlas.findRegion("player"),cam);
        lvlFactory.createFloor(atlas.findRegion("player"));
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(controller);	
	}

	@Override
	public void render(float delta) {
	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	engine.update(delta);

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
