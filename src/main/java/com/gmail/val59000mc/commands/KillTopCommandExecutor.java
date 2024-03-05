package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.languages.Lang;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.gmail.val59000mc.game.GameManager.getGameManager;
//La papu clase, add new command /kt
//show the kill top on chat

public class KillTopCommandExecutor implements CommandExecutor {
	private final PlayerManager playerManager;

	public KillTopCommandExecutor(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");
			return true;
		}

		Player player = (Player) sender;
		UhcPlayer uhcPlayer = playerManager.getUhcPlayer(player);

		if (args.length == 0 && (getGameManager().getGameState() == GameState.PLAYING ||
			getGameManager().getGameState() == GameState.DEATHMATCH ||
			getGameManager().getGameIsEnding() || getGameManager().getGameState() == GameState.ENDED ||
			getGameManager().getGameState() == GameState.LOADING)
		) {

			ChatColor aqua = ChatColor.AQUA;
			ChatColor red = ChatColor.RED;
			ChatColor gray = ChatColor.GRAY;
			ChatColor r = ChatColor.RESET;
			List<UhcPlayer> list = new ArrayList<>(getGameManager().getPlayerManager().getPlayersList());

			uhcPlayer.sendMessage(aqua + "Top kills in the UHC");
			sortPlayersByKills(list);
			int x = 1;
			for (UhcPlayer p : list) {
				uhcPlayer.sendMessage(gray +""+ x + ". " + r +p.getDisplayName() + red + " - " + p.getKills() + " kills");
				x++;
				if (x > 8) {
					break;
				}
			}

		}else {
			uhcPlayer.sendMessage(Lang.COMMAND_CHAT_ERROR);
		}
		return true;

	}

	public void sortPlayersByKills(List<UhcPlayer> listPlayers) {
		Collections.sort(listPlayers, new KillsComparator());
	}

	class KillsComparator implements Comparator<UhcPlayer> {
		@Override
		public int compare(UhcPlayer a, UhcPlayer b) {
			return Integer.compare(b.getKills(), a.getKills());
		}
	}

}


