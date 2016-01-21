package com.ford.log.automation.playarea;

import java.text.NumberFormat;

public class Numbers {
	public static void main(String[] args) {
		changeFormatOfNumberInString();
	}
	
	public static void changeFormatOfNumber(){
		double num = 2134.0;
		NumberFormat numberFor = NumberFormat.getInstance();
		System.out.println(numberFor.format(num));
	}
	
	public static void changeFormatOfNumberInString(){
		String num = "23450.00";
		int floatPos = num.indexOf(".") > -1 ? num.length() - num.indexOf(".") : 0;
		System.out.println("floatPos: "+floatPos);
		//int nGroups = num.
	}
}
