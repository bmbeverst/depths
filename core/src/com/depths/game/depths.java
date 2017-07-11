package com.depths.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.depths.game.actors.Player;
import com.depths.game.world.World;
import com.depths.screens.GameScreen;

// Was ApplicationAdapter originally
public class depths extends Game{
	private SpriteBatch batch;
    private BitmapFont font;

    private float elapsedTime = 0;
	private float deltaTime = 0;
    
	private Player player;
	private World world;

	private Texture img;
	@Override
	public void create () {

        Gdx.app.log("depths", "created");
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("arial-15.fnt"),false);
        font.setColor(Color.RED);
        world = new World();
        player = new Player(world);
		img = new Texture("badlogic.jpg");
		setScreen(new GameScreen());
		
	}

	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        player.update();
        deltaTime = Gdx.graphics.getDeltaTime();

        world.getLights().render(batch, deltaTime);
		//batch.begin(); Done in lights
		batch.draw(img, 0, 0, 1000, 1000);
        font.draw(batch, "Hello World", 300, 200);
        world.render(batch);
        player.draw(batch, elapsedTime);
		batch.end();
        elapsedTime += deltaTime;
	}



	@Override
	public void resize(final int width, final int height) {
		world.getLights().resize(width, height);
		super.resize(width, height);
	}
	
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        player.dispose();
        
    }

}
