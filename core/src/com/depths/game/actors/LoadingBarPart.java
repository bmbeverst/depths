package com.depths.game.actors;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingBarPart extends Actor {

	
	private Animation<TextureRegion> flameAnimation;
	private AtlasRegion image;
	private TextureRegion currentFrame;
	private float stateTime = 0f; 

	public LoadingBarPart(AtlasRegion ar, Animation<TextureRegion> an) {
		super();
		image = ar;
		flameAnimation = an;
		this.setWidth(64);
		this.setHeight(64);
		this.setVisible(false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		//batch.draw(image, getX(),getY(), 80, 40);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		batch.draw(currentFrame, getX()-5,getY(), 64, 64);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta; // Accumulate elapsed animation time
	    currentFrame = flameAnimation.getKeyFrame(stateTime, true);
	}
	
	
}
