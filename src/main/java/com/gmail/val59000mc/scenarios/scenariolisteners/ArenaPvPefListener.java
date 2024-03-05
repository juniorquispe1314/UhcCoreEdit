package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.ChatColor;

public class ArenaPvPefListener extends ScenarioListener {

	@Override
	public void onEnable() {
		getGameManager().broadcastMessage(ChatColor.AQUA + "OnEnableeeeee");
	}

	@Override
	public void onDisable() {
		getGameManager().broadcastMessage(ChatColor.RED + "OnEnableeeeee");
	}
}
