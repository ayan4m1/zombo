package me.ayan4m1.plugins.zombo;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DataStore {
    private HashMap<Integer, ZomboMobInfo> mobs = new HashMap<>();
    private HashMap<UUID, ZomboPlayerInfo> players = new HashMap<>();
    private HashMap<EntityType, ArrayList<ZomboDropInfo>> drops = new HashMap<>();
    private ArrayList<ZomboCraftRecipe> craftRecipes = new ArrayList<>();
    private HashMap<Integer, ArrayList<ZomboMobInfo>> waveRecipes = new HashMap<>();
    private HashMap<String, ItemStack[]> inventories = new HashMap<>();
    private HashMap<Location, String> chestLocks = new HashMap<>();

    public HashMap<Integer, ZomboMobInfo> getMobs() {
        return mobs;
    }

    public HashMap<UUID, ZomboPlayerInfo> getPlayers() {
        return players;
    }

    public HashMap<EntityType, ArrayList<ZomboDropInfo>> getDrops() {
        return drops;
    }

    public HashMap<Location, String> getChestLocks() {
        return chestLocks;
    }

    public ArrayList<ZomboCraftRecipe> getCraftRecipes() {
        return craftRecipes;
    }

    public HashMap<Integer, ArrayList<ZomboMobInfo>> getWaveRecipes() {
        return waveRecipes;
    }

    public ZomboCraftRecipe getCraftRecipeForInventory(Inventory inventory) {
        for (ZomboCraftRecipe craftRecipe : this.craftRecipes) {
            if (craftRecipe.craftable(inventory)) {
                return craftRecipe;
            }
        }
        return null;
    }

    public Integer getOnlinePlayers() {
        Integer ret = 0;
        for (UUID playerId : players.keySet()) {
            String playerName = "";
            ZomboPlayerInfo info = players.get(playerName);
            if (info.isOnline()) {
                ret++;
            }
        }
        return ret;
    }

    public ItemStack[] getTempInventoryForPlayer(String playerName) {
        return inventories.get(playerName);
    }

    public void setTempInventoryForPlayer(String playerName, ItemStack[] inventory) {
        inventories.put(playerName, inventory);
    }

    /**
     * Get an instance of the player map with a value type suitable for serialization
     *
     * @return The map of players
     */
    public HashMap<UUID, Object> getPlayersAsObject() {
        HashMap<UUID, Object> ret = new HashMap<>();
        for (UUID playerId : players.keySet()) {
            ret.put(playerId, players.get(playerId));
        }
        return ret;
    }

    public void setPlayers(HashMap<UUID, ZomboPlayerInfo> players) {
        this.players = players;
    }

    public void setDrops(HashMap<EntityType, ArrayList<ZomboDropInfo>> drops) {
        this.drops = drops;
    }

    public void setCraftRecipes(ArrayList<ZomboCraftRecipe> craftRecipes) {
        this.craftRecipes = craftRecipes;
    }

    public void setWaveRecipes(HashMap<Integer, ArrayList<ZomboMobInfo>> waveRecipes) {
        this.waveRecipes = waveRecipes;
    }

    /**
     * Find a mob by Id
     *
     * @param mobId
     * @return ZomboModInfo if found, null otherwise
     */
    public ZomboMobInfo getMobById(Integer mobId) {
        return mobs.get(mobId);
    }

    public boolean containsChestLock(Location chestLocation) {
        return chestLocks.containsKey(chestLocation);
    }

    public String getChestLock(Location chestLocation) {
        return chestLocks.get(chestLocation);
    }

    public void setChestLock(Location chestLocation, String playerName) {
        chestLocks.put(chestLocation, playerName);
    }

    public void removeChestLock(Location chestLocation) {
        if (chestLocks.containsKey(chestLocation)) {
            chestLocks.remove(chestLocation);
        }
    }

    /**
     * Find a player's game state by UUID
     *
     * @param playerId UUID of the player to look up
     * @return ZomboPlayerInfo if found, null otherwise
     */
    public ZomboPlayerInfo getPlayerByUniqueId(UUID playerId) {
        return players.get(playerId);
    }

    /**
     * Find drops for a mob type
     *
     * @param mobType
     * @return ArrayList<ZomboDropInfo> if found, null otherwise
     */
    public ArrayList<ZomboDropInfo> getDropsByType(EntityType mobType) {
        return drops.get(mobType);
    }

    /**
     * Find mobs to spawn for a given wave
     *
     * @param index The wave number
     * @return A list of ZomboMobInfo objects to spawn or null if not found
     */
    public ArrayList<ZomboMobInfo> getWaveByIndex(Integer index) {
        return waveRecipes.get(index);
    }

    /**
     * Checks for the given mob Id
     *
     * @param mobId
     * @return True if found, false otherwise
     */
    public boolean containsMob(Integer mobId) {
        return mobs.containsKey(mobId);
    }

    /**
     * Checks for the given player name
     *
     * @param playerName
     * @return True if found, false otherwise
     */
    public boolean containsPlayer(String playerName) {
        return players.containsKey(playerName);
    }

    public Entity spawnMob(Location loc, ZomboMobInfo info) {
        Entity newMob = loc.getWorld().spawnEntity(loc, info.getType());
        this.putMob(newMob.getEntityId(), info);
        return newMob;
    }

    public void putMob(Integer mobId, ZomboMobInfo info) {
        mobs.put(mobId, info);
    }

    public void removeMob(Integer mobId) {
        mobs.remove(mobId);
    }

    public void putPlayer(UUID playerId, ZomboPlayerInfo info) {
        players.put(playerId, info);
    }

    public void removePlayer(UUID playerId) {
        players.remove(playerId);
    }

    public void putDrop(EntityType type, ZomboDropInfo info) {
        ArrayList<ZomboDropInfo> dropList;
        if (drops.containsKey(type)) {
            dropList = drops.get(type);
        } else {
            dropList = new ArrayList<>();
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