package com.gmail.val59000mc.threads;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class ArenaClearThread extends BukkitRunnable {

	private BukkitTask task;
	private int currentIndex = 0;
	private final List<String> comandsList = GameManager.getGameManager().getResetArenaCommands();
	@Override
	public void run() {

			//execute commnads to clear arena
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comandsList.get(currentIndex));
			currentIndex++;

			if(currentIndex == comandsList.size()){
				stop();
				getGameManager().setArenaIsCleaning(false);
			}

	}

	public void start() {
		//this will execute every 6 ticks (0.3s)
		task = runTaskTimer(UhcCore.getPlugin(), 5, 6);
	}

	public void stop() {
		if (task != null) {
			task.cancel();
		}
	}

}



