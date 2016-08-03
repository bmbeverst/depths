package com.depths.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class depths extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
    private BitmapFont font;

    private TextureAtlas standRight;
    private TextureAtlas goRight;
    
    private Animation animationStandRight;
    private Animation animationGoRight;
    
    private float elapsedTime = 0;
    
    private Animation currentAnimation;
    
    float location = 100;
	private TextureRegion textureRegion;
	private boolean left;
	private int height;
	private int width;
	
	@Override
	public void create () {
        
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        
		standRight = new TextureAtlas(Gdx.files.internal("animations/StandRight-packed/pack.atlas"));
		animationStandRight = new Animation(1/2f, standRight.getRegions());
		goRight = new TextureAtlas(Gdx.files.internal("animations/GoRight-packed/pack.atlas"));
		animationGoRight = new Animation(1/12f, goRight.getRegions());
		
		height = standRight.findRegion("001").getRegionHeight();

		width = standRight.findRegion("001").getRegionWidth();
		
	}

	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        	location -= 1.5f;
            currentAnimation = animationGoRight;
            left = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
        	location += 1.5f;
            currentAnimation = animationGoRight;
            left = false;
        } else {
        	currentAnimation = animationStandRight;
        }
        	
        
        textureRegion = currentAnimation.getKeyFrame(elapsedTime, true);
        
		batch.begin();
		batch.draw(img, 0, 0);
        font.draw(batch, "Hello World", 300, 200);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(textureRegion, left ? location+width : location, 250, left ? -width : width, height);
		batch.end();
	}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        img.dispose();
        standRight.dispose();
        goRight.dispose();
        
    }

}
