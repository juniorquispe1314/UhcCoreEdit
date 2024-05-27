package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.LootGenerateEvent;

public class FirelessListener extends ScenarioListener{

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {

		if (e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if (e.getEntity() instanceof Player) {

			EntityDamageEvent.DamageCause cause = e.getCause();

			if (cause.equals(EntityDamageEvent.DamageCause.FIRE) || cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK) || cause.equals(EntityDamageEvent.DamageCause.LAVA)) {
				e.setCancelled(true);
			}
		}
	}

}
