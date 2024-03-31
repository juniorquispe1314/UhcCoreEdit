package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.events.UhcStartedEvent;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
// La papu clase, add new scen Fallout
// If you are not below Y=60 after 20 min, you will take 0.5 hearts of damage
public class FalloutListener extends ScenarioListener {

	private int taskId;
	//@Option(key = "time-before-start")
	private final long delay = 60*20;
	//@Option(key = "time-between-damage")
	private final long period = 10;
	//@Option(key = "y-layer")
	private final int yLayer = 60;

	public FalloutListener(){
		taskId = -1;
	}

	@EventHandler
	public void onGameStarted(UhcStartedEvent e){
		taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), new FalloutListener.FalloutThread(this), delay* TimeUtils.SECOND_TICKS);
	}

	@Override
	public void onEnable() {
		// start thread
		if (getGameManager().getGameState() == GameState.PLAYING ||
			getGameManager().getGameState() == GameState.DEATHMATCH){
			long timeUntilFirstRun = delay - GameManager.getGameManager().getElapsedTime();

			if (timeUntilFirstRun < 0){
				timeUntilFirstRun = 0;
			}

			if(getGameManager().getGameState() == GameState.DEATHMATCH){
				taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), new FalloutThread(this), 0);
			}else{
				taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), new FalloutThread(this), timeUntilFirstRun*TimeUtils.SECOND_TICKS);
			}


		}
	}

	@Override
	public void onDisable() {
		// stop thread
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

	public static class FalloutThread implements Runnable{

		private final FalloutListener listener;

		public FalloutThread(FalloutListener listener){
			this.listener = listener;
		}

		@Override
		public void run() {

			// damage players
			for (UhcPlayer uhcPlayer : GameManager.getGameManager().getPlayerManager().getOnlinePlayingPlayers()){
				if (uhcPlayer.getState() == PlayerState.PLAYING) {
					try {
						Player player = uhcPlayer.getPlayer();
						if (player.getLocation().getBlockY() > listener.yLayer) {
							String msg = Lang.SCENARIO_FALLOUT_DAMAGE;
							player.sendMessage(msg);
							UhcPlayer.damageIrreducible(player, 1);
						}
					} catch (UhcPlayerNotOnlineException ignored) {
						// Shouldn't happen
					}
				}
			}
			listener.taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), this, listener.period*TimeUtils.SECOND_TICKS);
		}

	}
}
