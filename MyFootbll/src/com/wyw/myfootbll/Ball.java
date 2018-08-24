package com.wyw.myfootbll;

public class Ball {
	
	public static final int MAX_VELOCITY = 20;				//�̤j�B�ʳt�v
	public static final int MIN_VELOCITY = 5;				//�̤p�B�ʳt�v
	public static final int RADIUS = 10;					//�b�|
	public static final float ACCELERATION = -0.10f;		//���y�b�L�H�����ɳt�׷|�v���I��
	public static final float CHANGE_RANGE = 10.0f;			//�ܦV���d��
	
	public int x = 0;
	public int y = 0;
	public int r = 0;
	public float velocity = MIN_VELOCITY;					//���y���B�ʳt�v
	
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
