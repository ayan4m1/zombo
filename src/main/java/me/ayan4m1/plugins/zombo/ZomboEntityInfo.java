package me.ayan4m1.plugins.zombo;

import java.io.Serializable;

import org.bukkit.entity.EntityType;

public class ZomboEntityInfo implements Serializable {
	private Integer xp = 0;
	private Integer level = 1;
	private boolean boss = false;
	private String  name = "";
	private EntityType type;

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
