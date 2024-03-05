package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamSizeCommandExecutor implements CommandExecutor {

	private GameManager gameManager;
	private TeamManager teamManager;
	public TeamSizeCommandExecutor(GameManager gameManager, TeamManager teamManager){
		this.gameManager = gameManager;
		this.teamManager = teamManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;
		ChatColor white = ChatColor.WHITE;
		ChatColor aqua = ChatColor.AQUA;
		String senderName = white + "[" + player.getDisplayName() + "]";

		if(args.length == 1){
			try{
				int newSize = Integer.parseInt(args[0]);

				if(newSize > 0){
					teamManager.setTeamSize(newSize);
					gameManager.broadcastMessage(senderName + aqua  + " Team Size has been updated to " + args[0]);
				}else {
					sender.sendMessage(ChatColor.RED +  "Only positive numbers are allowed");
				}
			}catch (NumberFormatException noNumber){
				sender.sendMessage(ChatColor.RED + "[ERROR] The correct use of the command is /teamsize <number>");
			}
		}else{
			sender.sendMessage( aqua + "Usage: /teamsize <number>");
			sender.sendMessage( ChatColor.GREEN + "CURRENT TEAMSIZE: " + teamManager.getTeamSize());
		}
		return true;
	}

}
