package me.ayan4m1.plugins.zombo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InventoryManager {
	public static final void starterKit(Player player) {
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.setArmorContents(new ItemStack[] {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE)});
		inventory.addItem(new ItemStack(Material.IRON_SWORD, 1));
		inventory.addItem(new ItemStack(Material.IRON_SWORD, 1));
		inventory.addItem(new ItemStack(Material.APPLE, 5));
		inventory.addItem(new ItemStack(Material.BREAD, 5));
		inventory.addItem(new ItemStack(Material.TORCH, 10));
	}
}
