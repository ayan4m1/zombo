package me.ayan4m1.plugins.zombo;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ZomboCraftRecipe {
    private List<ItemStack> reagents;
    private List<String> enchants;
    private Material outputType;
    private String name = "";
    private Integer xpCost = 0;

    public List<ItemStack> getReagents() {
        return reagents;
    }

    public void setReagents(List<ItemStack> reagents) {
        this.reagents = reagents;
    }

    public List<String> getEnchants() {
        return enchants;
    }

    public void setEnchants(List<String> outputEffects) {
        this.enchants = outputEffects;
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
        this.outputType = Material.AIR;
        this.enchants = new ArrayList<>();
        this.reagents = new ArrayList<>();
    }

    public ZomboCraftRecipe(Material outputType, ArrayList<ItemStack> reagents) {
        this.outputType = outputType;
        this.enchants = new ArrayList<>();
        this.reagents = reagents;
    }

    public ZomboCraftRecipe(Material outputType, ArrayList<String> enchants, ArrayList<ItemStack> reagents) {
        this.outputType = outputType;
        this.enchants = enchants;
        this.reagents = reagents;
    }

    public void addEnchant(String enchantmentName) {
        this.enchants.add(enchantmentName);
    }

    public boolean craftable(Inventory inventory) {
        for (ItemStack item : reagents) {
            if (item != null && inventory.contains(item)) {
                return false;
            }
        }
        if (!inventory.contains(outputType)) {
            return false;
        }
        return true;
    }
}
