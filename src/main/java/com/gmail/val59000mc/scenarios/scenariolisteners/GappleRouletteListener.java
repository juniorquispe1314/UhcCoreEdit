package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GappleRouletteListener extends ScenarioListener {


	@EventHandler
	public void onConsume(PlayerItemConsumeEvent ev){

		if(ev.getItem().getType().equals(Material.GOLDEN_APPLE)){
			Player player = ev.getPlayer();
			int rand = RandomUtils.randomInteger(1,30);

			if(rand == 1){
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 250, 0));
			} else if (rand == 2) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 250, 0));
			} else if (rand == 3) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 250, 0));
			} else if (rand == 4) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 250, 0));
			} else if (rand == 5) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 250, 0));
			} else if (rand == 6) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 0));
			} else if (rand == 7) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 250, 0));
			} else if (rand == 8) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 250, 0));
			} else if (rand == 9) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 250, 1));
			} else if (rand == 10) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 250, 0));
			} else if (rand == 11) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 0));
			} else if (rand == 12) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 250, 0));
			} else if (rand == 13) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 250, 2));
			} else if (rand == 14) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 250, 0));
			} else if (rand == 15) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 250, 0));
			} else if (rand == 16) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 250, 1));
			} else if (rand == 17) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 250, 0));
			} else if (rand == 18) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 250, 0));
			} else if (rand == 19) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 250, 0));
			} else if (rand == 20){
				player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 250, 4));
			}

		}
	}
}
