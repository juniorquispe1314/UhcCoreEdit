package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class AnvilLessListener extends ScenarioListener {

	@EventHandler
	public void onAnvilItem(PrepareAnvilEvent e) {
		e.setResult(null);
	}

	@EventHandler
	public void onCraftItem(PrepareItemCraftEvent e) {
		Recipe recipe = e.getRecipe();
		if (recipe != null && recipe.getResult().getType() == Material.ANVIL) {
			e.getInventory().setResult((ItemStack)null);
		}

	}
}
