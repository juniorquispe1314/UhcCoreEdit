package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.exceptions.UhcTeamException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.game.handlers.ScoreboardHandler;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerState;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.players.UhcTeam;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommandExecutor implements CommandExecutor{

	private static final Logger LOGGER = Logger.getLogger(SpectateCommandExecutor.class.getCanonicalName());

	private final GameManager gameManager;
	private final ScoreboardHandler scoreboardHandler;

	public SpectateCommandExecutor(GameManager gameManager, ScoreboardHandler scoreboardHandler){
		this.gameManager = gameManager;
		this.scoreboardHandler = scoreboardHandler;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;

		if (gameManager.getGameState() != GameState.WAITING){
			player.sendMessage(Lang.COMMAND_SPECTATE_ERROR);
			return true;
		}

		UhcPlayer uhcPlayer = gameManager.getPlayerManager().getUhcPlayer(player);

		if (uhcPlayer.getState() == PlayerState.DEAD){
			setPlayerPlaying(player, uhcPlayer);
			player.sendMessage(Lang.COMMAND_SPECTATE_PLAYING);
			return true;
		}

		setPlayerSpectating(player, uhcPlayer);
		player.sendMessage(Lang.COMMAND_SPECTATE_SPECTATING);
		return true;
	}

	private void setPlayerSpectating(Player player, UhcPlayer uhcPlayer){
		uhcPlayer.setState(PlayerState.DEAD);

		// Clear lobby items
		player.getInventory().clear();

		if (!uhcPlayer.getTeam().isSolo()){
			try {
				UhcTeam oldTeam = uhcPlayer.getTeam();
				oldTeam.leave(uhcPlayer);

				scoreboardHandler.updatePlayerOnTab(uhcPlayer);
				scoreboardHandler.updateTeamOnTab(oldTeam);
			}catch (UhcTeamException ex){
				LOGGER.log(Level.WARNING, "Unable to leave team", ex);
			}
		}
	}

	private void setPlayerPlaying(Player player, UhcPlayer uhcPlayer){
		uhcPlayer.setState(PlayerState.WAITING);
		scoreboardHandler.updatePlayerOnTab(uhcPlayer);

		// Give lobby items back
		UhcItems.giveLobbyItemsTo(player);
	}

}
