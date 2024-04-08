package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.customitems.GameItem;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.utils.ArenaWorld;
import com.gmail.val59000mc.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
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

			if(!gameManager.getArena()){
				uhcPlayer.sendMessage(red + "Arena is currently closed");
				return true;
			}

			if(uhcPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA)){
				uhcPlayer.sendMessage(red + "You are already in the arena");
			}else{
				//player join arena
				UhcPlayer a = gameManager.getPlayerManager().getUhcPlayer(uhcPlayer);
				ArenaWorld.joinArena(uhcPlayer);
				ArenaWorld.giveKit(Objects.requireNonNull(uhcPlayer.getPlayer()));
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("leave") && uhcPlayer.getWorld().getName().equalsIgnoreCase(ArenaWorld.NAME_WORLD_ARENA) ) {
			uhcPlayer.getInventory().clear();
			uhcPlayer.setHealth(20);
			uhcPlayer.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
			Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> leaveArena(uhcPlayer), 1);

			return true;
		}

		if(uhcPlayer.isOp() && args[0].equalsIgnoreCase("close")){
			gameManager.setArena(false);
			bringAllArenaPlayers();
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_DESTROY, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " CLOSED");
			return true;
		}

		if(uhcPlayer.isOp() && args[0].equalsIgnoreCase("open")){
			gameManager.setArena(true);
			gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_USE, 1,1);
			Bukkit.broadcastMessage(ChatColor.GOLD  + "[Arena]" + ChatColor.YELLOW + " OPEN");
			return true;
		}else{
			uhcPlayer.sendMessage(errorCommandMSG);
			return true;
		}

	}

	private void leaveArena(Player uhcPlayer){
		uhcPlayer.setGameMode(GameMode.ADVENTURE);
		UhcItems.giveLobbyItemsTo(uhcPlayer);
	}

	private void bringAllArenaPlayers(){

		List<Player> playersList = Objects.requireNonNull(Bukkit.getWorld(ArenaWorld.NAME_WORLD_ARENA)).getPlayers();

		for (Player p : playersList) {

			if(p.isOnline()){
				p.getInventory().clear();
				p.setHealth(20);
				p.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
			}

		}

		Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> leaveAllPlayersFromArena(playersList), 1);

	}

	private void leaveAllPlayersFromArena(List<Player> list){
		for(Player p : list){
			leaveArena(p);
		}
	}



	/*public void joinArena(Player uhcPlayer) {
		World world = Bukkit.getWorld(ArenaWorld.NAME_WORLD_ARENA);

		int rangeMax = ArenaWorld.MAX_RANGE;
		int rangeMin = ArenaWorld.MIN_RANGE;

		int ex = RandomUtils.randomInteger(rangeMin,rangeMax);
		int ez = RandomUtils.randomInteger(rangeMin,rangeMax);

		uhcPlayer.setGameMode(GameMode.SURVIVAL);
		uhcPlayer.getInventory().clear();
		//teleport to arena world
		uhcPlayer.teleport(Objects.requireNonNull(world).getBlockAt(ArenaWorld.X_SPAWN, ArenaWorld.Y_SPAWN, ArenaWorld.Z_SPAWN).getLocation().add(ex, 0, ez));

		//give kit
		ArenaWorld.giveKit(Objects.requireNonNull(uhcPlayer.getPlayer()));

	}*/
}
