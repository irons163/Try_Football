package com.wyw.myfootbll;

public class Player {
	
	public static final int MOVE_SSPEED = 3;				//速度
	public static final int RADIUS = 10;					//半徑
	
	int x = 0;
	int y = 0;
	int r = 0;
	int power = 15;				// 踢球時給球的速度大小
	int moveDir;				// 負值表示向左 正值表示向右	
	int type = Team.TEAM_TYPE_NONE;
	
	Player(int type, int radius){
		this.type = type;
		this.r = radius;
	}
}
