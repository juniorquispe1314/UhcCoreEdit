package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import static org.bukkit.Bukkit.getServer;

public class EnchatedGoldenApple extends ScenarioListener {

	private NamespacedKey craftEGA;
	private NamespacedKey craftEGA_r;

	@Override
	public void onEnable() {
		/*
			B = Block of gold
			I = gold ingot
			A = Apple

			B I B		or		I B I
		 	I A I				B A B
			B I B				I B I
		*/

		addFirstVersion();
		addSecondVersion();

	}

	@Override
	public void onDisable() {

		if(craftEGA != null){
			getServer().removeRecipe(craftEGA);
		}

		if(craftEGA_r != null){
			getServer().removeRecipe(craftEGA_r);
		}

	}

	private void addFirstVersion(){
		ItemStack enchantedApp = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
		ShapedRecipe enchantedAppCraft = new ShapedRecipe(enchantedApp);
		enchantedAppCraft.shape("BIB","IAI","BIB");
		enchantedAppCraft.setIngredient('B', Material.GOLD_BLOCK);
		enchantedAppCraft.setIngredient('A', Material.APPLE);
		enchantedAppCraft.setIngredient('I', Material.GOLD_INGOT);

		craftEGA = enchantedAppCraft.getKey();
		getServer().addRecipe(enchantedAppCraft);
	}

	private void addSecondVersion(){

		ItemStack enchantedApp = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
		ShapedRecipe enchantedAppCraft = new ShapedRecipe(enchantedApp);
		enchantedAppCraft.shape("IBI","BAB","IBI");
		enchantedAppCraft.setIngredient('B', Material.GOLD_BLOCK);
		enchantedAppCraft.setIngredient('A', Material.APPLE);
		enchantedAppCraft.setIngredient('I', Material.GOLD_INGOT);

		craftEGA_r = enchantedAppCraft.getKey();
		getServer().addRecipe(enchantedAppCraft);
	}

}
