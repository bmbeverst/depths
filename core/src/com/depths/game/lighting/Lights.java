package com.depths.game.lighting;

import java.util.ArrayList;

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

	//our shaders.
	private ShaderProgram defaultShader;
	private ShaderProgram finalShader;
	
	//values passed to the shader
	public static final float ambientIntensity = .7f;
	public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
	
	//used to make the light flicker
	public float zAngle;
	public static final float zSpeed = 15.0f;
	public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;
	
	//read our shader files
	final String vertexShader = Gdx.files.internal("shaders/vertexShader.glsl").readString();
	final String defaultFragmentShader = Gdx.files.internal("shaders/defaultFragmentShader.glsl").readString();
	final String lightBasedFragmentShader =  Gdx.files.internal("shaders/fragmentShader.glsl").readString();
	
	private ArrayList<Light> lights = new ArrayList<Light>(5);
	
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
	}

	
	public void render(SpriteBatch batch, float deltaTime) {


		zAngle += deltaTime * zSpeed;
		while(zAngle > PI2)
			zAngle -= PI2;
		
		fbo.begin();
		batch.setShader(defaultShader);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		float lightSizeSeed = (4.75f + 0.25f * (float)Math.sin(zAngle) + .2f*MathUtils.random());
		for(Light lightItem: lights) {
			if (lightItem.getLife() > 0) {
				float lightSize = lightSizeSeed*lightItem.getSize();
				batch.draw(light, lightItem.getX() - lightSize*0.5f + 0.5f, lightItem.getY() - lightSize*0.5f + 0.5f, lightSize, lightSize);
			}
		}

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
	
	public void addLight(Light light) {
		lights.add(light);
	}
	public void removeLight(Light light) {
		lights.remove(light);
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
