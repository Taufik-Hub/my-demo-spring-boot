package com.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
/**
 * @Author : Taufiq
 * MyColor byCode1 = MyColor.getByCode("1111"); //work with code      //null - if not present
 * MyColor valueOf = MyColor.valueOf("RED");   //work with enum name //IllegalArgumentException  - if not present
 * 
 * */
public enum MyColor {
	RED("1111") {
		@Override
		public String getMyColorDetails() {
			return "MyColor - RED - 1111";
		}
	},
	GREEN("2222") {
		@Override
		public String getMyColorDetails() {
			return "MyColor - GREEN - 2222";
		}
	},
	BLUE("3333") {
		@Override
		public String getMyColorDetails() {
			return "MyColor - BLUE - 3333";
		}
	},
	YELLOW("4444") {
		@Override
		public String getMyColorDetails() {
			return "MyColor - YELLOW - 4444";
		}
	},
	PINK("5555") {
		@Override
		public String getMyColorDetails() {
			return "MyColor - " + PINK + " - "+ PINK.getCode();
		}
	};

	//private or default
	MyColor(String code) {//String as here 5555 is specified as string in  PINK("5555")
		this.code = code;
	}

	private String code;

	protected abstract String getMyColorDetails(); // Compulsory need to override //we can add input parameters as well
	//protected abstract String getMyColorDetails(String a, int b);//possible
	public String getCode() {
		return code;
	}

	public static MyColor getByCode(String code) {
		return MYCOLOR_TYPE_MAP.get(code);
	}

	private static final Map<String, MyColor> MYCOLOR_TYPE_MAP = new HashMap<>();// private static final and public
	// method getByCode

	static {
		Stream.of(values()).forEach(mycolor -> MYCOLOR_TYPE_MAP.put(mycolor.getCode(), mycolor));
	}

}
