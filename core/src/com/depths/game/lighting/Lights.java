package com.depths.game.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Lights {
	

	//used for drawing
	private Texture light;
	private FrameBuffer fbo;

	//our different shaders.
	private ShaderProgram defaultShader;
	private ShaderProgram finalShader;
	
	//values passed to the shader
	public static final float ambientIntensity = .7f;
	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
	
	//light flicker
	private float[] lightSizes;
	private float[] halfLightSize;
	private int lightSizeIndex = 0;
	private boolean lightSizeForward = true;
	
	//read our shader files
	final String vertexShader = Gdx.files.internal("shaders/vertexShader.glsl").readString();
	final String defaultFragmentShader = Gdx.files.internal("shaders/defaultFragmentShader.glsl").readString();
	final String lightBasedFragmentShader =  Gdx.files.internal("shaders/fragmentShader.glsl").readString();
	
	public Lights() {
		ShaderProgram.pedantic = false;
		// Using my own vertex shader because it is simpler.
		defaultShader = new ShaderProgram(vertexShader, defaultFragmentShader);
		finalShader = new ShaderProgram(vertexShader, lightBasedFragmentShader);

		finalShader.begin();
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
				ambientColor.z, ambientIntensity);
		finalShader.end();
		//TODO Make sure the light is centered and remove unused space
		light = new Texture(Gdx.files.internal("lights/light.png"));
		generateSizes(400, 500, 10);
	}

	private void generateSizes(int min, int max, int step) {
		lightSizes = new float[(max-min)/step];
		halfLightSize = new float[(max-min)/step];
		float temp = min;
		
		for (int i = 0; i < lightSizes.length; i++) {
			lightSizes[i] = temp + 10f*MathUtils.random();
			halfLightSize[i] = lightSizes[i]*0.5f;
			temp += step;
		}
		
	}
	
	public void render(SpriteBatch batch, float deltaTime) {

		if (lightSizeIndex >= lightSizes.length-1) {
			lightSizeForward = false;
		} else if( lightSizeIndex <= 0 ) {
			lightSizeForward = true;
		}
		
		if (lightSizeForward) {
			lightSizeIndex++;
		} else {
			lightSizeIndex--;
		}
		
		
		fbo.begin();
		batch.setShader(defaultShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		Gdx.app.log("test", "Yesy:" + (100 - halfLightSize[lightSizeIndex]));
		batch.draw(light, 100 - halfLightSize[lightSizeIndex], 250 - halfLightSize[lightSizeIndex], lightSizes[lightSizeIndex], lightSizes[lightSizeIndex]);
		batch.draw(light, 200 - halfLightSize[lightSizeIndex], 50 - halfLightSize[lightSizeIndex], lightSizes[lightSizeIndex], lightSizes[lightSizeIndex]);
		batch.end();
		fbo.end();
	    Gdx.gl.glDisable(GL20.GL_BLEND);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setShader(finalShader);
		batch.begin();
		fbo.getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
		light.bind(0); //we force the binding of a texture on first texture unit to avoid artifacts
					   //this is because our default and ambient shader don't use multi texturing...
					   //You can basically bind anything, it doesn't matter
	}
	

	public void dispose() {
		finalShader.dispose();
		defaultShader.dispose();
		light.dispose();
		fbo.dispose();
	}

	public void resize(int width, int height) {
		fbo = new FrameBuffer(Format.RGBA8888, width, height, false);

		finalShader.begin();
		finalShader.setUniformf("resolution", width, height);
		finalShader.end();
	}
}
