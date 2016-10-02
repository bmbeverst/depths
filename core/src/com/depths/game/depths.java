package com.depths.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.depths.game.entities.Player;
import com.depths.game.world.World;

public class depths extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;

    private float elapsedTime = 0;
    
	private Player player;
	private World world;

	@Override
	public void create () {
        
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        player = new Player();
        world = new World();

		
	}

	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        player.update();
        
        
		batch.begin();
        font.draw(batch, "Hello World", 300, 200);
        world.render(batch);
        player.render(batch, elapsedTime);
        elapsedTime += Gdx.graphics.getDeltaTime();
		batch.end();
	}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        player.dispose();
        
    }

}
