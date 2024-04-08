package com.gmail.val59000mc.listeners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.handlers.PlayerDeathHandler;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

	private final PlayerDeathHandler playerDeathHandler;

	public PlayerDeathListener(PlayerDeathHandler playerDeathHandler) {
		this.playerDeathHandler = playerDeathHandler;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {

		//when the player died in arena pvp
		if(event.getEntity().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			Player player = event.getEntity();
			event.getDrops().removeAll(event.getDrops());
			Objects.requireNonNull(player.getLocation().getWorld()).dropItemNaturally(player.getLocation(),ArenaWorld.ITEM_DROPED_BY_DEATH);

			//Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> respawnPlayerOnArena(player), 3);
			//player.loadData();
			return;

		}

		playerDeathHandler.handlePlayerDeath(event);


	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event){

		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "PLAYER RESPAWN EVENTSSSSSSSSSSSS");

		PlayerManager pm = GameManager.getGameManager().getPlayerManager();
		UhcPlayer uhcPlayer = pm.getUhcPlayer(event.getPlayer());

		if(event.getPlayer().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> respawnPlayerOnArena(event.getPlayer()), 0);
		}else if(uhcPlayer.getState().equals(PlayerState.DEAD)){
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> pm.setPlayerSpectateAtLobby(uhcPlayer), 1);
		}
	}


	private void respawnPlayerOnArena(Player player){
		player.setHealth(20);
		ArenaWorld.joinArena(player);
		ArenaWorld.giveKit(player);
	}



}
