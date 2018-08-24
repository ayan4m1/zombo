package me.ayan4m1.plugins.zombo;

import org.bukkit.Material;

import java.util.Random;

public class ZomboDropInfo {
	private Float rate;
	private Material type;
	private Integer amount;

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * Creates an empty instance of ZomboDropInfo as a 0% chance to drop one unit of Air
	 * This constructor exists to provide a facility for snakeyaml to deserialize saved drops 
	 */
	public ZomboDropInfo() {
		this.type = Material.AIR;
		this.amount = 0;
		this.rate = 0F;
	}

	/**
	 * Creates an instance of ZomboDropInfo representing 1 unit of the given material with the given drop rate
	 * @param type The material type
	 * @param rate The drop rate, expressed as a percentage
	 */
	public ZomboDropInfo(Material type, Float rate) {
		this.type = type;
		this.amount = 1;
		this.rate = rate;
	}

	/**
	 * Creates an instance of ZomboDropInfo representing 1 unit of the given material with the given drop rate
	 * @param type The material type
	 * @param rate The drop rate, expressed as a percentage
	 * @param amount The number of units to drop
	 */
	public ZomboDropInfo(Material type, Float rate, Integer amount) {
		this.type = type;
		this.amount = amount;
		this.rate = rate;
	}

	/**
	 * Whether or not the drop should be dropped
	 * @param  rand An instance of the Random class
	 * @return boolean True if drop was successful, false otherwise
	 */
	public boolean canDrop(Random rand) {
		return (rand.nextFloat() < rate);
	}
}
