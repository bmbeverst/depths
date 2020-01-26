package com.depths.game.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.depths.game.Depths;

public class PhysicsDebugSystem extends IteratingSystem {

	private Box2DDebugRenderer debugRenderer;
	private World world;
	private OrthographicCamera camera;
	private Depths parent;

	public PhysicsDebugSystem(World world, OrthographicCamera camera, Depths parent) {
		super(Family.all().get());
		debugRenderer = new Box2DDebugRenderer();
		this.world = world;
		this.camera = camera;
		this.parent = parent;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (parent.getPreferences().isDebugEnabled())
			debugRenderer.render(world, camera.combined);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}
}