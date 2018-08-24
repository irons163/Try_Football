package com.wyw.myfootbll;

public class Formation {
	
	public static final int MAX_FORMATION_NUM = 6;
	
	private static int currIndex = 0;
	
	public static final int[] format = {
		3, 3, 4,
		2, 4, 4,
		3, 4, 3,
		2, 3, 5,
		2, 5, 3,
		4, 5, 1};
	
	public static void setCurrIndex(int index){
		if (index >= MAX_FORMATION_NUM)
			index = 0;
		
		currIndex = index;
	}
	
	public static int getCurrIndex(){
		return currIndex;
	}
	
	public static int[] getFormation(){
		int[] ai = new int[3];
		ai[0] = format[currIndex * 3 + 0];
		ai[1] = format[currIndex * 3 + 1];
		ai[2] = format[currIndex * 3 + 2];
		
		return ai;
	}
	
	public static String[] getFormationString(){
		String[] as = new String[3];
		as[0] = String.format("%d", format[currIndex * 3 + 0]);
		as[1] = String.format("%d", format[currIndex * 3 + 1]);
		as[2] = String.format("%d", format[currIndex * 3 + 2]);
		
		return as;
	}
}
