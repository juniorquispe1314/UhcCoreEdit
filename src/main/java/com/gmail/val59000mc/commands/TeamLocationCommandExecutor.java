package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//La papu clase, add new command /tl
// send current coords ONLY TO team, the world is sent also (nether, end or overworld)
public class TeamLocationCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;
	public TeamLocationCommandExecutor(PlayerManager playerManager){
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

		if(args.length == 0 && uhcPlayer.getState().equals(PlayerState.PLAYING)){
			Location loc= player.getLocation();
			ChatColor cc =uhcPlayer.getTeam().getColor();
			ChatColor ccr = ChatColor.RESET;
			String location = " is in: "+"X: "+cc+loc.getBlockX()+ccr+" Y: "+cc+loc.getBlockY()+ccr+" Z: "+cc+loc.getBlockZ()+ccr;
			String world = null;

			if(player.getWorld().getEnvironment() == World.Environment.NETHER){
				world = "Nether";
			} else if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
				world = "Overworld";
			}else{
				world = "End";
			}

			uhcPlayer.getTeam().sendMessage(cc+ player.getDisplayName().toString() +ccr+ location + ", in: "+ world);

			return true;

		}else{
			player.sendMessage(Lang.COMMAND_CHAT_ERROR);
		}

		return true;
	}
}
