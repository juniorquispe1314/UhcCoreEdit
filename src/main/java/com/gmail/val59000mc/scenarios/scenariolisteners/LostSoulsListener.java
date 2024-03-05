package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
// La papu clase, add new scen LostSouls
// Send the coordinates of the player who has just died via global chat, the enviroment is sent also (nether, end or overworld)

public class LostSoulsListener extends ScenarioListener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player deadPlayer = e.getEntity();

		ChatColor c = getPlayerManager().getUhcPlayer(deadPlayer).getTeam().getColor();
		ChatColor ls = ChatColor.DARK_RED;
		ChatColor cr = ChatColor.RESET;

		String x = c+""+deadPlayer.getLocation().getBlockX()+cr;
		String y = c+""+deadPlayer.getLocation().getBlockY()+cr;
		String z = c+""+deadPlayer.getLocation().getBlockZ()+cr;
		String msg = " died at coords: "+"X: "+x+" Y: "+y+" Z: "+z;

		getGameManager().broadcastMessage(ls+"[Lost Souls] "+cr+deadPlayer.getDisplayName() + msg + ", in "+ deadPlayer.getWorld().getEnvironment());


	}


}
