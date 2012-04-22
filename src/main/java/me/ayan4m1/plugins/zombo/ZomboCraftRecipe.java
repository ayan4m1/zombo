package me.ayan4m1.plugins.zombo;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ZomboCraftRecipe {
	private ArrayList<ItemStack> 	reagents;
	private ArrayList<Enchantment>	outputEffects;
	private Material				outputType;
	private String					name;
	private Integer					xpCost;

	public ArrayList<ItemStack> getReagents() {
		return reagents;
	}

	public void setReagents(ArrayList<ItemStack> reagents) {
		this.reagents = reagents;
	}

	public ArrayList<Enchantment> getOutputEffects() {
		return outputEffects;
	}

	public void setOutputEffects(ArrayList<Enchantment> outputEffects) {
		this.outputEffects = outputEffects;
	}

	public Material getOutputType() {
		return outputType;
	}

	public void setOutputType(Material outputType) {
		this.outputType = outputType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getXpCost() {
		return xpCost;
	}

	public void setXpCost(Integer xpCost) {
		this.xpCost = xpCost;
	}

	public ZomboCraftRecipe() {
		this.outputEffects = new ArrayList<Enchantment>();
	}

	public ZomboCraftRecipe(Material outputType, ArrayList<ItemStack> reagents) {
		this.outputType = outputType;
		this.outputEffects = new ArrayList<Enchantment>();
		this.reagents = reagents;
	}

	public ZomboCraftRecipe(Material outputType, ArrayList<Enchantment> outputEffects, ArrayList<ItemStack> reagents) {
		this.outputType = outputType;
		this.outputEffects = outputEffects;
		this.reagents = reagents;
	}

	public void addOutputEffect(Enchantment enchantment) {
		this.outputEffects.add(enchantment);
	}

	public boolean craftable(Inventory inventory) {
		for(ItemStack item : reagents) {
			if (inventory.contains(item)) {
				return false;
			}
		}
		return true;
	}
}
