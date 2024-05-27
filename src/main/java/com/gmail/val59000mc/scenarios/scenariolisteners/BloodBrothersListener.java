package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.PotionEffectUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BloodBrothersListener extends ScenarioListener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		if (e.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		if (e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();

			UhcPlayer p = getPlayerManager().getUhcPlayer(killer);

			if(p == null){
				return;
			}

			for(UhcPlayer member: p.getTeam().getMembers()){
				try {
					PotionEffectUtil.addRandomUniquePotionEffect(member.getPlayer());
				} catch (UhcPlayerNotOnlineException ex) {
					//not online
				}
			}
		}

	}


}
