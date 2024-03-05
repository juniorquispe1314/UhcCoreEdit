package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.enchantments.Enchantment.*;
// La papu clase, add new scen Cobwebless
// Avoid fire and flame

public class ColdWeaponsListener extends ScenarioListener {

	@EventHandler
	public void onEnchantItem(EnchantItemEvent e){

		boolean swordFire = e.getEnchantsToAdd().containsKey(FIRE_ASPECT);
		boolean bowFlame = e.getEnchantsToAdd().containsKey(ARROW_FIRE);

		if(swordFire){
			e.getEnchantsToAdd().remove(FIRE_ASPECT);
		}
		if(bowFlame){
			e.getEnchantsToAdd().remove(ARROW_FIRE);
		}
	}

	@EventHandler
	public void onAnvilItem(PrepareAnvilEvent e){

		if(e.getResult() == null){
			return;
		}

		boolean fireOn = e.getResult().containsEnchantment(FIRE_ASPECT);
		boolean flameOn = e.getResult().containsEnchantment(ARROW_FIRE);

		if(fireOn){
			e.setResult(null);
		}

		if(flameOn){
			e.setResult(null);
		}

	}

	@EventHandler
	public void onPVPFire(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){
			ItemStack weapon = ((Player) e.getDamager()).getInventory().getItemInMainHand();

			if(weapon.getEnchantmentLevel(FIRE_ASPECT) > 0){
				weapon.removeEnchantment(FIRE_ASPECT);
			}
		} else if (e.getDamager() instanceof Projectile) {
			Projectile arrow = (Projectile) e.getDamager();

			if(arrow.getShooter() instanceof Player){

				Player shooter = (Player) arrow.getShooter();
				ItemStack[] inv = shooter.getInventory().getContents();

				for (int i = 0 ; i< inv.length; i++){

					if(i <= 8 && inv[i] != null){
						inv[i].removeEnchantment(ARROW_FIRE);
					}

					if(i == 8){
						break;
					}

				}
			}


		}

		/*
		//ESTE METODO RECORRE EL INVENTARIO ENTERO, por cada flecha
		if(e.getDamager() instanceof Projectile){
			Projectile arrow = (Projectile) e.getDamager();

			if(arrow.getShooter() instanceof Player){

				Player shooter = (Player) arrow.getShooter();
				PlayerInventory inv = shooter.getInventory();

				for(ItemStack bow : inv){
					if(bow != null && bow.containsEnchantment(ARROW_FIRE)){
						bow.removeEnchantment(ARROW_FIRE);
					}
				}

			}
		}*/
	}


}
