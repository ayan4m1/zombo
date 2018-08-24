package me.ayan4m1.plugins.zombo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public final class InventoryManager {
	public static void clearInventory(Player player) {
		player.getInventory().clear();
	}

	public static void starterKit(Player player) {
		InventoryManager.clearInventory(player);
		PlayerInventory inventory = player.getInventory();
		inventory.setArmorContents(new ItemStack[] {new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE)});
		inventory.addItem(new ItemStack(Material.IRON_SWORD, 1));
		inventory.addItem(new ItemStack(Material.IRON_SWORD, 1));
		inventory.addItem(new ItemStack(Material.APPLE, 5));
		inventory.addItem(new ItemStack(Material.BREAD, 5));
		inventory.addItem(new ItemStack(Material.TORCH, 10));
	}

	@SuppressWarnings("unchecked")
	public static boolean consumeReagent(Player player, Material type, Integer amount) {
		PlayerInventory inventory = player.getInventory();
		if (!inventory.contains(type, amount)) {
			return false;
		}

		HashMap<Integer, ItemStack> reagents = (HashMap<Integer, ItemStack>)inventory.all(type);
		for(Integer itemPos : reagents.keySet()) {
			ItemStack item = reagents.get(itemPos);

			//Consume needed reagents and break or remove item and continue consumption
			if (item.getAmount() > amount) {
				item.setAmount(item.getAmount() - amount);
				break;
			} else {
				amount -= item.getAmount();
				inventory.remove(item);

				if (amount == 0) {
					break;
				}
			}
		}

		return true;
	}
}
