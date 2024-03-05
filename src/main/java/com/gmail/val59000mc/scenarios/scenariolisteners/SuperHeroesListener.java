package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.events.PlayerStartsPlayingEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;
import com.gmail.val59000mc.utils.VersionUtils;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuperHeroesListener extends ScenarioListener{

	private static final Logger LOGGER = Logger.getLogger(SuperHeroesListener.class.getCanonicalName());

	@EventHandler
	public void onGameStart(PlayerStartsPlayingEvent e){
		addHeroesEffect(e.getUhcPlayer(), RandomUtils.randomInteger(0, 5));
	}

	private void addHeroesEffect(UhcPlayer uhcPlayer, int effect){

		Player player;

		try {
			player = uhcPlayer.getPlayer();
		} catch (UhcPlayerNotOnlineException ignored) {
			// No effect for offline player
			return;
		}

		switch (effect){
			case 0:
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999,1, false, false));
				break;
			case 1:
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999,0, false, false));
				break;
			case 2:
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999,0, false, false));
				break;
			case 3:
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999,0, false, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999,1, false, false));
				break;
			case 4:
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999,1, false, false));
				break;
			case 5:
				//double maxHealth = 40;
				//VersionUtils.getVersionUtils().setPlayerMaxHealth(player, maxHealth);
				//player.setHealth(maxHealth);
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 999999,4, false, false));
				try {
					uhcPlayer.healFully(); // Fill the newly added hearts
				} catch (UhcPlayerNotOnlineException ignored) {
					// Shouldn't happen
				}

				break;
			default:
				LOGGER.warning("No effect for: " + effect);
				break;
		}
	}

}
