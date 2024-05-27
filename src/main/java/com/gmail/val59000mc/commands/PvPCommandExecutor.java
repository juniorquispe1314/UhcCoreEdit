package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.utils.UniversalSound;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.gmail.val59000mc.game.GameManager.getGameManager;

public class PvPCommandExecutor implements CommandExecutor {

	private final GameManager gameManager;
	private static final String USAGE_MESSAGE = ChatColor.AQUA + "[SetPvP] usage: /setpvp <on/off>";
	public PvPCommandExecutor(GameManager gameManager){
		this.gameManager = gameManager;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,  String label, String[] args) {

		if(gameManager.getGameState() == GameState.WAITING || gameManager.getGameState() == GameState.LOADING
			|| gameManager.getGameState() == GameState.STARTING || gameManager.getGameState() == GameState.ENDED){
			sender.sendMessage(ChatColor.RED + "You can not use this command now.");
			return true;
		}

		if(args.length == 1){
			String executer = "["+ sender.getName()+"]";

			if(args[0].equalsIgnoreCase("on")){
				setPvP(true, executer);
				gameManager.getPlayerManager().playSoundToAll(UniversalSound.WITHER_SPAWN.getSound());
				return true;
			}

			if(args[0].equalsIgnoreCase("off")){
				setPvP(false, executer);
				gameManager.getPlayerManager().playSoundToAll(Sound.BLOCK_BEACON_DEACTIVATE);
				return true;
			}
		}

		//show usage if cmd is incorrect
		sender.sendMessage(USAGE_MESSAGE);

		return true;
	}


	private void setPvP(boolean isEnabled, String executorName) {
		gameManager.setPvp(isEnabled);
		String status = isEnabled ? " PvP ON!" : " PvP OFF!";
		gameManager.broadcastInfoMessage(ChatColor.BLUE +"-------------------------------");
		gameManager.broadcastInfoMessage(ChatColor.AQUA + executorName + status);
		gameManager.broadcastInfoMessage(ChatColor.BLUE +"-------------------------------");
	}
}
