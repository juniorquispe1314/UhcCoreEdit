package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
// La papu clase, add new scen Cobwebless
// Disable cobwebs

public class CobWebLessListener extends ScenarioListener {

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {

		if(e.getPlayer().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if (e.getBlock().getType().equals(Material.COBWEB)){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED+ Lang.SCENARIO_COBWEBLESS_ENABLED);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		if(e.isCancelled()){
			return;
		}

		if (e.getBlock().getType().equals(Material.COBWEB) &&
			e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.STRING));

		}

	}


}
