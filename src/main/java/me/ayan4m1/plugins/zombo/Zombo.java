package me.ayan4m1.plugins.zombo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Zombo extends JavaPlugin implements Listener {
	private final String	  configFile   = "config.yml";
	private final String	  dataFile	   = "data.yml";
	private HashMap<Integer, ZomboEntityInfo> entities;
	private HashMap<Integer, ZomboPlayerInfo> players;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		try {
			this.entities = new HashMap<Integer, ZomboEntityInfo>();
			this.players = new HashMap<Integer, ZomboPlayerInfo>();

			File configFile = new File(getDataFolder(), this.configFile);
			File dataFile = new File(getDataFolder(), this.dataFile);

			this.getLogger().info("Loading config from " + this.configFile);
			getConfig().load(configFile);
		} catch (FileNotFoundException e) {
			this.getLogger().warning("File was not found");
		} catch (IOException e) {
			this.getLogger().warning("Error reading file - " + e.getMessage());
		} catch (InvalidConfigurationException e) {
			this.getLogger().warning(this.configFile + " is invalid - " + e.getMessage());
		}
	}

	public void onDisable() {
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.getWorld().getName().equals(this.getWorldName())) {
			return;
		}

		if (!this.players.containsKey(player.getEntityId())) {
			//Search for an outdated player Id and update it
			for(Integer oldPlayerId : this.players.keySet()) {
				ZomboPlayerInfo playerInfo = this.players.get(oldPlayerId);
				if (playerInfo.getName().equalsIgnoreCase(player.getName())) {
					this.removePlayer(oldPlayerId);
					playerInfo.setOnline(true);
					this.addPlayer(player.getEntityId(), playerInfo);
					getServer().broadcastMessage(player.getName() + " joined the fight!");
					return;
				}
			}

			//Add a new player if none was found
			ZomboPlayerInfo newInfo = new ZomboPlayerInfo();
			newInfo.setName(player.getName());
			newInfo.setOnline(true);
			this.addPlayer(player.getEntityId(), newInfo);
		}
		getServer().broadcastMessage(player.getName() + " joined the fight!");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (!player.getWorld().getName().equals(this.getWorldName())) {
			return;
		}

		if (this.players.containsKey(player.getEntityId())) {
			//Set online status to false
			ZomboPlayerInfo playerInfo = this.players.get(player.getEntityId());
			playerInfo.setOnline(false);
			this.players.remove(player.getEntityId());
			this.players.put(player.getEntityId(), playerInfo);
		}
		getServer().broadcastMessage(player.getName() + " left the fight!");
	}

	@EventHandler
	public Result onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		if (player.getWorld().getName().equals(this.getWorldName())) {
			if (!this.players.containsKey(player.getEntityId())) {
				ZomboPlayerInfo newInfo = new ZomboPlayerInfo();
				newInfo.setName(player.getName());
				newInfo.setOnline(true);
				this.addPlayer(player.getEntityId(), newInfo);
				getServer().broadcastMessage(player.getName() + " joined the fight!");
			}
		} else if (event.getFrom().getName().equals(this.getWorldName())) {
			if (this.players.containsKey(player.getEntityId())) {
				ZomboPlayerInfo playerInfo = this.players.get(player.getEntityId());
				playerInfo.setOnline(false);
				this.players.remove(player.getEntityId());
				this.players.put(player.getEntityId(), playerInfo);
				getServer().broadcastMessage(player.getName() + " left the fight!");
			}
		}

		return Result.ALLOW;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!event.getEntity().getWorld().getName().equals(this.getWorldName())) {
			return;
		}

		if (!(event.getEntity() instanceof Monster)) {
			return;
		}

		Monster monster = (Monster)event.getEntity();
		if (monster.getKiller() == null) {
			return;
		}

		Player player = monster.getKiller();
		if (!this.players.containsKey(player.getEntityId()) || !this.entities.containsKey(monster.getEntityId())) {
			return;
		}

		ZomboPlayerInfo playerInfo = this.players.get(player.getEntityId());
		ZomboEntityInfo entityInfo = this.entities.get(monster.getEntityId());

		//Update player information
		playerInfo.addKill(entityInfo.getType());
		playerInfo.addXp(entityInfo.getXp());
		this.players.remove(player.getEntityId());
		this.players.put(player.getEntityId(), playerInfo);

		//Send messages about kill
		monster.getKiller().sendMessage("[+" + entityInfo.getXp() + " XP] Killed a " + monster.getType().getName());
		getServer().broadcastMessage(player.getName() + " killed a " + monster.getType().getName());

		//Disable vanilla XP drop
		event.setDroppedExp(0);

		//Stop tracking monster
		this.removeEntity(monster.getEntityId());
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

