package com.depths.game.physics.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.depths.game.ecs.componets.B2dBodyComponent;
import com.depths.game.ecs.componets.BulletComponent;
import com.depths.game.ecs.componets.CollisionComponent;
import com.depths.game.ecs.componets.EnemyComponent;
import com.depths.game.ecs.componets.PlayerComponent;
import com.depths.game.ecs.componets.StateComponent;
import com.depths.game.ecs.componets.TextureComponent;
import com.depths.game.ecs.componets.TransformComponent;
import com.depths.game.ecs.componets.TypeComponent;
import com.depths.game.ecs.systems.RenderingSystem;
import com.depths.game.physics.B2dContactListener;
import com.depths.game.simplexnoise.SimplexNoise;
import com.depths.game.util.DFUtils;

public class LevelFactory {
	private BodyFactory bodyFactory;
	public World world;
	private PooledEngine engine;
	private SimplexNoise sim;
	public int currentLevel = 0;
	private TextureRegion floorTex;
	private int random = (int) (Math.random() * 10);
	private TextureRegion enemyTex;
	private TextureRegion platformTex;
	private SimplexNoise simRough;
	private TextureAtlas atlas;
	
	public LevelFactory(PooledEngine en, TextureAtlas atlas){
		engine = en;
		this.atlas = atlas;
		floorTex = DFUtils.makeTextureRegion(40*RenderingSystem.PPM, 0.5f*RenderingSystem.PPM, "111111FF");
		enemyTex = DFUtils.makeTextureRegion(1*RenderingSystem.PPM,1*RenderingSystem.PPM, "331111FF");
		platformTex = DFUtils.makeTextureRegion(2*RenderingSystem.PPM, 0.1f*RenderingSystem.PPM, "221122FF");
		world = new World(new Vector2(0,-10f), true);
		world.setContactListener(new B2dContactListener());
		bodyFactory = BodyFactory.getInstance(world);
			
		// create a new SimplexNoise (size,roughness,seed)
		sim = new SimplexNoise(512, 0.80f, 1);
		simRough = new SimplexNoise(512, 0.95f, 1);
			
	}


	/** Creates a pair of platforms per level up to yLevel
	 * @param ylevel
	 */
	public void generateLevel(int ylevel){
//		Gdx.app.log(this.getClass().getSimpleName(), "Create at: " + ylevel);
		while(ylevel > currentLevel){
			// get noise      sim.getNoise(xpos,ypos,zpos) 3D noise
			float noise1 = (float)sim.getNoise(1, currentLevel, 0 + random);		// platform 1 should exist?
	    	float noise2 = (float)sim.getNoise(1, currentLevel, 100 + random);	// if plat 1 exists where on x axis
	    	float noise3 = (float)sim.getNoise(1, currentLevel, 200 + random);	// platform 2 exists?
	    	float noise4 = (float)sim.getNoise(1, currentLevel, 300 + random);	// if 2 exists where on x axis ?
	    	float noise5 = (float)sim.getNoise(1, currentLevel ,1400 + random);	// should spring exist on p1?
	    	float noise6 = (float)sim.getNoise(1, currentLevel ,2500 + random);	// should spring exists on p2?
	    	float noise7 = (float)sim.getNoise(1, currentLevel, 2700 + random);	// should enemy exist?
	    	float noise8 = (float)sim.getNoise(1, currentLevel, 3000 + random);	// platform 1 or 2?
	    	if(noise1 > 0.1f){
	    		if(noise5 > 0.5f){
	    			// add bouncy platform
	    			createBouncyPlatform(noise2 * 20 +4, currentLevel + 10);
	    		} else {
		    		createPlatform(noise2 * 20 +4 , currentLevel + 10);
		    		if(noise7 > 0.5f){
		    			// add an enemy
		    			createEnemy(enemyTex,noise2 * 25 +2,currentLevel * 2 + 1);
		    		}
	    		}
	    	}
	    	if(noise3 > 0.2f){
	    		if(noise6 > 0.4f){
	    			// add bouncy platform
	    			createBouncyPlatform(noise4 * 20 +4, currentLevel + 5);
	    		} else {
	    			createPlatform(noise4 * 20 +4, currentLevel + 5);
		    		if(noise8 > 0.5f){
		    			// add an enemy
		    			createEnemy(enemyTex,noise4 * 25 +2,currentLevel * 2 + 1);
		    		}
	    		}
	    	}
	    	if (ylevel < 10) {
    			createPlatform(noise4 * 25 +4, currentLevel + 5);
	    		createPlatform(noise2 * 25 +4 , currentLevel + 10);
			}
	    	currentLevel++;
		}	
	}
	
