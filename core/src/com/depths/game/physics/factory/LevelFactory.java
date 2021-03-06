package com.depths.game.physics.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.depths.game.ecs.components.AnimationComponent;
import com.depths.game.ecs.components.B2dBodyComponent;
import com.depths.game.ecs.components.BulletComponent;
import com.depths.game.ecs.components.CollisionComponent;
import com.depths.game.ecs.components.EnemyComponent;
import com.depths.game.ecs.components.ParticleEffectComponent;
import com.depths.game.ecs.components.PlayerComponent;
import com.depths.game.ecs.components.StateComponent;
import com.depths.game.ecs.components.TextureComponent;
import com.depths.game.ecs.components.TransformComponent;
import com.depths.game.ecs.components.TypeComponent;
import com.depths.game.ecs.systems.RenderingSystem;
import com.depths.game.loader.DepthsAssetManager;
import com.depths.game.physics.B2dContactListener;
import com.depths.game.util.DFUtils;
import com.depths.game.util.OpenSimplexNoise;
import com.depths.game.util.ParticleEffectManager;

public class LevelFactory {
	private BodyFactory bodyFactory;
	public World world;
	private PooledEngine engine;
//	private SimplexNoise sim;
	public int currentLevel = 0;
	private TextureRegion floorTex;
	private TextureRegion enemyTex;
	// private SimplexNoise simRough;
	private TextureAtlas atlas;
	private OpenSimplexNoise openSim;
	private ParticleEffectManager pem;

	public LevelFactory(PooledEngine en, DepthsAssetManager atlas2) {
		engine = en;
		this.atlas = atlas2.manager.get("images/depthsGame.atlas");
		floorTex = atlas.findRegion("platform");
		enemyTex = atlas.findRegion("waterdrop");
		DFUtils.makeTextureRegion(2 * RenderingSystem.PPM, 0.1f * RenderingSystem.PPM, "221122FF");
		world = new World(new Vector2(0, -10f), true);
		world.setContactListener(new B2dContactListener());
		bodyFactory = BodyFactory.getInstance(world);

		openSim = new OpenSimplexNoise(MathUtils.random(2000l));
		
		pem = new ParticleEffectManager();
		pem.addParticleEffect(ParticleEffectManager.FIRE, atlas2.manager.get("particles/fire.pe",ParticleEffect.class),1f/64f);
		pem.addParticleEffect(ParticleEffectManager.WATER, atlas2.manager.get("particles/water.pe",ParticleEffect.class),1f/64f);
		pem.addParticleEffect(ParticleEffectManager.SMOKE, atlas2.manager.get("particles/smoke.pe",ParticleEffect.class),1f/64f);

	}

	/**
	 * Creates a pair of platforms per level up to yLevel
	 * 
	 * @param ylevel
	 */
	public void generateLevel(int ylevel) {
		while (ylevel > currentLevel) {
			int range = 15;
			for (int i = 1; i < 5; i++) {
				generateSingleColumn(genNForL(i * 1, currentLevel), genNForL(i * 100, currentLevel),
						genNForL(i * 200, currentLevel), genNForL(i * 300, currentLevel), range, i * 10);
			}
			currentLevel++;
		}
	}

	/**
	 * Make particle effect at xy
	 * @param x 
	 * @param y
	 * @return the Particle Effect Entity
	 */
	public Entity makeParticleEffect(int type, float x, float y){
		Entity entPE = engine.createEntity();
		ParticleEffectComponent pec = engine.createComponent(ParticleEffectComponent.class);
		pec.particleEffect = pem.getPooledParticleEffect(type);
		pec.particleEffect.setPosition(x, y);
		entPE.add(pec);
		engine.addEntity(entPE);
		return entPE;
	}

	/** Attache particle effect to body from body component
	 * @param type the type of particle effect to show
	 * @param b2dbody the bodycomponent with the body to attach to
	 * @return the Particle Effect Entity
	 */
	public Entity makeParticleEffect(int type, B2dBodyComponent b2dbody){
		return makeParticleEffect(type,b2dbody,0,0);
	}

