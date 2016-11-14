package com.depths.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.depths.game.entities.Player;
import com.depths.game.lighting.Lights;
import com.depths.game.world.World;

public class depths extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;

    private float elapsedTime = 0;
	private float deltaTime = 0;
    
	private Player player;
	private World world;
	private Lights lights = null;

	private Texture img;
	@Override
	public void create () {
        
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        player = new Player();
        world = new World();

        lights = new Lights();
		Gdx.app.log("MyTag", "Created");
		img = new Texture("badlogic.jpg");
		
	}

	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        player.update();
        deltaTime = Gdx.graphics.getDeltaTime();

		lights.render(batch, deltaTime);
		batch.draw(img, 0, 0, 1000, 1000);
		//batch.begin(); Done in lights
        font.draw(batch, "Hello World", 300, 200);
        world.render(batch);
        player.render(batch, elapsedTime);
		batch.end();
        elapsedTime += deltaTime;
	}



	@Override
	public void resize(final int width, final int height) {
		lights.resize(width, height);
	}
	
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        player.dispose();
        
    }

}
