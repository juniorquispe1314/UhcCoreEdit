package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.RandomUtils;
import com.gmail.val59000mc.utils.UniversalMaterial;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTables;

import java.util.Random;

import static org.bukkit.loot.LootTables.*;

public class LuckyOresListener extends ScenarioListener{

	@EventHandler
	public void onBreakOres(BlockBreakEvent e){

		Block brokenBlock = e.getBlock();
		Material ore = brokenBlock.getType();
		ItemStack pickAxe = e.getPlayer().getInventory().getItemInMainHand();

		if(ore.equals(Material.DIAMOND_ORE) || ore.equals(UniversalMaterial.DEEPSLATE_DIAMOND_ORE.getType())){
			removeSilkTouch(pickAxe);
			int random = RandomUtils.randomInteger(1, 100); //15%
			if(random <= 15) {
				e.setCancelled(true);
				spawnChest(brokenBlock, END_CITY_TREASURE);
			}
			return;
		}

		if(ore.equals(Material.GOLD_ORE) || ore.equals(UniversalMaterial.DEEPSLATE_GOLD_ORE.getType())){
			removeSilkTouch(pickAxe);
			int r = RandomUtils.randomInteger(1,5); //20%
			if(r == 1){
				e.setCancelled(true);
				spawnChest(brokenBlock, ABANDONED_MINESHAFT);
			}
			return;
		}

		if(ore.equals(Material.EMERALD_ORE) || ore.equals(UniversalMaterial.DEEPSLATE_EMERALD_ORE.getType())){
			removeSilkTouch(pickAxe);
			int r = RandomUtils.randomInteger(1,5);//20%
			if(r <= 1){
				e.setCancelled(true);
				spawnChest(brokenBlock, STRONGHOLD_LIBRARY);
			}
		}

	}

	private void spawnChest(Block brokenBlock, LootTables loot){
		brokenBlock.setType(Material.BARREL);
		Barrel chest = (Barrel) brokenBlock.getState();

		LootContext context = new LootContext.Builder(chest.getLocation()).build();
		loot.getLootTable().fillInventory(chest.getInventory(),new Random(),context);
	}

	private void removeSilkTouch(ItemStack pickAxe){
		if(pickAxe.containsEnchantment(Enchantment.SILK_TOUCH)){
			pickAxe.removeEnchantment(Enchantment.SILK_TOUCH);
		}
	}

}
