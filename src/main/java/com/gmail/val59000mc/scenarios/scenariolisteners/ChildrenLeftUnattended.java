package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.exceptions.UhcPlayerNotOnlineException;
import com.gmail.val59000mc.players.UhcPlayer;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.ArenaWorld;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChildrenLeftUnattended extends ScenarioListener{

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){

		if(e.getEntity().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		Player player = e.getEntity();
		UhcPlayer uhcPlayer = getPlayerManager().getUhcPlayer(player);

		for (UhcPlayer uhcMember : uhcPlayer.getTeam().getOnlinePlayingMembers()){
			if (uhcMember == uhcPlayer) continue;

			try {
				giveTeamReward(uhcMember.getPlayer());
			} catch (UhcPlayerNotOnlineException ignored) {
				// Shouldn't happen
			}
		}
	}

	private void giveTeamReward(Player player){
		Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
		wolf.setTamed(true);
		wolf.setOwner(player);
		wolf.setAdult();

		ItemStack potion = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		meta.setMainEffect(PotionEffectType.SPEED);
		PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 8*60*20, 0);
		meta.addCustomEffect(potionEffect, true);

		meta.setDisplayName(ChatColor.WHITE + "Potion of Swiftness");

		potion.setItemMeta(meta);

		player.getWorld().dropItem(player.getLocation(), potion);
	}

}
