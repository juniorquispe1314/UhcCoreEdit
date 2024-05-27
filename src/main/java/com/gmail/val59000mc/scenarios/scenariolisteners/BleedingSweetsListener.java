package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BleedingSweetsListener extends ScenarioListener{

	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerDeath(PlayerDeathEvent e){

		if(e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		e.getDrops().add(new ItemStack(Material.DIAMOND));
		e.getDrops().add(new ItemStack(Material.BOOK));
		e.getDrops().add(new ItemStack(Material.STRING));
		e.getDrops().add(new ItemStack(Material.GOLD_INGOT,5));
		e.getDrops().add(new ItemStack(Material.ARROW,16));
	}

}
