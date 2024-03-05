

package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.customitems.CraftsManager;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class HBPlusListener extends ScenarioListener {

	@EventHandler
	public void onPlayerCraft(CraftItemEvent e) {
		ItemStack item = e.getCurrentItem();

		// Don't apply hastey boy effects to custom crafted items.
		if (CraftsManager.isCraftItem(item)) {
			return;
		}

		// Try to enchant the item, give up if it's not an enchantable tool
		try {
			item.addEnchantment(Enchantment.DIG_SPEED, 4);
			item.addEnchantment(Enchantment.DURABILITY, 2);
		} catch (IllegalArgumentException ignored) {}

	}


}
