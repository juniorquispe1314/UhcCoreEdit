package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.OreType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class GoldLessListener extends ScenarioListener{

	@EventHandler (priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent e){

		if(e.getPlayer().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if (OreType.isGold(e.getBlock().getType())){
			e.getBlock().setType(Material.AIR);
		}

	}

}
