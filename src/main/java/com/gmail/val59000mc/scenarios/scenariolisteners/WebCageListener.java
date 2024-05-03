package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
// La papu clase, add new scen WebCage

public class WebCageListener extends ScenarioListener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){

		if(e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		Player p = e.getEntity();

		Location location = p.getLocation();
		double[] offsets = {-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0};

		for (double offsetX : offsets) {
			for (double offsetY : offsets) {
				for (double offsetZ : offsets) {
					if ((Math.abs(offsetX) == 3.0 && Math.abs(offsetY) != 3.0 && Math.abs(offsetZ) != 3.0) ||
						(Math.abs(offsetY) == 3.0 && Math.abs(offsetX) != 3.0 && Math.abs(offsetZ) != 3.0) ||
						(Math.abs(offsetZ) == 3.0 && Math.abs(offsetX) != 3.0 && Math.abs(offsetY) != 3.0)) {
						Location blockLocation = location.clone().add(offsetX, offsetY, offsetZ);
						blockLocation.getBlock().setType(Material.COBWEB);
					}
				}
			}
		}
	}



}
