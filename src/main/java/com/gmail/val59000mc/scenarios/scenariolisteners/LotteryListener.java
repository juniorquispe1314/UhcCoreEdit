package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class LotteryListener extends ScenarioListener {

	@EventHandler(
		priority = EventPriority.LOW
	)
	public void onPlayerDeath(PlayerDeathEvent e) {

		if (e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		e.getDrops().add(new ItemStack(getRandomIngot(), 64));
	}

	private Material getRandomIngot(){

		Material[] ingots = new Material[]{
			Material.DIAMOND,
			Material.GOLD_INGOT,
			Material.IRON_INGOT,
			Material.EMERALD
		};

		return ingots[RandomUtils.randomInteger(0, ingots.length - 1)];
	}

}
