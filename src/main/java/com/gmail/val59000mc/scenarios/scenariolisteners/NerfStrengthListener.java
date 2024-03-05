package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
//el papu scenario
//nerft strength effect, reduce 50%
public class NerfStrengthListener extends ScenarioListener {

	@EventHandler
	public void strengthNerf(EntityDamageByEntityEvent e){

		if(e.getDamager() instanceof Player){
			Player damager = (Player) e.getDamager();
			PotionEffect strenght = damager.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);

			if(strenght != null){
				int strenghtAmplifier = 1 + strenght.getAmplifier();
				double differential = strenghtAmplifier * 1.5;
				e.setDamage(e.getDamage() - differential);
			}
		}
	}


}
