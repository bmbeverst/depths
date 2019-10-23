package com.depths.game.ecs.componet;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component{

	public OrthographicCamera cam;
	public boolean isDead;
	public boolean onPlatform;
	public boolean onSpring;
	
}