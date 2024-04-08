package com.gmail.val59000mc.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class ArenaWorld {

	public static ItemStack ITEM_DROPED_BY_DEATH = new ItemStack(Material.GOLDEN_APPLE, 2);
	public static String NAME_WORLD_ARENA = "arena";

	//Arena spanw coords, this coords will be keep in mind for random spawn
	public static int X_SPAWN = 0;
	public static int Y_SPAWN = 71;
	public static int Z_SPAWN = 0;

	//range of random spawn
	public static int MAX_RANGE = 50;
	public static int MIN_RANGE = -50;


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
		uhcPlayer.sendMessage("SENDEADO A LA ARENA");

	}

	public static void giveKit(Player p){

		p.sendMessage("GIVE KIT WE");
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
		inventory.setItem(1, axe);
		inventory.setItem(2, water);
		inventory.setItem(3, lava);
		inventory.setItem(30, lava);
		inventory.setItem(4, cobweb);
		inventory.setItem(5, gapple);
		inventory.setItem(6, crossbow);
		inventory.setItem(7, cobblestone);
		inventory.setItem(26, cobblestone);
		inventory.setItem(8, bow);
		inventory.setItem(9, shield);
		inventory.setItem(35, cobblestone);
		inventory.setItem(10, arrow);
		inventory.setItem(29, water);
		inventory.setItem(17, pic);
		inventory.setItem(16, trident);
	}
}
