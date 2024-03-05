package com.gmail.val59000mc.players;

import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.exceptions.UhcTeamException;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.languages.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UhcTeam {

	private final List<UhcPlayer> members;
	private boolean readyToStart;
	private Location startingLocation;
	private final int teamNumber;
	private String teamName;
	private ChatColor color;
	private final Inventory teamInventory;
	private int teamSize;


	public UhcTeam(UhcPlayer uhcPlayer) {
		members = new ArrayList<>();
		members.add(uhcPlayer);
		readyToStart = false;
		teamNumber = GameManager.getGameManager().getTeamManager().getNewTeamNumber();
		teamName = "Team " + teamNumber;
		color = GameManager.getGameManager().getTeamManager().getRandomTeamColor();
		teamInventory = Bukkit.createInventory(null, 9*3, Lang.SCENARIO_TEAMINVENTORY_TITLE);
		teamSize = 2;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public int getTeamNumber() {
		return teamNumber;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPrefix() {
		return color + Lang.DISPLAY_TEAM_PREFIX.replace("%teamNumber%", Integer.toString(teamNumber));
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public Inventory getTeamInventory() {
		return teamInventory;
	}

	public void sendChatMessageToTeamMembers(UhcPlayer sender, String message){
		sendMessage(Lang.DISPLAY_TEAM_CHAT
			.replace("%player%", sender.getRealName())
			.replace("%message%", message));
	}

	public void sendMessage(String message){
		members.forEach(p -> p.sendMessage(message));
	}

	public boolean contains(UhcPlayer player){
		return members.contains(player);
	}

	public synchronized List<UhcPlayer> getMembers(){
		return members;
	}

	public List<UhcPlayer> getMembers(Predicate<UhcPlayer> filter){
		return members.stream().filter(filter).collect(Collectors.toList());
	}

	public int getMemberCount(){
		return members.size();
	}

	public boolean isSolo(){
		return getMemberCount() == 1;
	}

	public int getPlayingMemberCount(){
		return getMembers(UhcPlayer::isPlaying).size();
	}

	public boolean isSpectating(){
		return isSolo() && getLeader().getState() == PlayerState.DEAD;
	}

	public int getKills(){
		return members.stream()
				.mapToInt(UhcPlayer::getKills)
				.sum();
	}

	public List<UhcPlayer> getOnlinePlayingMembers(){
		return members.stream()
				.filter(UhcPlayer::isPlaying)
				.filter(UhcPlayer::isOnline)
				.collect(Collectors.toList());
	}

	public List<String> getMembersNames(){
		List<String> names = new ArrayList<>();
		for(UhcPlayer player : getMembers()){
			names.add(player.getName());
		}
		return names;
	}

	public void join(UhcPlayer player) throws UhcTeamException {
		if(player.canJoinATeam()){
			if(isFull()){
				//player.sendMessage(Lang.TEAM_MESSAGE_FULL.replace("%player%", player.getName()).replace("%leader%", getLeader().getName()).replace("%limit%", ""+ GameManager.getGameManager().getConfig().get(MainConfig.MAX_PLAYERS_PER_TEAM)));
				player.sendMessage(Lang.TEAM_MESSAGE_FULL.replace("%player%", player.getName()).replace("%leader%", getLeader().getName()).replace("%limit%", ""+ teamSize));
				//throw new UhcTeamException(Lang.TEAM_MESSAGE_FULL.replace("%player%", player.getName()).replace("%leader%", getLeader().getName()).replace("%limit%", ""+ GameManager.getGameManager().getConfig().get(MainConfig.MAX_PLAYERS_PER_TEAM)));
				throw new UhcTeamException(Lang.TEAM_MESSAGE_FULL.replace("%player%", player.getName()).replace("%leader%", getLeader().getName()).replace("%limit%", ""+ teamSize));
			}else{
				player.sendMessage(Lang.TEAM_MESSAGE_JOIN_AS_PLAYER.replace("%leader%", getLeader().getName()));
				for(UhcPlayer teamMember : getMembers()){
					teamMember.sendMessage(Lang.TEAM_MESSAGE_PLAYER_JOINS.replace("%player%",player.getName()));
				}
				getMembers().add(player);
				player.setTeam(this);
			}
		}else{
			throw new UhcTeamException(Lang.TEAM_MESSAGE_PLAYER_ALREADY_IN_TEAM.replace("%player%", player.getName()));
		}
	}

	public boolean isFull() {
		MainConfig cfg = GameManager.getGameManager().getConfig();
		return teamSize == getMembers().size();
	}

	public void leave(UhcPlayer player) throws UhcTeamException {
		if(player.canLeaveTeam()){

			boolean isLeader = player.isTeamLeader();
			getMembers().remove(player);
			player.setTeam(new UhcTeam(player));

			UhcPlayer newLeader = getMembers().get(0);

			if(isLeader){
				player.sendMessage(Lang.TEAM_MESSAGE_LEAVE_AS_LEADER.replace("%newleader%", newLeader.getName()));
				for(UhcPlayer uhcPlayer : getMembers()){
					uhcPlayer.sendMessage(Lang.TEAM_MESSAGE_LEADER_LEAVES.replace("%leader%", player.getName()).replace("%newleader%", newLeader.getName()));
				}
			}else{
				player.sendMessage(Lang.TEAM_MESSAGE_LEAVE_AS_PLAYER);
				for(UhcPlayer teamMember : getMembers()){
					teamMember.sendMessage(Lang.TEAM_MESSAGE_PLAYER_LEAVES.replace("%player%", player.getName()));
				}
			}
		}else{
			throw new UhcTeamException(Lang.TEAM_MESSAGE_CANT_LEAVE);
		}
	}

	public UhcPlayer getLeader(){
		return getMembers().get(0);
	}

	public boolean isReadyToStart(){
		return readyToStart;
	}

	public boolean isOnline(){
		return members.stream().anyMatch(UhcPlayer::isOnline);
	}

	public void changeReadyState(){
		readyToStart = !readyToStart;

		String message = readyToStart ? Lang.TEAM_MESSAGE_NOW_READY : Lang.TEAM_MESSAGE_NOW_NOT_READY;
		sendMessage(message);
	}

	public void regenTeam(boolean doubleRegen) {
		for(UhcPlayer uhcPlayer : getMembers()){
			uhcPlayer.sendMessage(Lang.ITEMS_REGEN_HEAD_ACTION);
			try{
				Player p = uhcPlayer.getPlayer();
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,100,doubleRegen?2:1));
			} catch (UhcPlayerNotOnlineException ignored) {
				// No regen for offline players
			}
		}

	}

	public void setStartingLocation(Location loc){
		this.startingLocation = loc;
	}

	public Location getStartingLocation(){
		return startingLocation;
	}

}
