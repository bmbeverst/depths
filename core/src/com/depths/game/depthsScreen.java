package com.depths.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.depths.game.actors.Player;
import com.depths.game.world.World;

// Was ApplicationAdapter originally
public class depthsScreen implements Screen{
	private SpriteBatch batch;
    private BitmapFont font;
	OrthographicCamera camera;


    private float elapsedTime = 0;
	private float deltaTime = 0;
    
	private Player player;
	private World world;

	private Texture img;
	
	
	public depthsScreen(depths game) {
        Gdx.app.log("depthsScreen", "Attached");
		this.batch = game.batch;
		this.font = game.font;
        font.setColor(Color.RED);
        world = new World();
        player = new Player(world);
		img = new Texture("badlogic.jpg");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		
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
        Gdx.app.log("depthsScreen", "resizing");
		world.getLights().resize(width, height);
	}
	
    @Override
    public void dispose() {
        Gdx.app.log("depthsScreen", "dispose called");
        player.dispose();
        
    }


	@Override
	public void show() {
		// TODO Auto-generated method stub
        Gdx.app.log("depthsScreen", "show called");
		
	}


	@Override
	public void hide() {
        Gdx.app.log("depthsScreen", "hide called");  
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
        Gdx.app.log("depthsScreen", "pause called");  
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
        Gdx.app.log("depthsScreen", "resume called");   
		// TODO Auto-generated method stub
		
	}

}
