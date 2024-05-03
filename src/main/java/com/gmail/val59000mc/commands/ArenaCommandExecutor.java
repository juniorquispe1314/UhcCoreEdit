package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ArenaCommandExecutor implements CommandExecutor {
	private final GameManager gameManager;

	public ArenaCommandExecutor(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		ChatColor red = ChatColor.RED;
		ChatColor darkRed = ChatColor.DARK_RED;

		Player uhcPlayer = (Player) sender;
		String errorCommandMSG = darkRed + "[Arena]" + red + " Incorrect command.";

		if (!gameManager.getGameState().equals(GameState.WAITING)){
			uhcPlayer.sendMessage(red +"You cannot join the Arena at this time.");
			return true;
		}

		if(args.length > 1){
			uhcPlayer.sendMessage(errorCommandMSG);
			return true;
		}

		//a sub command is the only possible option

		if(args.length == 0 || args[0].equalsIgnoreCase("join")){

			if(!gameManager.getArenaStatus()){
				uhcPlayer.sendMessage(red + "Arena is currently closed");
				return true;
			}

			if(uhcPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA)){
				uhcPlayer.sendMessage(red + "You are already in the arena");
			}else{
				//player join arena
				//UhcPlayer a = gameManager.getPlayerManager().getUhcPlayer(uhcPlayer);
				ArenaWorld.joinArena(uhcPlayer);
				ArenaWorld.giveKit(Objects.requireNonNull(uhcPlayer.getPlayer()));
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("leave") && uhcPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA) ) {
			uhcPlayer.getInventory().clear();
			uhcPlayer.setHealth(20);
			uhcPlayer.teleport(Objects.requireNonNull(Bukkit.getWorld(ArenaWorld.NAME_WORLD_LOBBY)).getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> ArenaWorld.leaveArena(uhcPlayer), 1);

			return true;
		}

		if(uhcPlayer.isOp() && args[0].equalsIgnoreCase("close")){
			gameManager.setArenaStatus(false);
			ArenaWorld.bringAllArenaPlayers();
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_DESTROY, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " CLOSED");
			return true;
		}

		if(uhcPlayer.isOp() && args[0].equalsIgnoreCase("open")){
			gameManager.setArenaStatus(true);
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_USE, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " OPEN");
			return true;
		}else{
			uhcPlayer.sendMessage(errorCommandMSG);
			return true;
		}

	}

}
