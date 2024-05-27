package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class SuperGiveCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;
	private int playersProcessed = 0;
	public SuperGiveCommandExecutor(PlayerManager playerManager){
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		if(getGameManager().getGameState() != GameState.PLAYING){
			sender.sendMessage(ChatColor.RED + "You can not use this command now.");
			return true;
		}

		Player player = (Player) sender;
		org.bukkit.inventory.ItemStack itemInHand = player.getInventory().getItemInMainHand();

		if(playerManager.getPlayersList().isEmpty() || playerManager.getPlayersList() == null){
			player.sendMessage(ChatColor.RED +"No players online to give items to.");
			return true;
		}

		if(!isYellowShulker(itemInHand)){
			String yellow = ChatColor.YELLOW + "YELLOW";
			player.sendMessage(ChatColor.RED + "You must have a " +yellow + ChatColor.RED+" shulker in your main hand to use this command.");
			return true;
		}

		BlockStateMeta meta = (BlockStateMeta) itemInHand.getItemMeta();
		ShulkerBox shulker = (ShulkerBox) meta.getBlockState();

		List<ItemStack> withEmptySlots = Arrays.asList(shulker.getInventory().getContents());
		List<ItemStack> reformed = removeEmptySlots(withEmptySlots);

		if(shulkerIsEmpty(reformed)){
			player.sendMessage(ChatColor.RED +"[ERROR] The shulker is EMPTY.");
			return true;
		}

		if(args.length == 0){
			runGiveKitToAllThread(reformed, player);
			return true;
		}

		String whom = args[0];

		//give kit to a single player
		if(args.length == 1){

			UhcPlayer playerToGive= null;
			try {
				playerToGive = playerManager.getUhcPlayer(whom);
			} catch (UhcPlayerDoesNotExistException doesnotExist) {
				player.sendMessage(ChatColor.RED + "[ERROR] "+ whom + " player doesn't exist.");
				return true;
			}

			//the player exist
			try {
				givekitToPlayer(reformed, playerToGive.getPlayer());
				player.sendMessage(ChatColor.GREEN + "[SUPERGIVE] "+playerToGive.getDisplayName()+" have received the kit successfully.");
			} catch (UhcPlayerNotOnlineException e) {
				player.sendMessage(ChatColor.RED + "[ERROR] "+ whom + " is not online.");
			}

		}else{
			player.sendMessage(ChatColor.AQUA + "Usage: /supergive <player>   or   /supergive ");
		}

		return true;
	}

	private List<ItemStack> removeEmptySlots(List<ItemStack> withEmptySlots){
		List<ItemStack> noEmptySlots = new ArrayList<>();

		for (ItemStack item : withEmptySlots){
			if(item != null){
				noEmptySlots.add(item);
			}
		}

		return noEmptySlots;
	}

	private void runGiveKitToAllThread(List<ItemStack> lr , Player s){
		//this will execute every 6 ticks (0.3s)
		List<UhcPlayer> list = playerManager.getPlayersList();
		BukkitTask taks = new BukkitRunnable() {
			@Override
			public void run() {

				String strPlayerName = list.get(playersProcessed).getDisplayName();

				try {
					givekitToPlayer(lr,list.get(playersProcessed).getPlayer());
					getGameManager().broadcastMessage(ChatColor.GOLD + "[✦] "+strPlayerName+" has received the kit");
				} catch (UhcPlayerNotOnlineException e) {
					s.sendMessage(ChatColor.RED+"[SUPERGIVE] "+ strPlayerName+ChatColor.RED+" is not online.");
				}

				playersProcessed++;

				if(playersProcessed == playerManager.getPlayersList().size()){
					playersProcessed = 0;
					s.sendMessage(ChatColor.BLUE + "------------------------------------------");
					s.sendMessage(ChatColor.AQUA + "[SUPERGIVE] Everyone has received the kit correctly.");
					s.sendMessage(ChatColor.BLUE + "------------------------------------------");
					s.playSound(s.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1,1);
					cancel();
				}

			}

		}.runTaskTimer(UhcCore.getPlugin(), 5, 6);
	}

	private void givekitToPlayer(List<ItemStack> listReformed, Player playerToGive){

		for (ItemStack item : listReformed){
			if(invFull(playerToGive)){
				Location loc = playerToGive.getLocation().add(0.5,0,0.5);
				loc.getWorld().dropItem(loc, item);
			}else{
				playerToGive.getInventory().addItem(item);
			}
		}
	}

	private void giveKitToAll(List<ItemStack> listReformed , Player sender){ //deprecated

		for(UhcPlayer p : playerManager.getPlayersList()){
			for(ItemStack item : listReformed){
				try {
					if(invFull(p.getPlayer())){
						Location loc = p.getPlayer().getLocation().add(0.5,0,0.5);
						loc.getWorld().dropItem(loc, item);
					}else{
						p.getPlayer().getInventory().addItem(item);
					}

				} catch (UhcPlayerNotOnlineException e) {
					sender.sendMessage(ChatColor.RED +"[SUPERGIVE] "+p.getDisplayName()+ " is not online.");
					playersProcessed++;
				}
			}
			sender.sendMessage(ChatColor.GRAY + "[SuperGive] Everyone has received " + p.getDisplayName() + " correctly.");

		}
	}

	private boolean isYellowShulker(ItemStack yellowShulker){
		return yellowShulker.getType().equals(Material.YELLOW_SHULKER_BOX);
	}

	private boolean shulkerIsEmpty(List<ItemStack> emptyShulker){
		return emptyShulker.size() == 0;
	}

	public boolean invFull(Player p) {
		return p.getInventory().firstEmpty() == -1;
	}

}


