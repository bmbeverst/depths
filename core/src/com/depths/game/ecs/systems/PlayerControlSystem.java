package com.depths.game.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.depths.game.controllers.KeyboardController;
import com.depths.game.ecs.componets.B2dBodyComponent;
import com.depths.game.ecs.componets.PlayerComponent;
import com.depths.game.ecs.componets.StateComponent;
import com.depths.game.physics.factory.LevelFactory;

public class PlayerControlSystem extends IteratingSystem{

	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<B2dBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	KeyboardController controller;
	private LevelFactory lvlFactory;
	
	
	@SuppressWarnings("unchecked")
	public PlayerControlSystem(KeyboardController keyCon, LevelFactory lvlf) {
		super(Family.all(PlayerComponent.class).get());
		lvlFactory = lvlf;
		controller = keyCon;
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodm.get(entity);
		StateComponent state = sm.get(entity);
		PlayerComponent player = pm.get(entity);
		
		player.cam.position.y = b2body.body.getPosition().y;
		
		// make player jump very high
		if(player.onSpring){
			b2body.body.applyLinearImpulse(0, 175f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
			state.set(StateComponent.STATE_JUMPING);
			player.onSpring = false;
		}
		
		if(b2body.body.getLinearVelocity().y > 0){
			state.set(StateComponent.STATE_FALLING);
		}
		
		if(b2body.body.getLinearVelocity().y == 0){
			if(state.get() == StateComponent.STATE_FALLING){
				state.set(StateComponent.STATE_NORMAL);
			}
			if(b2body.body.getLinearVelocity().x != 0){
				state.set(StateComponent.STATE_MOVING);
			}
		}
		
		if(controller.left){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		if(controller.right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f),b2body.body.getLinearVelocity().y);
		}
		
		if(!controller.left && ! controller.right){
			b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
		}
		
		if(controller.up && 
				(state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
			//b2body.body.applyForceToCenter(0, 3000,true);
			b2body.body.applyLinearImpulse(0, 75f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
			state.set(StateComponent.STATE_JUMPING);
		}
		
		if(player.timeSinceLastShot > 0){
			player.timeSinceLastShot -= deltaTime;
		}
		
		if(controller.isMouse1Down){ // if mouse button is pressed
			// user wants to fire
			if(player.timeSinceLastShot <=0){ // check the player hasn't just shot
				//player can shoot so do player shoot
				Vector3 mousePos = new Vector3(controller.mouseLocation.x,controller.mouseLocation.y,0); // get mouse position
				player.cam.unproject(mousePos); // convert position from screen to box2d world position
				float speed = 10f;  // set the speed of the bullet
				float shooterX = b2body.body.getPosition().x; // get player location
				float shooterY = b2body.body.getPosition().y; // get player location
				float velx = mousePos.x - shooterX; // get distance from shooter to target on x plain
				float vely = mousePos.y  - shooterY; // get distance from shooter to target on y plain
				float length = (float) Math.sqrt(velx * velx + vely * vely); // get distance to target direct
				if (length != 0) {
				      velx = velx / length;  // get required x velocity to aim at target
				      vely = vely / length;  // get required y velocity to aim at target
				}
				// create a bullet
				lvlFactory.createBullet(shooterX, shooterY, velx*speed, vely*speed);
				//reset timeSinceLastShot
				player.timeSinceLastShot = player.shootDelay;
			}	
		}
	}
}