package com.enums;

public class TestEnum {
	
	public static void main(String[] args) {
		System.out.println(MyColor.BLUE);
		System.out.println(MyColor.BLUE.getCode());
		MyColor colorRed = MyColor.RED;//object of enum
		String colorcodeRed1 = MyColor.RED.getCode();//code
		
		System.out.println(MyColor.getByCode("2"));//if code not present then null
		//MyColor c1 = new MyColor("2");//cannot use
		System.out.println(MyColor.getByCode("2222"));
		MyColor byCode = MyColor.getByCode("5555");
		System.out.println(byCode);
		System.out.println(byCode.getCode());
		System.out.println(byCode.name());
		System.out.println(byCode.getMyColorDetails());
		
		MyColor byCode1 = MyColor.getByCode("44445");//null
		//System.out.println(byCode1);//null pointer exception
		
		//MyColor valueOf = MyColor.valueOf("1234");//IllegalArgumentException
		MyColor valueOf = MyColor.valueOf("PINK");
		System.out.println(valueOf.getMyColorDetails());//null pointer exception
	}
	

}
