package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class GappleRouletteListener extends ScenarioListener {

	private final List<PotionEffect> potsList = getPotsList();


	@EventHandler
	public void onConsume(PlayerItemConsumeEvent ev){

		if(ev.getItem().getType().equals(Material.GOLDEN_APPLE)){

			Player player = ev.getPlayer();
			int rand = RandomUtils.randomInteger(0,potsList.size() - 1);

			player.addPotionEffect(potsList.get(rand));

		}
	}

	private List<PotionEffect> getPotsList(){

		List<PotionEffect> potionEffects = Arrays.asList(
			new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 250, 0),
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 250, 0),
			new PotionEffect(PotionEffectType.WATER_BREATHING, 250, 0),
			new PotionEffect(PotionEffectType.BLINDNESS, 250, 0),
			new PotionEffect(PotionEffectType.CONDUIT_POWER, 250, 0),
			new PotionEffect(PotionEffectType.CONFUSION, 250, 0),
			new PotionEffect(PotionEffectType.FAST_DIGGING, 250, 0),
			new PotionEffect(PotionEffectType.GLOWING, 250, 0),
			new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 250, 1),
			new PotionEffect(PotionEffectType.HEALTH_BOOST, 250, 0),
			new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 0),
			new PotionEffect(PotionEffectType.INVISIBILITY, 250, 0),
			new PotionEffect(PotionEffectType.JUMP, 250, 2),
			new PotionEffect(PotionEffectType.LEVITATION, 250, 0),
			new PotionEffect(PotionEffectType.NIGHT_VISION, 250, 0),
			new PotionEffect(PotionEffectType.BAD_OMEN, 250, 1),
			new PotionEffect(PotionEffectType.SLOW, 250, 0),
			new PotionEffect(PotionEffectType.SLOW_FALLING, 250, 0),
			new PotionEffect(PotionEffectType.SPEED, 250, 0),
			new PotionEffect(PotionEffectType.LUCK, 250, 4));

		return potionEffects;
	}
}
