package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.UhcCore;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.exceptions.ParseException;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.*;

import io.papermc.lib.PaperLib;

import com.gmail.val59000mc.configuration.YamlFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowerPowerListener extends ScenarioListener{

	private static final Logger LOGGER = Logger.getLogger(FlowerPowerListener.class.getCanonicalName());

	private static final UniversalMaterial[] FLOWERS = new UniversalMaterial[]{
			UniversalMaterial.POPPY,
			UniversalMaterial.BLUE_ORCHID,
			UniversalMaterial.ALLIUM,
			UniversalMaterial.AZURE_BLUET,
			UniversalMaterial.RED_TULIP,
			UniversalMaterial.ORANGE_TULIP,
			UniversalMaterial.WHITE_TULIP,
			UniversalMaterial.PINK_TULIP,
			UniversalMaterial.OXEYE_DAISY,
			UniversalMaterial.SUNFLOWER,
			UniversalMaterial.LILAC,
			UniversalMaterial.ROSE_BUSH,
			UniversalMaterial.PEONY,
			UniversalMaterial.DEAD_BUSH,
			UniversalMaterial.DANDELION
	};

	private List<JsonItemStack> flowerDrops;
	private int expPerFlower;

	@Override
	public void onEnable(){
		flowerDrops = new ArrayList<>();

		String source = PaperLib.getMinecraftVersion() < 13 ? "flowerpower-1.8.yml" : "flowerpower-1.13.yml";
		YamlFile cfg;

		try{
			cfg = FileUtils.saveResourceIfNotAvailable(UhcCore.getPlugin(), "flowerpower.yml", source);
		} catch (IOException | InvalidConfigurationException ex) {
			LOGGER.log(Level.WARNING, "Unable to load flowerpower.yml", ex);
			return;
		}

		expPerFlower = cfg.getInt("exp-per-flower", 3);

		for (String drop : cfg.getStringList("drops")){
			try {
				JsonItemStack flowerDrop = JsonItemUtils.getItemFromJson(drop);
				flowerDrops.add(flowerDrop);
			} catch (ParseException ex) {
				LOGGER.log(Level.WARNING, "Failed to parse FlowerPower item: " + drop, ex);
			}
		}
	}

	@EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e){

		if (e.getPlayer().getWorld().getName().equals(ArenaWorld.NAME_WORLD_ARENA)){
			return;
		}

		Block block = e.getBlock();

		// For tall flowers start with the bottom block.
		Block below = block.getRelative(BlockFace.DOWN);
		if (isFlower(below)){
			block = below;
		}

		if (isFlower(block)){
			Location blockLoc = block.getLocation().add(.5,.5,.5);
			block.setType(Material.AIR);
			UhcItems.spawnExtraXp(blockLoc, expPerFlower);

			int random = RandomUtils.randomInteger(0, flowerDrops.size()-1);
			ItemStack drop = flowerDrops.get(random);
			blockLoc.getWorld().dropItem(blockLoc, drop);
		}
	}

	private boolean isFlower(Block block){
		for (UniversalMaterial flower : FLOWERS){
			if (flower.equals(block)) return true;
		}

		if (PaperLib.getMinecraftVersion() >= 14){
			String material = block.getType().toString();
			return material.equals("LILY_OF_THE_VALLEY") || material.equals("CORNFLOWER");
		}
		return false;
	}

}
