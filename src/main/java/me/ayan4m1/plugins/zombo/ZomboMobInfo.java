package me.ayan4m1.plugins.zombo;

import org.bukkit.entity.EntityType;

public class ZomboMobInfo {
	private Integer xp = 0;
	private Integer level = 1;
	private boolean boss = false;
	private String  name = "";
	private EntityType type;

	public ZomboMobInfo(EntityType type) {
		this.type = type;
	}

	public ZomboMobInfo(EntityType type, Integer xp) {
		this.type = type;
		this.xp = xp;
	}

	public Integer getXp() {
		return xp;
	}
	public void setXp(Integer xp) {
		this.xp = xp;
	}

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public boolean isBoss() {
		return boss;
	}
	public void setBoss(boolean boss) {
		this.boss = boss;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
}
