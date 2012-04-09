package me.ayan4m1.plugins.zombo;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ZomboDropInfo extends ItemStack {
	private Float chance;
	private Random rand;

	public ZomboDropInfo(Material type, Float chance) {
		super(type, 1);
		this.chance = chance;
		this.rand = new Random();
	}

	public ZomboDropInfo(Material type, Float chance, Integer amount) {
		super(type, amount);
		this.chance = chance;
		this.rand = new Random();
	}

	public Float getChance() {
		return chance;
	}

	public void setChance(Float chance) {
		this.chance = chance;
	}

	public void setRandomSeed(long seed) {
		this.rand.setSeed(seed);
	}

	public boolean canDrop() {
		return (rand.nextFloat() < chance);
	}
}
