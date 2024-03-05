package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;
import com.gmail.val59000mc.utils.UniversalMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
//papu clase
//Killing a player increases a level of your armor or weapons (sharpness, power, or protection).

public class UpgradesListener extends ScenarioListener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){

		Player killer = e.getEntity().getKiller();

		if(killer == null){
			return;
		}

		ItemStack helmet = killer.getInventory().getHelmet();
		ItemStack chestplate = killer.getInventory().getChestplate();
		ItemStack leggins = killer.getInventory().getLeggings();
		ItemStack boots = killer.getInventory().getBoots();

		Inventory inv = killer.getInventory();

		boolean noArmor = (helmet == null || headPlayerOrSkull(helmet)) && (chestplate == null || chestplate.getType() == Material.ELYTRA) && (leggins == null) && (boots == null);
		boolean noSword = (!inv.contains(Material.IRON_SWORD)) && (!inv.contains(Material.DIAMOND_SWORD)) && (!inv.contains(UniversalMaterial.NETHERITE_SWORD.getType()));
		boolean noAxe = (!inv.contains(Material.IRON_AXE)) && (!inv.contains(Material.DIAMOND_AXE)) && (!inv.contains(UniversalMaterial.NETHERITE_AXE.getType()));
		boolean noBow = !inv.contains(Material.BOW);

		boolean itemEnchanted = false;

		if(noArmor && noSword && noAxe && noBow){
			//the player has nothing to echant
			String msg = ChatColor.BLUE + "[Upgrades] ";
			killer.sendMessage(msg + ChatColor.AQUA + "You do not have an item to upgrade.");
			Bukkit.getLogger().info(msg + killer.getDisplayName() + " dont have an item to upgrade.");
			return;
		}

		while(!itemEnchanted){

			int randomInteger = RandomUtils.randomInteger(1, 10);

			switch (randomInteger){
				case 1:
					if(helmet != null && !headPlayerOrSkull(helmet)){
						levelUp(helmet, Enchantment.PROTECTION_ENVIRONMENTAL,killer);
						itemEnchanted = true;
					}
					break;
				case 2:
					if(chestplate != null && chestplate.getType() != Material.ELYTRA){
						levelUp(chestplate, Enchantment.PROTECTION_ENVIRONMENTAL, killer);
						itemEnchanted = true;
					}
					break;
				case 3:
					if(leggins != null){
						levelUp(leggins, Enchantment.PROTECTION_ENVIRONMENTAL, killer);
						itemEnchanted = true;
					}
					break;
				case 4:
					if(boots != null){
						levelUp(boots, Enchantment.PROTECTION_ENVIRONMENTAL, killer);
						itemEnchanted = true;
					}
					break;
				case 5:
				case 6:
					if(inv.contains(UniversalMaterial.NETHERITE_SWORD.getType())){
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(), UniversalMaterial.NETHERITE_SWORD.getType(),Enchantment.DAMAGE_ALL, killer);
					}else if(inv.contains(Material.DIAMOND_SWORD)){
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(), Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, killer);
					} else if (inv.contains(Material.IRON_SWORD)) {
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(), Material.IRON_SWORD,Enchantment.DAMAGE_ALL, killer);
					}
					break;
				case 7:
				case 8:
					if(inv.contains(UniversalMaterial.NETHERITE_AXE.getType())){
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(), UniversalMaterial.NETHERITE_AXE.getType(),Enchantment.DAMAGE_ALL, killer);
					}else if(inv.contains(Material.DIAMOND_AXE)){
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(),Material.DIAMOND_AXE, Enchantment.DAMAGE_ALL,killer);
					} else if (inv.contains(Material.IRON_AXE)) {
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(), Material.IRON_AXE, Enchantment.DAMAGE_ALL, killer);
					}
					break;
				case 9:
				case 10:
					if(inv.contains(Material.BOW)){
						itemEnchanted = lookForMaterialToEnchant(inv.getContents(),Material.BOW,Enchantment.ARROW_DAMAGE, killer);
					}
					break;
			}
		}
	}
	private boolean lookForMaterialToEnchant(ItemStack[] inv, Material material, Enchantment enchantment, Player killer){
		for(ItemStack item : inv){
			if(item != null && item.getType() == material){
				levelUp(item, enchantment, killer);
				return true;
			}
		}
		return false;
	}
	private void levelUp(ItemStack item, Enchantment enchantment, Player killer){
		int lvl = item.getEnchantmentLevel(enchantment) + 1;
		item.addUnsafeEnchantment(enchantment, lvl);
		String msg = ChatColor.BLUE + "[Upgrades] ";
		killer.sendMessage(msg + ChatColor.AQUA+ "Your " + item.getType() +" has been upgraded to level "+ lvl);
		Bukkit.getLogger().info(msg + killer.getDisplayName() + " got an upgrade: "+ item.getType() + " to level " + lvl);
	}

	private boolean headPlayerOrSkull(ItemStack helmet){
		//if the item is not a helmet (head player, skull...) return true
		return helmet.getType().toString().contains("_HEAD") || helmet.getType().toString().contains("_SKULL");
	}

}