	/**
	 * Attache particle effect to body from body component with offsets
	 * @param type the type of particle effect to show
	 * @param b2dbody the bodycomponent with the body to attach to
	 * @param xo x offset
	 * @param yo y offset
	 * @return the Particle Effect Entity
	 */
	public Entity makeParticleEffect(int type, B2dBodyComponent b2dbody, float xo, float yo){
		Entity entPE = engine.createEntity();
		ParticleEffectComponent pec = engine.createComponent(ParticleEffectComponent.class);
		pec.particleEffect = pem.getPooledParticleEffect(type);
		pec.particleEffect.setPosition(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y);
		pec.particleEffect.getEmitters().first().setAttached(true); //manually attach for testing
        pec.xOffset = xo; 
        pec.yOffset = yo;
		pec.isattached = true;
		pec.particleEffect.getEmitters().first().setContinuous(true);
		pec.attachedBody = b2dbody.body;
		entPE.add(pec);
		engine.addEntity(entPE);

		Gdx.app.log(this.getClass().getSimpleName(), " " + entPE);
		return entPE;
	}
	
	// generate noise for level
	private float genNForL(int level, int height) {
		return (float) openSim.eval(height, level);
	}

	private void generateSingleColumn(float n1, float n2, float n3, float n4, int range, int offset) {
		if (n1 > -0.8f) {
			createPlatform(n2 * range + offset, currentLevel * 2);
			if (n3 > 0.3f) {
				// add bouncy platform
				createBouncyPlatform(n2 * range + offset, currentLevel * 2);
			}
			if (n4 > 0.2f) {
				// add an enemy
				createEnemy(enemyTex, n2 * range + offset, currentLevel * 2 + 1);
			}
		}
	}

//	public void generateLevelOld(int ylevel){
////		Gdx.app.log(this.getClass().getSimpleName(), "Create at: " + ylevel);
//		while(ylevel > currentLevel){
//			// get noise      sim.getNoise(xpos,ypos,zpos) 3D noise
//			float noise1 = (float)sim.getNoise(1, currentLevel, 0 + random);		// platform 1 should exist?
//	    	float noise2 = (float)sim.getNoise(1, currentLevel, 100 + random);	// if plat 1 exists where on x axis
//	    	float noise3 = (float)sim.getNoise(1, currentLevel, 200 + random);	// platform 2 exists?
//	    	float noise4 = (float)sim.getNoise(1, currentLevel, 300 + random);	// if 2 exists where on x axis ?
//	    	float noise5 = (float)sim.getNoise(1, currentLevel ,1400 + random);	// should spring exist on p1?
//	    	float noise6 = (float)sim.getNoise(1, currentLevel ,2500 + random);	// should spring exists on p2?
//	    	float noise7 = (float)sim.getNoise(1, currentLevel, 2700 + random);	// should enemy exist?
//	    	float noise8 = (float)sim.getNoise(1, currentLevel, 3000 + random);	// platform 1 or 2?
//	    	if(noise1 > 0.1f){
//	    		if(noise5 > 0.5f){
//	    			// add bouncy platform
//	    			createBouncyPlatform(noise2 * 20 +4, currentLevel + 10);
//	    		} else {
//		    		createPlatform(noise2 * 20 +4 , currentLevel + 10);
//		    		if(noise7 > 0.5f){
//		    			// add an enemy
//		    			createEnemy(enemyTex,noise2 * 25 +2,currentLevel * 2 + 1);
//		    		}
//	    		}
//	    	}
//	    	if(noise3 > 0.2f){
//	    		if(noise6 > 0.4f){
//	    			// add bouncy platform
//	    			createBouncyPlatform(noise4 * 20 +4, currentLevel + 5);
//	    		} else {
//	    			createPlatform(noise4 * 20 +4, currentLevel + 5);
//		    		if(noise8 > 0.5f){
//		    			// add an enemy
//		    			createEnemy(enemyTex,noise4 * 25 +2,currentLevel * 2 + 1);
//		    		}
//	    		}
//	    	}
//	    	if (ylevel < 10) {
//    			createPlatform(noise4 * 25 +4, currentLevel + 5);
//	    		createPlatform(noise2 * 25 +4 , currentLevel + 10);
//			}
//	    	currentLevel++;
//		}
//	}

