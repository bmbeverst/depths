package com.depths.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

	private TextureAtlas standRight;
	private Animation animationStandRight;
	private TextureAtlas goRight;
	private Animation animationGoRight;
	private int height;
	private int width;
	private boolean left;
	
    private Animation currentAnimation;
    
    private float x = 100;
    private float y = 250;
	private TextureRegion textureRegion;
	private TextureAtlas attackRight;
	private Animation animationAttackRight;
	private TextureAtlas jumpRight;
	private Animation animationJumpRight;
	
	private boolean jump = false;
	private int jumpTime = 0;
	private int maxJump = 40;

	public Player(){
		standRight = new TextureAtlas(Gdx.files.internal("animations/StandRight-packed/pack.atlas"));
		animationStandRight = new Animation(1/2f, standRight.getRegions());
		goRight = new TextureAtlas(Gdx.files.internal("animations/GoRight-packed/pack.atlas"));
		animationGoRight = new Animation(1/12f, goRight.getRegions());
		

		jumpRight = new TextureAtlas(Gdx.files.internal("animations/JumpRight-packed/pack.atlas"));
		animationJumpRight = new Animation(1/12f, jumpRight.getRegions());
		attackRight = new TextureAtlas(Gdx.files.internal("animations/AttackRight-packed/pack.atlas"));
		animationAttackRight = new Animation(1/50f, attackRight.getRegions());
		
		height = standRight.findRegion("001").getRegionHeight();
		width = standRight.findRegion("001").getRegionWidth();
	}
	public void update() {
		if (jump) {
			jumpTime --;
			if (jumpTime >= maxJump/2) {
				y += 2.0f;
			} else {
				y -= 2.0f;
			}
			if (jumpTime < 1 ) {
				jump = false;
			}
		}if(Gdx.input.isKeyPressed(Input.Keys.UP) && !jump){ 
            currentAnimation = animationJumpRight;
            jump = true;
            jumpTime = maxJump;
	    } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        	x -= 1.5f;
            currentAnimation = animationGoRight;
            left = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
        	x += 1.5f;
            currentAnimation = animationGoRight;
            left = false;
        } else {
        	currentAnimation = animationStandRight;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            currentAnimation = animationAttackRight;
        } 
	}
	
	public void render(SpriteBatch batch, float elapsedTime) {

        textureRegion = currentAnimation.getKeyFrame(elapsedTime, true);
        batch.draw(textureRegion, left ? x+width : x, y, left ? -width : width, height);
	}

    public void dispose() {
        standRight.dispose();
        goRight.dispose();
        jumpRight.dispose();
        attackRight.dispose();
    }
}
