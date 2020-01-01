package com.depths.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Title extends Actor {

	private AtlasRegion image;
	private BitmapFont font;
	private String text = "Starting";

	public Title(AtlasRegion ar) {
		super();
		image = ar;
		this.setWidth(80);
		this.setHeight(100);
		font = new BitmapFont(Gdx.files.internal("arial-15.fnt"), false);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(image, getX(), getY(), 80, 80);
		font.draw(batch, "Welcome to Depths!!!", getX() - 35, getY() - 10);
		font.draw(batch, text, getX() - text.length(), getY() - 25);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}

	public void setText(String text) {
		this.text = text;
	}

}
