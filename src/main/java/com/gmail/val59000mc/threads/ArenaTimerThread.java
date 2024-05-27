package com.gmail.val59000mc.threads;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class ArenaTimerThread extends BukkitRunnable {

	public BukkitTask task;
	private int timeRemaining = ArenaWorld.TIME_TO_CLEAR_ARENA;
	@Override
	public void run() {

		if(timeRemaining == 0){
			//clear arena and stop this thread
			//start clear arena thread
			ArenaClearThread clearArenaThread = new ArenaClearThread();
			clearArenaThread.start();
			getGameManager().setArenaIsCleaning(true);
			timeRemaining = ArenaWorld.TIME_TO_CLEAR_ARENA;

		}else if(timeRemaining <= 10 ){
			getGameManager().broadcastMessage(ChatColor.GOLD+"Arena will be reset in "+timeRemaining + " s");
		}

		if(!getGameManager().isArenaCleaning()) {
			timeRemaining--;
		}


	}

	public void start() {
		//this will execute every 20 ticks (1s), TIMER
		task = runTaskTimer(UhcCore.getPlugin(), 5, 20);
	}

	public void stop() {
		if (task != null) {
			task.cancel();
		}
	}




}
