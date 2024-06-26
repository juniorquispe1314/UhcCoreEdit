package com.gmail.val59000mc.listeners;

import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.LocationUtils;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener{

	private final PlayerManager playerManager;

	public PlayerMovementListener(PlayerManager playerManager){
		this.playerManager = playerManager;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		handleFrozenPlayers(event);
	}

	private void handleFrozenPlayers(PlayerMoveEvent e){
		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(e.getPlayer());
		if (uhcPlayer.isFrozen()){
			Location freezeLoc = uhcPlayer.getFreezeLocation();
			Location toLoc = e.getTo();

			if (toLoc.getBlockX() != freezeLoc.getBlockX() || toLoc.getBlockZ() != freezeLoc.getBlockZ()){
				Location newLoc = toLoc.clone();
				newLoc.setX(freezeLoc.getBlockX() + .5);
				newLoc.setZ(freezeLoc.getBlockZ() + .5);

				e.getPlayer().teleport(LocationUtils.withSameDirection(newLoc, e.getPlayer()));
			}
		}
	}

}