	public Entity createBullet(float x, float y, float xVel, float yVel){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		BulletComponent bul = engine.createComponent(BulletComponent.class);
		
		b2dbody.body = bodyFactory.makeCirclePolyBody(x,y,0.5f, BodyFactory.Materials.STONE, BodyType.DynamicBody,true);
		b2dbody.body.setBullet(true); // increase physics computation to limit body travelling through other objects
		bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
		position.position.set(x,y,0);
		texture.region = atlas.findRegion("player");;
		type.type = TypeComponent.Types.BULLET;
		b2dbody.body.setUserData(entity);
		bul.xVel = xVel;
		bul.yVel = yVel;
		
		entity.add(bul);
		entity.add(colComp);
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(type);	
		
		engine.addEntity(entity);
		return entity;
	}
	
	public Entity createEnemy(TextureRegion tex, float x, float y){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
			
		b2dbody.body = bodyFactory.makeCirclePolyBody(x,y,1, BodyFactory.Materials.STONE, BodyType.KinematicBody,true);
		position.position.set(x,y,0);
		texture.region = tex;
		enemy.xPosCenter = x;
		type.type = TypeComponent.Types.ENEMY;
		b2dbody.body.setUserData(entity);
			
		entity.add(colComp);
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(enemy);
		entity.add(type);	
			
		engine.addEntity(entity);
			
		return entity;
	}
	
	
	public Entity createBouncyPlatform(float x, float y){
//		Gdx.app.log(this.getClass().getSimpleName(), "createBouncyPlatform" + Math.abs(x) + " " + y);
		Entity entity = engine.createEntity();
		// create body component
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(Math.abs(x), y, .5f, 0.5f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		//make it a sensor so not to impede movement
		bodyFactory.makeAllFixturesSensors(b2dbody.body);
		
		// give it a texture..todo get another texture and anim for springy action
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = floorTex;
		
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.Types.SPRING;
		
		b2dbody.body.setUserData(entity);
		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		engine.addEntity(entity);
		
		return entity;
	}
	
	public void createPlatform(float x, float y){
//		Gdx.app.log(this.getClass().getSimpleName(), "createPlatform" + x + " " + y);
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 0.2f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = floorTex;
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.Types.SCENERY;
		b2dbody.body.setUserData(entity);
		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		engine.addEntity(entity);
		
	}
	
	public void createFloor(TextureRegion tex){
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = tex;
		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.Types.SCENERY;
		
		b2dbody.body.setUserData(entity);
		
		entity.add(b2dbody);
		entity.add(texture);
		entity.add(type);
		
		engine.addEntity(entity);
	}
	
	public Entity createPlayer(OrthographicCamera cam){
	
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		PlayerComponent player = engine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		StateComponent stateCom = engine.createComponent(StateComponent.class);
		
		
		player.cam = cam;
		b2dbody.body = bodyFactory.makeCirclePolyBody(10,1,1, BodyFactory.Materials.STONE, BodyType.DynamicBody,true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(10,1,0);
		texture.region = atlas.findRegion("player");
		type.type = TypeComponent.Types.PLAYER;
		stateCom.set(StateComponent.STATE_NORMAL);
		b2dbody.body.setUserData(entity);
		
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(player);
		entity.add(colComp);
		entity.add(type);
		entity.add(stateCom);
		
		engine.addEntity(entity);
		
		return entity;
		
	}
}