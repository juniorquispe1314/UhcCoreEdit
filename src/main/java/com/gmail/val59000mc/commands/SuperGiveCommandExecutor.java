package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperGiveCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;
	public SuperGiveCommandExecutor(PlayerManager playerManager){
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;
		org.bukkit.inventory.ItemStack itemInHand = player.getInventory().getItemInMainHand();
		String usage = ChatColor.AQUA + "Usage: /supergive <player>   or   /supergive @a";

		if(args.length == 0){
			player.sendMessage(usage);
			return true;
		}

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

		if(args.length != 1){
			player.sendMessage(usage);
			return true;
		}

		if(shulkerIsEmpty(reformed)){
			player.sendMessage(ChatColor.RED +"[ERROR] The shulker is EMPTY.");
			return true;
		}

		String whom = args[0];

		if(whom.equals("@a")){
			//if the command is correct and skulker is not empty give kit
			giveKitToAll(reformed, player);
		} else{
			UhcPlayer playerToGive= null;
			try {
				playerToGive = playerManager.getUhcPlayer(whom);
			} catch (UhcPlayerDoesNotExistException doesnotExist) {
				player.sendMessage(ChatColor.RED + "[ERROR] "+ whom + " player doesn't exist.");
				return true;
			}

			//the player exist
			try {
				givekitToPlayer(reformed, playerToGive.getPlayer(), player);
			} catch (UhcPlayerNotOnlineException e) {
				player.sendMessage(ChatColor.RED + "[ERROR] "+ whom + " is not online.");
			}

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

	private void giveKitToAll(List<ItemStack> listReformed , Player sender){

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
				}
			}
			sender.sendMessage(ChatColor.GRAY + "[SuperGive] Everyone has received " + p.getDisplayName() + " correctly.");

		}

	}

	private void givekitToPlayer(List<ItemStack> listReformed, Player playerToGive, Player senderMsg){

		for (ItemStack item : listReformed){
			if(invFull(playerToGive)){
				Location loc = playerToGive.getLocation().add(0.5,0,0.5);
				loc.getWorld().dropItem(loc, item);
			}else{
				playerToGive.getInventory().addItem(item);
			}
		}

		senderMsg.sendMessage(ChatColor.GREEN + "[SUPERGIVE] "+playerToGive.getDisplayName()+" have received the kit successfully.");
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


