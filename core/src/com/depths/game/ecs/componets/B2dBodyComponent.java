package com.depths.game.ecs.componets;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class B2dBodyComponent implements Component, Poolable {
	public Body body;
	public boolean isDead = false;

	@Override
	public void reset() {
		body = null;
		isDead = false;
	}

}