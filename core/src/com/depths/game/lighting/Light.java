/**
 * 
 */
package com.depths.game.lighting;

/**
 * @author brooks
 *
 */
public class Light {

	private float x, y, size;
	private int life = 500;
	
	public Light(float x, float y, float size) {
		this.x = x;
		this.y = y;
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



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + life;
		result = prime * result + Float.floatToIntBits(size);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Light other = (Light) obj;
		if (life != other.life)
			return false;
		if (Float.floatToIntBits(size) != Float.floatToIntBits(other.size))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}



	public void setSize(float size) {
		this.size = size;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void update(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
