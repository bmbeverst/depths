package com.depths.game.ecs.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Mapper {
	public static final ComponentMapper<AnimationComponent> animCom = ComponentMapper.getFor(AnimationComponent.class);
	public static final ComponentMapper<B2dBodyComponent> b2dCom = ComponentMapper.getFor(B2dBodyComponent.class);
	public static final ComponentMapper<BulletComponent> bulletCom = ComponentMapper.getFor(BulletComponent.class);
	public static final ComponentMapper<CollisionComponent> collisionCom = ComponentMapper
			.getFor(CollisionComponent.class);
	public static final ComponentMapper<EnemyComponent> enemyCom = ComponentMapper.getFor(EnemyComponent.class);
	public static final ComponentMapper<PlayerComponent> playerCom = ComponentMapper.getFor(PlayerComponent.class);
	public static final ComponentMapper<StateComponent> stateCom = ComponentMapper.getFor(StateComponent.class);
	public static final ComponentMapper<TextureComponent> texCom = ComponentMapper.getFor(TextureComponent.class);
	public static final ComponentMapper<TransformComponent> transCom = ComponentMapper.getFor(TransformComponent.class);
	public static final ComponentMapper<TypeComponent> typeCom = ComponentMapper.getFor(TypeComponent.class);
//	public static final ComponentMapper<SteeringComponent> sCom = ComponentMapper.getFor(SteeringComponent.class);
	public static final ComponentMapper<ParticleEffectComponent> peCom = ComponentMapper.getFor(ParticleEffectComponent.class);
}