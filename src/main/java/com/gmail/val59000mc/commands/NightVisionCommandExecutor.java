package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
//La papu clase, add new command /nv
// add potion effect to player, night vision for entire the game

public class NightVisionCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;

	public NightVisionCommandExecutor(PlayerManager playerManager){
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

		if ((args.length == 0) && (uhcPlayer.getState().equals(PlayerState.PLAYING))){
			try {
				uhcPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999,0, false, false));
				uhcPlayer.sendMessage(ChatColor.DARK_GREEN+"You have night vision now.");
			} catch (UhcPlayerNotOnlineException e) {
				uhcPlayer.sendMessage(Lang.COMMAND_CHAT_ERROR);
			}
			return true;
		}

		return true;
	}
}
