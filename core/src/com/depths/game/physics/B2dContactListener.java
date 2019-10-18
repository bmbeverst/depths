package com.depths.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class B2dContactListener implements ContactListener {

	private B2dModel parent;
	
	public B2dContactListener(B2dModel parent){
		this.parent = parent;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Gdx.app.log(this.getClass().getSimpleName(), "Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		Gdx.app.log(this.getClass().getSimpleName(), fa.getBody().getType()+" has hit "+ fb.getBody().getType());
		
		if(fa.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = true;
			return;
		}else if(fb.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = true;
			return;
		}
		
		if(fa.getBody().getType() == BodyType.StaticBody){
			this.shootUpInAir(fa, fb);
		}else if(fb.getBody().getType() == BodyType.StaticBody){
			this.shootUpInAir(fb, fa);
		}else{
			// neither a nor b are static so do nothing
		}
	}
	
	private void shootUpInAir(Fixture staticFixture, Fixture otherFixture){
		Gdx.app.log(this.getClass().getSimpleName(), "Adding Force");
		otherFixture.getBody().applyForceToCenter(new Vector2(-1000,-1000), true);
		parent.playSound(B2dModel.BOING_SOUND); 
	}

	@Override
	public void endContact(Contact contact) {
		Gdx.app.log(this.getClass().getSimpleName(), "Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = false;
			return;
		}else if(fb.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = false;
			return;
		}
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {		
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}

}