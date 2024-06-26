package com.gmail.val59000mc.game.handlers;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.configuration.VaultManager;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomEventHandler {

	private static final Logger LOGGER = Logger.getLogger(CustomEventHandler.class.getCanonicalName());

	private final MainConfig config;

	public CustomEventHandler(MainConfig config){
		this.config = config;
	}

	public void handleTimeEvent(Set<UhcPlayer> playingPlayers, long time) {
		if(!config.get(MainConfig.ENABLE_TIME_EVENT)) {
			return;
		}

		long intervalTimeEvent = config.get(MainConfig.INTERVAL_TIME_EVENTS);

		if (time%intervalTimeEvent != 0){
			return;
		}

		double reward = config.get(MainConfig.REWARD_TIME_EVENT);
		List<String> timeCommands = config.get(MainConfig.TIME_COMMANDS);
		List<String> timeCommandsPlayers = new ArrayList<>();

		for (String cmd : timeCommands){
			if (cmd.contains("%name%")){
				timeCommandsPlayers.add(cmd);
			}
		}
		timeCommands.removeAll(timeCommandsPlayers);

		String message = Lang.EVENT_TIME_REWARD
				.replace("%time%", TimeUtils.getFormattedTime(intervalTimeEvent))
				.replace("%totaltime%", TimeUtils.getFormattedTime(time))
				.replace("%money%", "" + reward);

		for (UhcPlayer uhcPlayer : playingPlayers) {
			try {
				Player p = uhcPlayer.getPlayer();

				// Time Commands per player
				timeCommandsPlayers.forEach(cmd -> {
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%name%", uhcPlayer.getRealName()));
					} catch (CommandException exception) {
						LOGGER.log(Level.WARNING, "Failed to execute time reward command: " + cmd, exception);
					}
				});

				// Money rewards
				if (reward > 0) {
					VaultManager.addMoney(p, reward);
					uhcPlayer.sendMessage(message);
				}
			} catch (UhcPlayerNotOnlineException e) {
				// Ignore offline players
			}
		}

		// Time commands
		timeCommands.forEach(cmd -> {
			if (cmd.startsWith("/")){
				cmd = cmd.substring(1);
			}

			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
			} catch (CommandException exception) {
				LOGGER.log(Level.WARNING, "Failed to execute time reward command: " + cmd, exception);
			}
		});
	}

	public void handleKillEvent(Player killer, UhcPlayer uhcKiller) {
		if(!config.get(MainConfig.ENABLE_KILL_EVENT)) {
			return;
		}

		double reward = config.get(MainConfig.REWARD_KILL_EVENT);
		List<String> killCommands = config.get(MainConfig.KILL_COMMANDS);

		if (reward > 0) {
			VaultManager.addMoney(killer, reward);
			uhcKiller.sendMessage(Lang.EVENT_KILL_REWARD.replace("%money%", Double.toString(reward)));
		}

		// If the list is empty, this will never execute
		killCommands.forEach(cmd -> {
			if (cmd.startsWith("/")){
				cmd = cmd.substring(1);
			}

			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%name%", uhcKiller.getRealName()));
			} catch (CommandException exception){
				LOGGER.log(Level.WARNING, "Failed to execute kill reward command: " + cmd, exception);
			}
		});
	}

	public void handleWinEvent(Set<UhcPlayer> winners) {
		if(!config.get(MainConfig.ENABLE_WIN_EVENT)) {
			return;
		}

		double reward = config.get(MainConfig.REWARD_WIN_EVENT);
		List<String> winCommands = config.get(MainConfig.WIN_COMMANDS);
		List<String> winCommandsPlayer = new ArrayList<>();
		for (String cmd : winCommands){
			if (cmd.contains("%name%")){
				winCommandsPlayer.add(cmd);
			}
		}
		winCommands.removeAll(winCommandsPlayer);

		for(UhcPlayer player : winners) {
			try {
				if (reward > 0) {
					player.sendMessage(Lang.EVENT_WIN_REWARD.replace("%money%", Double.toString(reward)));
					VaultManager.addMoney(player.getPlayer(), reward);
				}

				winCommandsPlayer.forEach(cmd -> {
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%name%", player.getRealName()));
					} catch (CommandException exception){
						LOGGER.log(Level.WARNING, "Failed to execute win reward command: " + cmd, exception);
					}
				});
			} catch (UhcPlayerNotOnlineException e) {
				// no reward for offline players
			}
		}

		winCommands.forEach(cmd -> {
			if (cmd.startsWith("/")){
				cmd = cmd.substring(1);
			}

			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
			} catch (CommandException exception) {
				LOGGER.log(Level.WARNING, "Failed to execute win reward command: " + cmd, exception);
			}
		});
	}

}
