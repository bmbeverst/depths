package com.depths.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.depths.game.lighting.Light;
import com.depths.game.world.World;


public class Player extends Actor{

	private TextureAtlas standRight;
	private Animation<TextureRegion> animationStandRight;
	private TextureAtlas goRight;
	private Animation<TextureRegion> animationGoRight;
	private boolean left;
	
    private Animation<TextureRegion> currentAnimation;
    
	private TextureRegion textureRegion;
	private TextureAtlas attackRight;
	private Animation<TextureRegion> animationAttackRight;
	private TextureAtlas jumpRight;
	private Animation<TextureRegion> animationJumpRight;
    private float stateTime = 0f;
	 
	private boolean jump = false;
	private int jumpTime = 0;
	private int maxJump = 40;
	private World world;

	public Player(World world){
		standRight = new TextureAtlas(Gdx.files.internal("animations/StandRight-packed/pack.atlas"));
		animationStandRight = new Animation<TextureRegion>(1/2f, standRight.getRegions());
		goRight = new TextureAtlas(Gdx.files.internal("animations/GoRight-packed/pack.atlas"));
		animationGoRight = new Animation<TextureRegion>(1/12f, goRight.getRegions());
		

		jumpRight = new TextureAtlas(Gdx.files.internal("animations/JumpRight-packed/pack.atlas"));
		animationJumpRight = new Animation<TextureRegion>(1/12f, jumpRight.getRegions());
		attackRight = new TextureAtlas(Gdx.files.internal("animations/AttackRight-packed/pack.atlas"));
		animationAttackRight = new Animation<TextureRegion>(1/50f, attackRight.getRegions());
		
		this.setSize(standRight.findRegion("001").getRegionHeight(), standRight.findRegion("001").getRegionWidth());
		
		this.setPosition(100, 250);
		
		this.world = world;
	}
	public void update() {
		if (jump) {
			jumpTime --;
			if (jumpTime >= maxJump/2) {
				this.moveBy(0,  2.0f);
			} else {
				this.moveBy(0,  -2.0f);
			}
			if (jumpTime < 1 ) {
				jump = false;
			}
		}if(Gdx.input.isKeyPressed(Input.Keys.UP) && !jump){ 
            currentAnimation = animationJumpRight;
            jump = true;
            jumpTime = maxJump;
	    } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			this.moveBy(-1.5f,  0);
        	if (!jump) {
                currentAnimation = animationGoRight;
        	}
            left = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			this.moveBy(1.5f,  0);
        	if (!jump) {
                currentAnimation = animationGoRight;
        	}
            left = false;
        } else {
        	currentAnimation = animationStandRight;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            currentAnimation = animationAttackRight;
            this.world.getLights().addLight(new Light(this.getX(), this.getY(), 50));
        } 
	}

    @Override
	public void draw(Batch batch, float parentAlpha) {
    	stateTime += Gdx.graphics.getDeltaTime();
        textureRegion = currentAnimation.getKeyFrame(stateTime, true);
        float x = this.getX();
        float y = this.getY();
        float width =  this.getWidth();
        float height = this.getHeight();
        batch.draw(textureRegion, left ? x+width : x, y, left ? -width : width, height);
	}

    public void dispose() {
        standRight.dispose();
        goRight.dispose();
        jumpRight.dispose();
        attackRight.dispose();
    }
}
