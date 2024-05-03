package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.UniversalMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HeavyPocketsListener extends ScenarioListener {

	@EventHandler(
		priority = EventPriority.LOW
	)
	public void onPlayerDeath(PlayerDeathEvent e) {

		if (e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		e.getDrops().add(new ItemStack(UniversalMaterial.NETHERITE_SCRAP.getType(), 2));
	}
}
