package com.wyw.myfootbll;

public class Player {
	
	public static final int MOVE_SSPEED = 3;				//�t��
	public static final int RADIUS = 10;					//�b�|
	
	int x = 0;
	int y = 0;
	int r = 0;
	int power = 15;				// ��y�ɵ��y���t�פj�p
	int moveDir;				// �t�Ȫ�ܦV�� ���Ȫ�ܦV�k	
	int type = Team.TEAM_TYPE_NONE;
	
	Player(int type, int radius){
		this.type = type;
		this.r = radius;
	}
}
