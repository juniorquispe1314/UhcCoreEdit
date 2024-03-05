package com.gmail.val59000mc.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.exceptions.UhcPlayerDoesNotExistException;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.PlayerManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtocolUtils{

	private static final Logger LOGGER = Logger.getLogger(ProtocolUtils.class.getCanonicalName());

	private static NickNamePacketListener nickNameListener;

	public static void registerNickNameListener() {
		if (nickNameListener == null) {
			nickNameListener = new NickNamePacketListener();
			ProtocolLibrary.getProtocolManager().addPacketListener(nickNameListener);
		}
	}

	public static void unregisterNickNameListener() {
		if (nickNameListener != null) {
			ProtocolLibrary.getProtocolManager().removePacketListener(nickNameListener);
			nickNameListener = null;
		}
	}

	/***
	 * This method is used to change the player display name using ProtocolLib
	 * @param uhcPlayer The player you want to change the display-name for.
	 * @param nickName The wanted nick-name, set to null to reset. (Make sure its not over 16 characters long!)
	 */
	public static void setPlayerNickName(UhcPlayer uhcPlayer, String nickName){
		uhcPlayer.setNickName(nickName);

		try {
			// Make the player disappear and appear to update their name.
			updatePlayer(uhcPlayer.getPlayer());
		} catch (UhcPlayerNotOnlineException ignored) {
			// Don't update offline players
		}
	}

	/***
	 * This method can be used to change the tab header and footer.
	 * @param player The player to change the header / footer for
	 * @param header The new header
	 * @param footer The new footer
	 */
	public static void setPlayerHeaderFooter(Player player, String header, String footer){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(header));
		packet.getChatComponents().write(1, WrappedChatComponent.fromText(footer));
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
		}catch (InvocationTargetException ex){
			LOGGER.log(Level.WARNING, "Unable to set tab header/footer", ex);
		}
	}

	private static void updatePlayer(Player player){
		for (Player all : player.getWorld().getPlayers()){
			all.hidePlayer(player);
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(UhcCore.getPlugin(), () -> {
			for (Player all : player.getWorld().getPlayers()){
				all.showPlayer(player);
			}
		}, 1);
	}

	private static class NickNamePacketListener extends PacketAdapter {

		public NickNamePacketListener() {
			super(UhcCore.getPlugin(), PacketType.Play.Server.PLAYER_INFO);
		}

		@Override
		public void onPacketSending(PacketEvent event) {
			if (event.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.ADD_PLAYER){
				return;
			}

			List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>();
			List<PlayerInfoData> playerInfoDataList = event.getPacket().getPlayerInfoDataLists().read(0);
			PlayerManager pm = GameManager.getGameManager().getPlayerManager();

			for (PlayerInfoData playerInfoData : playerInfoDataList) {
				if (
					playerInfoData == null ||
					playerInfoData.getProfile() == null ||
					Bukkit.getPlayer(playerInfoData.getProfile().getUUID()) == null
				) { // Unknown player
					newPlayerInfoDataList.add(playerInfoData);
					continue;
				}

				WrappedGameProfile profile = playerInfoData.getProfile();
				UhcPlayer uhcPlayer;

				try {
					uhcPlayer = pm.getUhcPlayer(profile.getUUID());
				} catch (UhcPlayerDoesNotExistException ignored) {
					newPlayerInfoDataList.add(playerInfoData);
					continue;
				}

				// No display-name so don't change player data.
				if (!uhcPlayer.hasNickName()){
					newPlayerInfoDataList.add(playerInfoData);
					continue;
				}

				profile = profile.withName(uhcPlayer.getName());

				PlayerInfoData newPlayerInfoData = new PlayerInfoData(profile, playerInfoData.getPing(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());
				newPlayerInfoDataList.add(newPlayerInfoData);
			}
			event.getPacket().getPlayerInfoDataLists().write(0, newPlayerInfoDataList);
		}

	}

}
