package com.gmail.val59000mc.maploader;

import com.gmail.val59000mc.utils.UniversalMaterial;
import com.gmail.val59000mc.utils.VersionUtils;

import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class SugarCanePopulator extends BlockPopulator{

	private final int percentage;

	public SugarCanePopulator(int percentage){
		this.percentage = percentage;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk){
		// Iterates through 1-14 to ensure block checks do not load adjacent unloaded chunks which could lead to a stack overflow
		for (int x = 1; x < 15; x++) {
			for (int z = 1; z < 15; z++) {
				Location loc = world.getHighestBlockAt(chunk.getBlock(x, 0, z).getLocation()).getLocation();
				// Increase loc offset by 1 to ensure newer versions generate sugar cane properly as there exists a bug with getHighestBlockAt in older versions where it is +1 offset
				// The while loop below will then trim any remaining air spaces to properly generate sugar cane
				loc.add(0, 1, 0);
				Block below = loc.getBlock().getRelative(BlockFace.DOWN);

				// Scan down from highest block to find the ground
				while (VersionUtils.getVersionUtils().isAir(below.getType()) || UniversalMaterial.isLeaves(below.getType())) {
					loc.setY(loc.getY()-1);
					below = loc.getBlock().getRelative(BlockFace.DOWN);
				}

				Block block = loc.getBlock();

				if (percentage <= random.nextInt(100) || (below.getType() != Material.SAND && below.getType() != UniversalMaterial.GRASS_BLOCK.getType())) continue;

				if (!hasAdjacentWater(below)) continue;

				int height = random.nextInt(3) + 2;
				Location location = block.getLocation();

				while (height > 0) {
					if (!VersionUtils.getVersionUtils().isAir(location.getBlock().getType())) break;
					world.getBlockAt(location).setType(UniversalMaterial.SUGAR_CANE_BLOCK.getType());
					location = location.add(0, 1, 0);
					height--;
				}
			}
		}
	}

	private boolean hasAdjacentWater(Block block) {
		Block[] neighbors = {
			block.getRelative(BlockFace.NORTH),
			block.getRelative(BlockFace.SOUTH),
			block.getRelative(BlockFace.EAST),
			block.getRelative(BlockFace.WEST)
		};

		for (Block neighbor : neighbors) {
			// Skip non-water blocks
			if (neighbor.getType() != UniversalMaterial.STATIONARY_WATER.getType()) continue;
			// Skip non-source blocks (e.g. flowing water)
			if (PaperLib.getMinecraftVersion() > 12) {
				Levelled data = (Levelled) neighbor.getBlockData();
				if (data.getLevel() != 0) continue;
			} else {
				if (neighbor.getData() != 0) continue;
			}
			return true; // Found an adjacent water source block!
		}
		return false;
	}

}
