package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.threads.ArenaTimerThread;
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

		Player senderPlayer = (Player) sender;
		String errorCommandMSG = darkRed + "[Arena]" + red + " Incorrect command.";

		if (!gameManager.getGameState().equals(GameState.WAITING)){
			senderPlayer.sendMessage(red +"You cannot join the Arena at this time.");
			return true;
		}

		if(args.length > 1){
			senderPlayer.sendMessage(errorCommandMSG);
			return true;
		}

		//a sub command is the only possible option

		if(args.length == 0 || args[0].equalsIgnoreCase("join")){

			if(!gameManager.getArenaStatus()){
				senderPlayer.sendMessage(red + "Arena is currently closed");
				return true;
			}

			if(senderPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA)){
				senderPlayer.sendMessage(red + "You are already in the arena");
			}else{
				//player join arena
				ArenaWorld.joinArena(senderPlayer);
				ArenaWorld.giveKit(Objects.requireNonNull(senderPlayer.getPlayer()));
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("leave") && senderPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA) ) {
			senderPlayer.getInventory().clear();
			senderPlayer.setHealth(20);
			senderPlayer.teleport(Objects.requireNonNull(Bukkit.getWorld(ArenaWorld.NAME_WORLD_LOBBY)).getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> ArenaWorld.leaveArena(senderPlayer), 1);

			return true;
		}

		if(senderPlayer.isOp() && args[0].equalsIgnoreCase("close")){

			if(!gameManager.getArenaStatus()){
				senderPlayer.sendMessage(red + "The arena is already closed");
				return true;
			}

			//close arena
			gameManager.setArenaStatus(false);
			ArenaWorld.bringAllArenaPlayers();
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_DESTROY, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " CLOSED");

			gameManager.setArenaIsCleaning(false);
			gameManager.getArenaTimerThread().stop();
			gameManager.setArenaTimerThread(null);


			return true;
		}

		if(senderPlayer.isOp() && args[0].equalsIgnoreCase("open")){

			if(gameManager.getArenaStatus()){
				senderPlayer.sendMessage(red + "The arena is already open");
				return true;
			}

			//open arena
			gameManager.setArenaStatus(true);
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_USE, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " OPEN");

			//start thread timer
			gameManager.setArenaTimerThread(new ArenaTimerThread());
			gameManager.getArenaTimerThread().start();

			return true;

		}else{
			senderPlayer.sendMessage(errorCommandMSG);
			return true;
		}

	}

}
