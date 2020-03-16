package com.depths.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent implements Component, Poolable {
	public TextureRegion region = null;
	public float offsetY = 0f;
    public float offsetX = 0;
	
	@Override
	public void reset() {
		region = null;
	}
}