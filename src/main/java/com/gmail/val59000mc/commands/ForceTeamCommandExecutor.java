package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class ForceTeamCommandExecutor implements CommandExecutor {

	private static GameManager gameManager;

	public ForceTeamCommandExecutor(GameManager gameManager) {
		ForceTeamCommandExecutor.gameManager = gameManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String senderName = ChatColor.WHITE + "[" + sender.getName().toString() + "] ";


		if(args.length != 2 || (args[0].equals(args[1]))){
			sender.sendMessage(ChatColor.RED + "Usage: /forceteam playerNoTeammed playerWithTeam");
			return true;
		}

		try {
			UhcPlayer playerNew = gameManager.getPlayerManager().getUhcPlayer(args[0]);
			playerNew.getTeam().getMembers().remove(playerNew);
			UhcPlayer playerWT = gameManager.getPlayerManager().getUhcPlayer(args[1]);
			UhcPlayer playerLeader = playerWT.getTeam().getLeader();
			playerLeader.getTeam().getMembers().add(playerNew);
			playerNew.setTeam(playerLeader.getTeam());
			gameManager.getScoreboardManager().updatePlayerOnTab(playerNew);
			getGameManager().broadcastMessage(senderName+ ChatColor.AQUA + playerNew.getName().toString() + " has been assigned to " + playerLeader.getName().toString() + "'s team.");
		} catch (UhcPlayerDoesNotExistException var9) {
			var9.printStackTrace();
		}

		return true;
	}
}
