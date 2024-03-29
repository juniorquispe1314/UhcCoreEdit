package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.VersionUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
// killing a player will give an extra heart

public class BloodHunterListener extends ScenarioListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		Player killer = e.getEntity().getKiller();

		if(killer == null){
			return;
		}

		double extraHeart = 2;
		double healthPlayer = killer.getHealth();
		double currentHeartContainers = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		//give an extra herat container
		VersionUtils.getVersionUtils().setPlayerMaxHealth(killer, currentHeartContainers + extraHeart);
		//heal player 1 heart
		killer.setHealth(healthPlayer + extraHeart);

	}

}