	public Entity createBullet(float x, float y, float xVel, float yVel) {
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		BulletComponent bul = engine.createComponent(BulletComponent.class);

		b2dbody.body = bodyFactory.makeCirclePolyBody(x, y, 0.5f, BodyFactory.Materials.STONE, BodyType.DynamicBody,
				true);
		b2dbody.body.setBullet(true); // increase physics computation to limit body travelling through other objects
		bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
		position.position.set(x, y, 0);
		texture.region = atlas.findRegion("player");
		;
		type.type = TypeComponent.Types.BULLET;
		b2dbody.body.setUserData(entity);
		bul.xVel = xVel;
		bul.yVel = yVel;
		
		//attach party to bullet
		bul.particleEffect = makeParticleEffect(ParticleEffectManager.FIRE,b2dbody);

		Gdx.app.log(this.getClass().getSimpleName(), " " + bul.particleEffect);

		entity.add(bul);
		entity.add(colComp);
		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(type);

		engine.addEntity(entity);
		return entity;
	}

	public Entity createEnemy(TextureRegion tex, float x, float y) {
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);

		b2dbody.body = bodyFactory.makeCirclePolyBody(x, y, 1, BodyFactory.Materials.STONE, BodyType.KinematicBody,
				true);
		position.position.set(x, y, 0);
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

	public Entity createBouncyPlatform(float x, float y) {
//		Gdx.app.log(this.getClass().getSimpleName(), "createBouncyPlatform" + Math.abs(x) + " " + y);
		Entity entity = engine.createEntity();
		// create body component
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(Math.abs(x), y, .5f, 0.5f, BodyFactory.Materials.STONE,
				BodyType.StaticBody);
		// make it a sensor so not to impede movement
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

//		Gdx.app.log(this.getClass().getSimpleName(), "createPlatform" + x + " " + y);
	public void createPlatform(float x, float y) {
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.2f, 0.3f, BodyFactory.Materials.STONE, BodyType.StaticBody);
		b2dbody.body.setUserData(entity);
		entity.add(b2dbody);

		TextureComponent texture = engine.createComponent(TextureComponent.class);
		texture.region = floorTex;
		entity.add(texture);

		TypeComponent type = engine.createComponent(TypeComponent.class);
		type.type = TypeComponent.Types.SCENERY;
		entity.add(type);

		TransformComponent trans = engine.createComponent(TransformComponent.class);
		trans.position.set(x, y, 0);
		entity.add(trans);

		engine.addEntity(entity);

	}

	public void createFloor() {
		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.Materials.STONE, BodyType.StaticBody);
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

	public Entity createPlayer(OrthographicCamera cam) {

		Entity entity = engine.createEntity();
		B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
		TransformComponent position = engine.createComponent(TransformComponent.class);
		TextureComponent texture = engine.createComponent(TextureComponent.class);
		AnimationComponent animCom = engine.createComponent(AnimationComponent.class);
		PlayerComponent player = engine.createComponent(PlayerComponent.class);
		CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		StateComponent stateCom = engine.createComponent(StateComponent.class);

		texture.offsetY = -30f;
		player.cam = cam;
		b2dbody.body = bodyFactory.makeCirclePolyBody(10, 1, 1, BodyFactory.Materials.STONE, BodyType.DynamicBody,
				true);
		// set object position (x,y,z) z used to define draw order 0 first drawn
		position.position.set(10, 1, 0);

		Animation<TextureRegion> anim = new Animation<TextureRegion>(0.1f, atlas.findRegions("torch/torchlight"));
		// anim.setPlayMode(Animation.PlayMode.LOOP);
		animCom.animations.put(StateComponent.STATE_NORMAL, anim);
		animCom.animations.put(StateComponent.STATE_MOVING, anim);
		animCom.animations.put(StateComponent.STATE_JUMPING, anim);
		animCom.animations.put(StateComponent.STATE_FALLING, anim);
		animCom.animations.put(StateComponent.STATE_HIT, anim);

		type.type = TypeComponent.Types.PLAYER;
		stateCom.set(StateComponent.STATE_NORMAL);
		stateCom.isLooping = true;
		b2dbody.body.setUserData(entity);

		entity.add(b2dbody);
		entity.add(position);
		entity.add(texture);
		entity.add(animCom);
		entity.add(player);
		entity.add(colComp);
		entity.add(type);
		entity.add(stateCom);

		engine.addEntity(entity);

		return entity;

	}

	public void resetWorld() {
		currentLevel = 0;
		openSim = new OpenSimplexNoise(MathUtils.random(2000l));
		Array<Body> bods = new Array<Body>();
		world.getBodies(bods);
		for(Body bod:bods){
			world.destroyBody(bod);
		}
		
	}
}