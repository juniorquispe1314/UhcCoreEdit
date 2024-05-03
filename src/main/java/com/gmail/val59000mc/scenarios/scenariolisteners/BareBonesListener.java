package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.UniversalMaterial;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
//papu clase
//Diamond and gold ores don't drop anything, you can't craft golden apples, anvils, or enchanting tables,
// and when you kill a player, they drop 1 diamond, 1 golden apple, 12 arrows, and 2 string.

public class BareBonesListener extends ScenarioListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 1));
		e.getDrops().add(new ItemStack(Material.DIAMOND, 1));
		e.getDrops().add(new ItemStack(Material.STRING, 2));
		e.getDrops().add(new ItemStack(Material.ARROW, 12));
	}

	@EventHandler
	public void onCraftItem(PrepareItemCraftEvent e) {
		Recipe recipe = e.getRecipe();
		if (recipe != null && (recipe.getResult().getType() == Material.ENCHANTING_TABLE || recipe.getResult().getType() == Material.ANVIL || recipe.getResult().getType() == Material.GOLDEN_APPLE)) {
			e.getInventory().setResult((ItemStack)null);
		}
	}

	@EventHandler(
		priority = EventPriority.LOW
	)
	public void onBlockBreak(BlockBreakEvent e) {

		if(e.getPlayer().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if (e.getBlock().getType() == Material.DIAMOND_ORE) {
			e.getBlock().setType(Material.AIR);
		}

		if (e.getBlock().getType() == UniversalMaterial.DEEPSLATE_DIAMOND_ORE.getType()) {
			e.getBlock().setType(Material.AIR);
		}

		if (e.getBlock().getType() == UniversalMaterial.GOLD_ORE.getType()) {
			e.getBlock().setType(Material.AIR);
		}

		if (e.getBlock().getType() == UniversalMaterial.DEEPSLATE_GOLD_ORE.getType()) {
			e.getBlock().setType(Material.AIR);
		}

		if (e.getBlock().getType() == UniversalMaterial.NETHER_GOLD_ORE.getType()) {
			e.getBlock().setType(Material.AIR);
		}

	}

}
