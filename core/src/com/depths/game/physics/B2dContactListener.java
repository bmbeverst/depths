package com.depths.game.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.depths.game.ecs.componets.CollisionComponent;

public class B2dContactListener implements ContactListener {

	public B2dContactListener() {
	}

	@Override
	public void beginContact(Contact contact) {
//		Gdx.app.log(this.getClass().getSimpleName(), "Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
//		Gdx.app.log(this.getClass().getSimpleName(), fa.getBody().getType()+" has hit "+ fb.getBody().getType());

		if (fa.getBody().getUserData() instanceof Entity) {
			Entity ent = (Entity) fa.getBody().getUserData();
			entityCollision(ent, fb);
			return;
		} else if (fb.getBody().getUserData() instanceof Entity) {
			Entity ent = (Entity) fb.getBody().getUserData();
			entityCollision(ent, fa);
			return;
		}
	}

	private void entityCollision(Entity ent, Fixture fb) {
		if (fb.getBody().getUserData() instanceof Entity) {
			Entity colEnt = (Entity) fb.getBody().getUserData();

			CollisionComponent col = ent.getComponent(CollisionComponent.class);
			CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

			if (col != null) {
				col.collisionEntity = colEnt;
			} else if (colb != null) {
				colb.collisionEntity = ent;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
//		Gdx.app.log(this.getClass().getSimpleName(), "Contact end");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}