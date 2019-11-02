package com.depths.game.ecs.componets;

import com.badlogic.ashley.core.Component;
/*
 * Stores the type of entity this is
 */
public class TypeComponent implements Component {
	public static enum Types
	{
		PLAYER, ENEMY, SCENERY, SPRING, BULLET, OTHER;
	}
	public Types type = Types.OTHER;

}