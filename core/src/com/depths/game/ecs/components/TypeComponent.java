package com.depths.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/*
 * Stores the type of entity this is
 */
public class TypeComponent implements Component, Poolable {
	public static enum Types {
		PLAYER, ENEMY, SCENERY, SPRING, BULLET, OTHER;
	}

	public Types type = Types.OTHER;
	
	@Override
	public void reset() {
		type = Types.OTHER;		
	}

}