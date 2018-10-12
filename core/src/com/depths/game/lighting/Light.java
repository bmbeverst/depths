/**
 * 
 */
package com.depths.game.lighting;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author brooks
 *
 */
public class Light extends Actor {

	private float size;
	private int life = 500;
	
	public Light(float x, float y, float size) {
		this.setPosition(x, y);
		this.size = size;
	}

	public float getSize() {
		return size;
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		life--;
		return life;
	}

	public void setSize(float size) {
		this.size = size;
	}
}
