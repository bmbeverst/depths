package com.depths.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Chunk {

	private TextureAtlas tileAlias;
	private TextureRegion tile;
	private int width;
	
	public Chunk() {
		tileAlias = new TextureAtlas(Gdx.files.internal("scene/BlueTiles-packed/pack.atlas"));
		tile = new TextureRegion(tileAlias.findRegion("tileBlue27").getTexture(), tileAlias.findRegion("tileBlue27").getRegionX(), tileAlias.findRegion("tileBlue27").getRegionY(), tileAlias.findRegion("tileBlue27").getRegionWidth(),tileAlias.findRegion("tileBlue27").getRegionHeight());
		width = tileAlias.findRegion("tileBlue27").getRegionWidth();
	}
	
	public void render(SpriteBatch batch) {
		for (int x = 0; x < 10; x++) {
			batch.draw(tile, x*width, 210);
		}
		
	}

}
