package com.depths.game.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.depths.game.ecs.components.B2dBodyComponent;
import com.depths.game.ecs.components.BulletComponent;
import com.depths.game.ecs.components.Mapper;
import com.depths.game.ecs.components.ParticleEffectComponent;

public class BulletSystem extends IteratingSystem {
	private Entity player;
	private int max_distance = 20;

	public BulletSystem(Entity player) {
		super(Family.all(BulletComponent.class).get());
		this.player = player;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get box 2d body and bullet components
		B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
		BulletComponent bullet = Mapper.bulletCom.get(entity);

		// apply bullet velocity to bullet body
		b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);

		// get player pos
		B2dBodyComponent playerBodyComp = Mapper.b2dCom.get(player);
		float px = playerBodyComp.body.getPosition().x;
		float py = playerBodyComp.body.getPosition().y;

		// get bullet pos
		float bx = b2body.body.getPosition().x;
		float by = b2body.body.getPosition().y;

		// if bullet is 20 units away from player on any axis then it is probably off
		// screen
		if (bx - px > max_distance || by - py > max_distance) {
			bullet.isDead = true;
		}

		// check if bullet is dead
		if (bullet.isDead) {
			Gdx.app.log(this.getClass().getSimpleName(), "Bullet died");
			ParticleEffectComponent test = Mapper.peCom.get(bullet.particleEffect);
			Gdx.app.log(this.getClass().getSimpleName(), " " + test);
			Mapper.peCom.get(bullet.particleEffect).isDead = true;
			b2body.isDead = true;
		}
	}
}