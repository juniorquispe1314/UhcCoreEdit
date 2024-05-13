package com.gmail.val59000mc.utils;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.customitems.UhcItems;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class ArenaWorld {

	public static List<ItemStack> ITEMS_DROPED_BY_DEATH = getItemsDropedByDeath();

	public static String NAME_WORLD_ARENA = "arena";
	public static String NAME_WORLD_LOBBY = "world";

	//Arena spanw coords, this coords will be keep in mind for random spawn (real spawn)
	public static int X_SPAWN = 0;
	public static int Y_SPAWN = 71;
	public static int Z_SPAWN = 0;

	//range of random spawn
	public static int MAX_RANGE = 60;
	public static int MIN_RANGE = -60;
	public static int TIME_TO_CLEAR_ARENA = 60 * 8; // 8 min

	public static void giveKit(Player p){

		PlayerInventory inventory = p.getInventory();

		ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack ches = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack water = new ItemStack(Material.WATER_BUCKET);
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack axe = new ItemStack(Material.IRON_AXE);
		ItemStack lava = new ItemStack(Material.LAVA_BUCKET);
		ItemStack arrow = new ItemStack(Material.ARROW, 5);
		ItemStack shield = new ItemStack(Material.SHIELD);
		ItemStack trident = new ItemStack(Material.TRIDENT);
		trident.addEnchantment(Enchantment.LOYALTY, 1);
		ItemStack cobweb = new ItemStack(Material.COBWEB, 4);
		ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 3);
		ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 64);
		ItemStack pic = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemStack crossbow = new ItemStack(Material.CROSSBOW);
		crossbow.addEnchantment(Enchantment.PIERCING, 1);
		ItemStack bow = new ItemStack(Material.BOW);

		// Armor & Offhand
		inventory.setHelmet(helmet);
		inventory.setLeggings(leg);
		inventory.setBoots(boots);
		inventory.setChestplate(ches);
		inventory.setItemInOffHand(shield);

		// Items
		inventory.setItem(0, sword);
		inventory.setItem(1, bow);
		inventory.setItem(2, gapple);
		inventory.setItem(3, cobweb);
		inventory.setItem(4, crossbow);
		inventory.setItem(5, cobblestone);
		inventory.setItem(6, water);
		inventory.setItem(7, lava);
		inventory.setItem(8, axe);

		inventory.setItem(9, shield);
		inventory.setItem(10, arrow);
		inventory.setItem(31, trident);
		inventory.setItem(33, water);
		inventory.setItem(34, lava);
		inventory.setItem(35, pic);

	}

	public static void joinArena(Player uhcPlayer) {
		World world = Bukkit.getWorld(ArenaWorld.NAME_WORLD_ARENA);

		int rangeMax = MAX_RANGE;
		int rangeMin = MIN_RANGE;

		int ex = RandomUtils.randomInteger(rangeMin,rangeMax);
		int ez = RandomUtils.randomInteger(rangeMin,rangeMax);

		uhcPlayer.setGameMode(GameMode.SURVIVAL);
		uhcPlayer.getInventory().clear();
		//teleport to arena world
		uhcPlayer.teleport(Objects.requireNonNull(world).getBlockAt(X_SPAWN, Y_SPAWN,Z_SPAWN).getLocation().add(ex, 0, ez));

	}

	public static void leaveArena(Player player){
		player.setGameMode(GameMode.ADVENTURE);
		UhcItems.giveLobbyItemsTo(player);
		player.setLevel(0);
		player.setExp(0);

	}

	public static void bringAllArenaPlayers(){

		List<Player> playersList = Objects.requireNonNull(Bukkit.getWorld(ArenaWorld.NAME_WORLD_ARENA)).getPlayers();

		for (Player p : playersList) {

			if(p.isOnline()){
				p.getInventory().clear();
				p.setHealth(20);
				p.teleport(Objects.requireNonNull(Bukkit.getWorld(ArenaWorld.NAME_WORLD_LOBBY)).getSpawnLocation());
			}

		}

		Bukkit.getScheduler().runTaskLater(UhcCore.getPlugin(), () -> leaveAllPlayersFromArena(playersList), 1);

	}

	public static void leaveAllPlayersFromArena(List<Player> list){
		for(Player p : list){
			leaveArena(p);
		}
	}

	private static List<ItemStack> getItemsDropedByDeath(){
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(Material.GOLDEN_APPLE, 2));
		list.add(new ItemStack(Material.COBWEB, 2));
		list.add(new ItemStack(Material.ARROW, 2));
		list.add(new ItemStack(Material.COBBLESTONE,5));

		return list;
	}
	public static boolean isBreakableBlock(Material m){

		//cobble, stone, obsi, cobwebs, tripwire
		return m.equals(Material.OBSIDIAN) ||
			m.equals(Material.COBWEB) ||
			m.equals(Material.STONE) ||
			m.equals(Material.COBBLESTONE) ||
			m.equals(Material.TRIPWIRE);
	}

	public static List<String> getResetCommands(){

		Bukkit.getLogger().info("Adding private comands to reset ArenaPvP");

		List<String> cmds = new ArrayList<String>();
		List<String> blocksReset = deleteBlocksClenaArena(); //7 blocks

		/*

		TOTAL: 7 blocks to clear
			   10 areas to clear
			   7 * 10 = 70

		*/

		int x1 = 65;
		int x2 = 40;

		for(String block : blocksReset){

			// 5 because it is half of the arena (the arena has 12 areas in total)
			// run once this for means 2 added commands
			for(int i = 0;i<5;i++) {
				String area1 = "execute in minecraft:" + NAME_WORLD_ARENA + " run fill " + x1 + " 82 64 " + x2 +
					" 66 0 minecraft:air replace " + block;

				String area2 = "execute in minecraft:" + NAME_WORLD_ARENA + " run fill " + x1 + " 82 -1 " + x2 +
					" 66 -64 minecraft:air replace " + block;

				cmds.add(area1);
				cmds.add(area2);

				x1 -= 26;
				x2 -= 26;
			}

			x1 = 65;
			x2 = 40;

		}

		return cmds;
	}


	private static List<String> deleteBlocksClenaArena(){
		List<String> blocks = new ArrayList<String>();

		blocks.add("minecraft:water");
		blocks.add("minecraft:lava");
		blocks.add("minecraft:cobblestone");
		blocks.add("minecraft:stone");
		blocks.add("minecraft:obsidian");
		blocks.add("minecraft:cobweb");
		blocks.add("minecraft:tripwire");

		return blocks;
	}



}
