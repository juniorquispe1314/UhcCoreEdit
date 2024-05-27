package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class DemolitionArcheryListener extends ScenarioListener {

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {

		if (event.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if(isEnabled(Scenario.EXPLOSIVE_ARROWS)){
			return;
		}

		if(event.getEntity() instanceof Arrow && event.getHitBlock() != null) {

			Block hitBlock = event.getHitBlock();

			if(hitBlock.getType().toString().contains("OBSIDIAN") || hitBlock.getType().equals(Material.END_PORTAL)
				|| hitBlock.getType().equals(Material.BEDROCK) || hitBlock.getType().equals(Material.END_PORTAL_FRAME)){
				//its an unbreackable block, remove the arrow because of avoiding deleted blocks chain
				event.getEntity().remove();
			}else{
				hitBlock.setType(Material.AIR);
				event.getEntity().remove();
			}

		}

	}

}
