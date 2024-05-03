package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.exceptions.UhcTeamException;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class RandomTeamCommandExecutor implements CommandExecutor {

	private final PlayerManager playerManager;

	public RandomTeamCommandExecutor(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;

		if(getGameManager().getGameState() != GameState.WAITING){
			sender.sendMessage(ChatColor.RED + "[ERROR] It is only possible to randomize teams in WAITING state");
			return true;
		}

		//check the number is a correct number
		if(args.length == 1){
			try{
				int randomTeamSize = Integer.parseInt(args[0]);
				int totalPlayers = playerManager.getPlayersList().size();

				if(randomTeamSize <= 0){
					sender.sendMessage(ChatColor.RED + "[ERROR] Only positive numbers are allowed");
					return true;
				}

				if(randomTeamSize >= totalPlayers){
					sender.sendMessage(ChatColor.RED + "[ERROR] Not possible a single team");
					return true;
				}


				//random teams logic
				List<UhcPlayer> playersList = new ArrayList<>(playerManager.getPlayersList());

				removeAllTeams(playersList);
				randomizeTeams(playersList, randomTeamSize);

				String commandName = ChatColor.AQUA + "[Random Teams]";
				getGameManager().broadcastMessage(commandName +ChatColor.DARK_AQUA + " ALL TEAMS WERE RANDOMIZED");
				getGameManager().getPlayerManager().playSoundToAll(Sound.BLOCK_ANVIL_USE, 1,1);

			}catch (NumberFormatException noNumber){
				sender.sendMessage(ChatColor.RED + "[ERROR] The correct use of the command is /randomteam <number>");
			}

		}else{
			sender.sendMessage( ChatColor.AQUA + "Usage: /randomteam <number>");
		}

		return true;
	}


	private void randomizeTeams(List<UhcPlayer> list, int teamSize){
		Collections.shuffle(list);
		UhcPlayer leader = null;

		for(int i = 0 ; i < list.size() ; i += teamSize){
			int end = Math.min(i + teamSize, list.size());
			ArrayList<UhcPlayer> newTeam = new ArrayList<UhcPlayer>(list.subList(i, end));

			for(int j = 0; j < newTeam.size(); j++){
				if (j == 0) {
					leader = newTeam.get(j);
				} else {
					//remove team (member)
					UhcPlayer member = newTeam.get(j);
					member.getTeam().getMembers().remove(member);

					//add member to new team
					leader.getTeam().getMembers().add(member);
					member.setTeam(leader.getTeam());

					getGameManager().getScoreboardManager().updatePlayerOnTab(member);
					getGameManager().getScoreboardManager().updateTeamOnTab(member.getTeam());

				}
			}
		}

	}

	private void removeAllTeams(List<UhcPlayer> list){

		//remove if player is spec
		list.removeIf(p -> p.getState() == PlayerState.DEAD);

		for(UhcPlayer p : list){

			if(p.getTeam().getMembers().size() > 1){
				UhcTeam oldTeam = p.getTeam();
				try {

					oldTeam.leave(p);

				} catch (UhcTeamException e) {
					list.remove(p);
					throw new RuntimeException("**ERROR WHEN LEAVING FROM THE TEAM: " + p.getDisplayName() + "** - " +e);
				}

				getGameManager().getScoreboardManager().updatePlayerOnTab(p);
				getGameManager().getScoreboardManager().updateTeamOnTab(oldTeam);
			}

		}

	}
}
