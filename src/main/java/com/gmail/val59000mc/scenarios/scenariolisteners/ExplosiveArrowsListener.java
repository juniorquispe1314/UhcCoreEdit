package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

public class ExplosiveArrowsListener extends ScenarioListener {

	private final Map<Arrow, Float> arrowForceMap = new HashMap<>();

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {

		if (event.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if((event.getEntity() instanceof Arrow) && (event.getEntity().getShooter() instanceof Player)) {
			Arrow arrow = (Arrow) event.getEntity();
			if(arrowForceMap.containsKey(arrow)){
				event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), (arrowForceMap.get(arrow) * (2.4f)));
				arrowForceMap.remove(arrow);
			}

			event.getEntity().remove();
		}
	}

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {

		if (event.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if ((event.getEntity() instanceof Player) && (event.getProjectile() instanceof Arrow)) {
			Arrow arrow = (Arrow) event.getProjectile();
			float force = event.getForce();
			arrowForceMap.put(arrow, force);
		}
	}
	@EventHandler
	public void onEntityDamageExplosion(EntityDamageEvent event) {

		if (event.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if((event.getEntity() instanceof Player) && (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
			|| event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){

			event.setDamage(event.getDamage() * 0.3f);

		}
	}





}
