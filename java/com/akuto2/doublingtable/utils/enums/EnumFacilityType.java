package com.akuto2.doublingtable.utils.enums;

import net.minecraft.util.StringRepresentable;

public enum EnumFacilityType implements StringRepresentable {
	WOOD("wood", 2),
	STONE("stone", 4),
	IRON("iron", 8),
	GOLD("gold", 16),
	DIAMOND("diamond", 32),
	EMERALD("emerald", 64);
	
	private final String name;
	private final int times;
	
	private EnumFacilityType(String name, int times) {
		this.name = name;
		this.times = times;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
	
	public int getTimes() {
		return times;
	}

	@Override
	public String toString() {
		return getSerializedName();
	}
}
