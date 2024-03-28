package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class RandomTeamCommandExecutor implements CommandExecutor {

	private final GameManager gameManager;

	public RandomTeamCommandExecutor(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;

		//check the number is a correct number
		if(args.length == 1){
			try{
				int randomTeamSize = Integer.parseInt(args[0]);
				int totalPlayers = gameManager.getPlayerManager().getPlayersList().size();

				if(randomTeamSize <= 0){
					sender.sendMessage(ChatColor.RED + "[ERROR] Only positive numbers are allowed");
					return true;
				}

				if(randomTeamSize >= totalPlayers){
					sender.sendMessage(ChatColor.RED + "[ERROR] Not possible a single team");
					return true;
				}
				List<UhcPlayer> playersList = new ArrayList<>(gameManager.getPlayerManager().getPlayersList());
				randomizeTeams(playersList, randomTeamSize, player);

			}catch (NumberFormatException noNumber){
				sender.sendMessage(ChatColor.RED + "[ERROR] The correct use of the command is /randomteam <number>");
			}

		}else{
			sender.sendMessage( ChatColor.AQUA + "Usage: /randomteam <number>");
		}

		/*Player player = (Player) sender;
		//UhcPlayer uhcPlayer = gameManager.getPlayerManager().getUhcPlayer(player);
		List<UhcPlayer> playersList = new ArrayList<>(gameManager.getPlayerManager().getPlayersList());

		Collections.shuffle(playersList);

		for(UhcPlayer uhcPlayer : playersList){

		}*/


		return true;
	}

	private void randomizeTeams(List<UhcPlayer> list, int teamSize, Player s){
		//ESTO FUNCIONA BIEN
		Collections.shuffle(list);
		int cont = 0;
		for(UhcPlayer uhcPlayer : list){
			cont++;
			//HACER TEAMS AQUI
			s.sendMessage(uhcPlayer.getName());
			if(cont == teamSize){
				s.sendMessage("-------------------------");
				cont = 0;
			}

		}
	}
}
