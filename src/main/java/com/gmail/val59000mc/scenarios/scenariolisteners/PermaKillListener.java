package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PermaKillListener extends ScenarioListener{

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){

		if(e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		World world = getGameManager().getMapLoader().getUhcWorld(World.Environment.NORMAL);
		world.setTime(world.getTime() + 12000);
	}

}
