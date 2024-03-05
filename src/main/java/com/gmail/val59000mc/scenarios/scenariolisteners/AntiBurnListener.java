package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiBurnListener extends ScenarioListener {

	@EventHandler

	public void onDamage(EntityDamageEvent e) {
		if (e.getEntityType() == EntityType.DROPPED_ITEM &&
			(e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
			e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
			e.getCause() == EntityDamageEvent.DamageCause.CONTACT ||
			e.getCause() == EntityDamageEvent.DamageCause.CRAMMING ||
			e.getCause() == EntityDamageEvent.DamageCause.CUSTOM ||
			e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
			e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR ||
			e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION ||
			e.getCause() == EntityDamageEvent.DamageCause.LAVA))
		{
			e.setCancelled(true);

		}

	}
}
