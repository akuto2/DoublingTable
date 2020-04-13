package Akuto2.Utils;

import net.minecraft.util.IStringSerializable;

/**
 * Enum型のプロパティをまとめておくクラス
 */
public class EnumUtils {
	public static enum EnumFacilityTypes implements IStringSerializable{
		wood(0, "wood", 2),
		stone(1, "stone", 4),
		iron(2, "iron", 8),
		gold(3, "gold", 16),
		diamond(4, "diamond", 32),
		emerald(5, "emerald", 64),
		lapis(6, "lapis", 9),
		redstone(7, "redstone", 1);

		private static EnumFacilityTypes[] types;
		private int meta;
		private String name;
		private int times;

		static {
			types = new EnumFacilityTypes[values().length];
			for(EnumFacilityTypes type : values()) {
				types[type.getMeta()] = type;
			}
		}

		private EnumFacilityTypes(int meta, String name, int times) {
			this.meta = meta;
			this.name = name;
			this.times = times;
		}

		public static EnumFacilityTypes fromMeta(int meta) {
			if(meta < 0 || meta > values().length) {
				meta = 0;
			}

			return types[meta];
		}

		public int getMeta() {
			return meta;
		}

		public int getTimes() {
			if(this == EnumFacilityTypes.redstone) {
				return Utils.redstoneAmount;
			}

			return times;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
