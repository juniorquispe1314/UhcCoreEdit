package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
//La papu clase, add new command /clearitem
//removes the enchants from an item

public class ClearItemCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;

	public ClearItemCommandExecutor(PlayerManager playerManager){
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;
		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(player);

		if (uhcPlayer.getState() != PlayerState.PLAYING){
			uhcPlayer.sendMessage(Lang.COMMAND_CHAT_ERROR);
			return true;
		}

		if(args.length == 0 && uhcPlayer.getState().equals(PlayerState.PLAYING)){
			ItemStack is = player.getInventory().getItemInMainHand();
			ItemMeta im = is.getItemMeta();
			ItemStack isn = null;

			if(is.getEnchantments().size() > 0){
				//replace item
				isn = new ItemStack(is.getType(), 1,(short) ((Damageable) im).getDamage());
				player.getInventory().setItemInMainHand(isn);
				return true;
			}else if (is.getType().equals(Material.ENCHANTED_BOOK)){
				isn = new ItemStack(Material.BOOK);
				player.getInventory().setItemInMainHand(isn);
				return true;
			}
		}

		return true;
	}
}
