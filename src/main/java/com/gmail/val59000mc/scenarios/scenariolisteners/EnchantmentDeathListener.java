package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
// La papu clase, add new scen EnchantmentDeath
// You can only get a enchantment table by killing a player.

public class EnchantmentDeathListener extends ScenarioListener {

	public EnchantmentDeathListener() {
	}

	@EventHandler
	public void onCraftItem(PrepareItemCraftEvent e) {
		Recipe recipe = e.getRecipe();
		if (recipe != null && recipe.getResult().getType() == Material.ENCHANTING_TABLE) {
			e.getInventory().setResult((ItemStack)null);
		}

	}

	@EventHandler(
		priority = EventPriority.LOW
	)
	public void onPlayerDeath(PlayerDeathEvent e) {

		if (e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		e.getDrops().add(new ItemStack(Material.ENCHANTING_TABLE, 1));
	}


}
