package com.depths.game.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.depths.game.ecs.componets.CollisionComponent;
import com.depths.game.ecs.componets.EnemyComponent;
import com.depths.game.ecs.componets.Mapper;
import com.depths.game.ecs.componets.PlayerComponent;
import com.depths.game.ecs.componets.TypeComponent;

public class CollisionSystem extends IteratingSystem {
	ComponentMapper<CollisionComponent> cm;
	ComponentMapper<PlayerComponent> pm;

	public CollisionSystem() {
		super(Family.all(CollisionComponent.class).get());

		cm = ComponentMapper.getFor(CollisionComponent.class);
		pm = ComponentMapper.getFor(PlayerComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get collision for this entity
		CollisionComponent cc = cm.get(entity);
		// get collided entity
		Entity collidedEntity = cc.collisionEntity;

		TypeComponent thisType = entity.getComponent(TypeComponent.class);

		// Do Player Collisions
		if (thisType.type == TypeComponent.Types.PLAYER) {
			if (collidedEntity != null) {
				TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
				if (type != null) {
					switch (type.type) {
					case ENEMY:
						// do player hit enemy thing
						Gdx.app.log(this.getClass().getSimpleName(), "player hit enemy");
						PlayerComponent pl = pm.get(entity);
						pl.isDead = true;
						int score = (int) pl.cam.position.y;
						Gdx.app.log(this.getClass().getSimpleName(), "Score = " + score);
						break;
					case SCENERY:
						// do player hit scenery thing
						pm.get(entity).onPlatform = true;
//						Gdx.app.log(this.getClass().getSimpleName(), "player hit scenery");
						break;
					case SPRING:
						// do player hit other thing
						pm.get(entity).onSpring = true;
						Gdx.app.log(this.getClass().getSimpleName(), "player hit spring: bounce up");
						break;
					case OTHER:
						// do player hit other thing
						Gdx.app.log(this.getClass().getSimpleName(), "player hit other");
						break;
					case BULLET:
						Gdx.app.log(this.getClass().getSimpleName(), "Player just shot. bullet in player atm");
						break;
					default:
						Gdx.app.log(this.getClass().getSimpleName(), "Player no matching type found");
					}
					cc.collisionEntity = null; // collision handled reset component
				} else {
					Gdx.app.log(this.getClass().getSimpleName(), "type == null");
				}
			}
		} else if (thisType.type == TypeComponent.Types.ENEMY) { // Do enemy collisions
			if (collidedEntity != null) {
				TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
				if (type != null) {
					switch (type.type) {
					case PLAYER:
						Gdx.app.log(this.getClass().getSimpleName(), "enemy hit player");
						break;
					case ENEMY:
						Gdx.app.log(this.getClass().getSimpleName(), "enemy hit enemy");
						break;
					case SCENERY:
//						Gdx.app.log(this.getClass().getSimpleName(), "enemy hit scenery");
						break;
					case SPRING:
						Gdx.app.log(this.getClass().getSimpleName(), "enemy hit spring");
						break;
					case OTHER:
						Gdx.app.log(this.getClass().getSimpleName(), "enemy hit other");
						break;
					case BULLET:
						EnemyComponent enemy = Mapper.enemyCom.get(entity);
						enemy.isDead = true;
						Gdx.app.log(this.getClass().getSimpleName(), "enemy got shot");
						break;
					default:
						Gdx.app.log(this.getClass().getSimpleName(), "Enemy no matching type found");
					}
					cc.collisionEntity = null; // collision handled reset component
				} else {
					Gdx.app.log(this.getClass().getSimpleName(), "type == null");
				}
			}
		}
	}
}