package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FastGetAwayListener extends ScenarioListener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();
			killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 1));
		}

	}
}
