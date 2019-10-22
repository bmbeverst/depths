package com.depths.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.depths.game.ecs.componet.CollisionComponent;
import com.depths.game.ecs.componet.PlayerComponent;
import com.depths.game.ecs.componet.TypeComponent;

public class CollisionSystem  extends IteratingSystem {
	 ComponentMapper<CollisionComponent> cm;
	 ComponentMapper<PlayerComponent> pm;


	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class,PlayerComponent.class).get());
		
		 cm = ComponentMapper.getFor(CollisionComponent.class);
		 pm = ComponentMapper.getFor(PlayerComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = cm.get(entity);
		
		Entity collidedEntity = cc.collisionEntity;
		if(collidedEntity != null){
			TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
			if(type != null){
				switch(type.type){
				case TypeComponent.ENEMY:
					//do player hit enemy thing
					Gdx.app.log(this.getClass().getSimpleName(), "player hit enemy");
					break;
				case TypeComponent.SCENERY:
					//do player hit scenery thing
					Gdx.app.log(this.getClass().getSimpleName(), "player hit scenery");
					break;
				case TypeComponent.OTHER:
					//do player hit other thing
					Gdx.app.log(this.getClass().getSimpleName(), "player hit other");
					break; //technically this isn't needed				
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}
		
	}

}