package me.ayan4m1.plugins.zombo;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Zombo extends JavaPlugin implements Listener {
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
	private HashMap<Integer, ZomboEntityInfo> entities;
	private HashMap<Integer, ZomboPlayerInfo> players;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome, " + event.getPlayer().getDisplayName() + "!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
    	if (!(sender instanceof Player)) {
    		return false;
    	}
    	return false;
    }
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player)sender;
		if (!this.getWorldName().equals(player.getWorld().getName())) {
			return false;
		}

		return false;
	}

	private String getWorldName() {
		return this.getConfig().getString("world");
	}

	private LivingEntity spawnEntity(Location loc, ZomboEntityInfo info) {
		LivingEntity newMob = loc.getWorld().spawnCreature(loc, info.getType());
		return newMob;
	}

	private boolean addEntity(Integer entityId, ZomboEntityInfo info) {
		if (!this.entities.containsKey(entityId)) {
			this.entities.put(entityId, info);
			return true;
		}
		return false;
	}

	private boolean removeEntity(Integer entityId) {
		if (this.entities.containsKey(entityId)) {
			this.entities.remove(entityId);
			return true;
		}
		return false;
	}

	public boolean addPlayer(Integer playerId, ZomboPlayerInfo info) {
		if (!this.players.containsKey(playerId)) {
			this.players.put(playerId, info);
			return true;
		}
		return false;
	}

	private boolean removePlayer(Integer playerId) {
		if (this.players.containsKey(playerId)) {
			this.players.remove(playerId);
		}
		return true;
	}
}

