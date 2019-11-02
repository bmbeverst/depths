package com.depths.game.ecs.componets;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component{
		public OrthographicCamera cam = null;
		public boolean onPlatform = false;
		public boolean onSpring = false;
		public boolean isDead = false;
		public float shootDelay = 0.5f;
		public float timeSinceLastShot = 0f;
}