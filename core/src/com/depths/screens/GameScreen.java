/**
 * 
 */
package com.depths.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author brooks
 *
 */
public class GameScreen implements Screen {

    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");
    }
    
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
        Gdx.app.log("GameScreen", "show called");

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
        // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
		Gdx.gl.glClearColor(0, 0, 0, 1);
        // Fills the screen with the selected color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
        Gdx.app.log("GameScreen", "pause called");   

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
        Gdx.app.log("GameScreen", "resume called");   

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
        Gdx.app.log("GameScreen", "hide called");  

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
