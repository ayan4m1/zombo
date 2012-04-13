package me.ayan4m1.plugins.zombo;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DataStore {
	private HashMap<Integer, ZomboMobInfo> mobs = new HashMap<Integer, ZomboMobInfo>();
	private HashMap<String, ZomboPlayerInfo>  players = new HashMap<String, ZomboPlayerInfo>();
	private HashMap<EntityType, ArrayList<ZomboDropInfo>> drops = new HashMap<EntityType, ArrayList<ZomboDropInfo>>(); 

	public DataStore() {
		ArrayList<ZomboDropInfo> zombieDrops = new ArrayList<ZomboDropInfo>();
		zombieDrops.add(new ZomboDropInfo(Material.BONE, 0.1F, 2));
		zombieDrops.add(new ZomboDropInfo(Material.GLOWSTONE_DUST, 0.05F, 1));
		drops.put(EntityType.ZOMBIE, zombieDrops);

		ArrayList<ZomboDropInfo> spiderDrops = new ArrayList<ZomboDropInfo>();
		spiderDrops.add(new ZomboDropInfo(Material.SPIDER_EYE, 0.02F, 5));
		spiderDrops.add(new ZomboDropInfo(Material.WEB, 0.2F, 1));
		drops.put(EntityType.SPIDER, spiderDrops);
	}

	public HashMap<Integer, ZomboMobInfo> getMobs() {
		return mobs;
	}

	public HashMap<String, ZomboPlayerInfo> getPlayers() {
		return players;
	}

	public HashMap<EntityType, ArrayList<ZomboDropInfo>> getDrops() {
		return drops;
	}
	
	/**
	 * Get an instance of the player map with a value type suitable for serialization
	 * @return The map of players
	 */
	public HashMap<String, Object> getPlayersAsObject() {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		for(String playerName : players.keySet()) {
			ret.put(playerName, players.get(playerName));
		}
		return ret;
	}

	public void setPlayers(HashMap<String, ZomboPlayerInfo> players) {
		this.players = players;
	}

	/**
	 * Find a mob by Id
	 * @param mobId
	 * @return ZomboModInfo if found, null otherwise
	 */
	public ZomboMobInfo getMobById(Integer mobId) {
		return mobs.get(mobId);
	}

	/**
	 * Find a player by Id
	 * @param playerName
	 * @return ZomboPlayerInfo if found, null otherwise
	 */
	public ZomboPlayerInfo getPlayerByName(String playerName) {
		return players.get(playerName);
	}

	/**
	 * Find drops for a mob type
	 * @param mobType
	 * @return ArrayList<ZomboDropInfo> if found, null otherwise
	 */
	public ArrayList<ZomboDropInfo> getDropsByType(EntityType mobType) {
		return drops.get(mobType);
	}

	/**
	 * Checks for the given mob Id
	 * @param mobId
	 * @return True if found, false otherwise
	 */
	public boolean containsMob(Integer mobId) {
		return mobs.containsKey(mobId);
	}

	/**
	 * Checks for the given player name
	 * @param playerName
	 * @return True if found, false otherwise
	 */
	public boolean containsPlayer(String playerName) {
		return players.containsKey(playerName);
	}

	public LivingEntity spawnMob(Location loc, ZomboMobInfo info) {
		LivingEntity newMob = loc.getWorld().spawnCreature(loc, info.getType());
		this.putMob(newMob.getEntityId(), info);
		return newMob;
	}

	public void putMob(Integer mobId, ZomboMobInfo info) {
		mobs.put(mobId, info);
	}

	public void removeMob(Integer mobId) {
		if (mobs.containsKey(mobId)) {
			mobs.remove(mobId);
		}
	}

	public void putPlayer(String playerName, ZomboPlayerInfo info) {
		players.put(playerName, info);
	}

	public void removePlayer(Integer playerId) {
		if (players.containsKey(playerId)) {
			players.remove(playerId);
		}
	}

	public void putDrop(EntityType type, ZomboDropInfo info) {
		ArrayList<ZomboDropInfo> dropList;
		if (drops.containsKey(type)) {
			dropList = drops.get(type);
		} else {
			dropList = new ArrayList<ZomboDropInfo>();
		}
		dropList.add(info);
		drops.put(type, dropList);
	}
	
	public void removeDrop(EntityType type, ZomboDropInfo info) {
		if (drops.containsKey(type)) {
			ArrayList<ZomboDropInfo> dropList = drops.get(type);
			dropList.remove(info);
			if (dropList.isEmpty()) {
				drops.remove(type);
			} else {
				drops.put(type, dropList);
			}
		}
	}
}