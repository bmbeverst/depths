package com.depths.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class World {
	private DelayedRemovalArray<Chunk> chunks;
	
	public World() {
		chunks = new DelayedRemovalArray<Chunk>();
		chunks.add(new Chunk());
	}
	public void render(SpriteBatch batch) { 
		for (Chunk chunk : chunks) {
			chunk.render(batch);
			
		}
	}
}
