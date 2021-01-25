package akuto2.doublingtable.utils;

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

	public static enum EnumExpBoostTypes{
		x1(1, 1),
		x2(2, 2),
		x4(3, 4),
		x8(4, 8),
		x16(5, 16),
		x32(6, 32),
		x64(7, 64),
		x128(8, 128),
		x256(9, 256),
		x512(10, 512),
		x1024(11, 1024);

		private static EnumExpBoostTypes[] types;
		private int num;
		private int times;

		static {
			types = new EnumExpBoostTypes[values().length];
			for(EnumExpBoostTypes type : values()) {
				types[type.getNum() - 1] = type;
			}
		}

		private EnumExpBoostTypes(int num, int times) {
			this.num = num;
			this.times = times;
		}

		public int getNum() {
			return num;
		}

		public int getTimes() {
			return times;
		}
	}
}
