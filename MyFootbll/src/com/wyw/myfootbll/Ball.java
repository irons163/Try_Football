package com.wyw.myfootbll;

public class Ball {
	
	public static final int MAX_VELOCITY = 20;				//最大運動速率
	public static final int MIN_VELOCITY = 5;				//最小運動速率
	public static final int RADIUS = 10;					//半徑
	public static final float ACCELERATION = -0.10f;		//足球在無人撞擊時速度會逐漸衰減
	public static final float CHANGE_RANGE = 10.0f;			//變向的範圍
	
	public int x = 0;
	public int y = 0;
	public int r = 0;
	public float velocity = MIN_VELOCITY;					//足球的運動速率
	
	public float dx = 0;
	public float dy = 0;
	
	Ball(int radius){
		this.r = radius;
	}

	public void setDir(float dir){
		dx = (float) Math.cos(Math.toRadians(dir));
		dy = (float) Math.sin(Math.toRadians(dir));
	}
	
	public float getDir(){
		return (float)Math.toDegrees(Math.atan2(dy, dx));
	}
	
}
