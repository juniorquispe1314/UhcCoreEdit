package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GoldenRetrieverListener extends ScenarioListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().add(UhcItems.createGoldenHead());
	}
}
