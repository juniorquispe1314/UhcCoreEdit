package com.gmail.val59000mc.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectUtil {


	public static void addRandomUniquePotionEffect(Player player) {

		List<PotionEffectType> currentPlayerEffects = new ArrayList<>();
		for (PotionEffect effect : player.getActivePotionEffects()) {
			currentPlayerEffects.add(effect.getType());
		}

		List<PotionEffectType> possibleEffects = new ArrayList<>();
		possibleEffects.add(PotionEffectType.SPEED);
		possibleEffects.add(PotionEffectType.INCREASE_DAMAGE);
		possibleEffects.add(PotionEffectType.FIRE_RESISTANCE);
		possibleEffects.add(PotionEffectType.FAST_DIGGING);
		possibleEffects.add(PotionEffectType.DOLPHINS_GRACE);
		possibleEffects.add(PotionEffectType.DAMAGE_RESISTANCE);
		possibleEffects.add(PotionEffectType.INVISIBILITY);

		// Remove effects that player already has
		possibleEffects.removeAll(currentPlayerEffects);

		if (!possibleEffects.isEmpty()) {
			// Choose a random effect from remaining possible effects
			PotionEffectType chosenEffect = possibleEffects.get(RandomUtils.randomInteger(0, possibleEffects.size() - 1));
			player.addPotionEffect(new PotionEffect(chosenEffect, RandomUtils.randomInteger(2000, 4800), 0));
		}
	}
}
