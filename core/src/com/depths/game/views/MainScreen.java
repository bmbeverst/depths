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
import com.depths.game.ecs.system.PhysicsDebugSystem;
import com.depths.game.ecs.system.PhysicsSystem;
import com.depths.game.ecs.system.PlayerControlSystem;
import com.depths.game.ecs.system.RenderingSystem;
import com.depths.game.physics.B2dContactListener;
import com.depths.game.physics.B2dModel;
import com.depths.game.physics.BodyFactory;

public class MainScreen implements Screen {

	private Depths parent; // a field to store our orchestrator
	private B2dModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	private KeyboardController controller;
	private AtlasRegion playerTex;
	private SpriteBatch sb;
	private BodyFactory bodyFactory;
	private World world;
	private PooledEngine engine;
	private TextureAtlas atlas;
	 
	// our constructor with a depths argument
	public MainScreen(Depths depths){
		parent = depths;     // setting the argument to our field.	
		controller = new KeyboardController();

		world = new World(new Vector2(0,-10f), true);
		world.setContactListener(new B2dContactListener());
		bodyFactory = BodyFactory.getInstance(world);

		// gets the images as a texture
		atlas = parent.assetManager.manager.get("images/depthsGame.atlas"); // new
		playerTex = atlas.findRegion("player"); // updated

		sb = new SpriteBatch();
	        // Create our new rendering system
		RenderingSystem renderingSystem = new RenderingSystem(sb);
		cam = renderingSystem.getCamera();
		sb.setProjectionMatrix(cam.combined);
			

		//create a pooled engine
		engine = new PooledEngine();

        // add all the relevant systems our engine should run
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
		
        // create some game objects
        createPlayer();
        createPlatform(2,2);
        createPlatform(2,7);
        createPlatform(7,2);
        createPlatform(7,7);
        
        createFloor();
	}
	
	private void createPlayer(){

		// Create the Entity and all the components that will go in the entity
		Entity entity = ((PooledEngine) engine).createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		PlayerComponent player = engine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		TypeComponent type = ((PooledEngine) engine).createComponent(TypeComponent.class);
		StateComponent stateCom = engine.createComponent(StateComponent.class);

		// create the data for the components and add them to the components
		b2dbody.body = bodyFactory.makeCirclePolyBody(10,10,1, BodyFactory.Materials.STONE, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(10,10,0);
		texture.region = atlas.findRegion("player");
		type.type = TypeComponent.PLAYER;
		stateCom.set(StateComponent.STATE_NORMAL);
		b2dbody.body.setUserData(entity);

		// add the components to the entity
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(player);
		entity.add(colComp);
		entity.add(type);
		entity.add(stateCom);

		// add the entity to the engine	
		engine.addEntity(entity);
			
	}
	
	private void createPlatform(float x, float y){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 3, 0.2f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = atlas.findRegion("player");
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.SCENERY;
		b2dbody.body.setUserData(entity);
		
		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		
		engine.addEntity(entity);
		
	}

	private void createFloor(){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = atlas.findRegion("player");
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.SCENERY;
		
		b2dbody.body.setUserData(entity);

		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		
		engine.addEntity(entity);
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
