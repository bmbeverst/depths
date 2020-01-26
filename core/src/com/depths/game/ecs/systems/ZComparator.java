package com.depths.game.ecs.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.depths.game.ecs.components.TransformComponent;

/**
 * Created by barry on 12/8/15 @ 10:22 PM.
 */
public class ZComparator implements Comparator<Entity> {
	private ComponentMapper<TransformComponent> transformM;

	public ZComparator() {
		transformM = ComponentMapper.getFor(TransformComponent.class);
	}

	@Override
	public int compare(Entity entityA, Entity entityB) {
		return (int) Math.signum(transformM.get(entityB).position.z - transformM.get(entityA).position.z);
	}
}
