package com.wyw.myfootbll;

import java.util.ArrayList;

public class PlayerThread extends Thread {

	GameView father = null;
	Field field = null;
	Team topTeam;
	Team bottomTeam;
	
	boolean outerFlag;			//���������X�Ц줸
	boolean flag;				//�O�_�ݭn���ʲy������m�X�Ц줸
	int sleepSpan = 20;			//�������v�ɶ�
	boolean myMoving;			//��true��ܪ��a�i���ʡA��false��ܪ��a���i��
	boolean aiMoving;			//��true���AI�i���ʡA��false���AI���i��
	
	public PlayerThread(GameView father){
		this.father = father;
		field = father.field;
		topTeam = father.topTeam;
		bottomTeam = father.bottomTeam;
		outerFlag = true;
		flag = true;
		myMoving = true;
		aiMoving = true;
	}
	
	public void run(){
		while(outerFlag){
			while(flag){
				//�ק缾�a�MAI�B�ʭ�����m				
				if(myMoving){					//�p�G���a���y���O�i���ʪ�
					move(bottomTeam, father.roll, false);
				}			
				if(aiMoving){					//�P�_AI�O�_�i�H����
					move(topTeam, 0, true);
				}	
				try{
					Thread.sleep(sleepSpan);		//�������v�@�q�ɶ�
				}
				catch(Exception e){
					e.printStackTrace();			//�C�L�î��򲧱`
				}			
			}
			try{
				Thread.sleep(300);			//���ݭn���ʪ��a�ɡA����������ίv�@�q�ɶ�
			}
			catch(Exception e){
				e.printStackTrace();		//����æC�L���`
			}	
		}
	}
	
	public void move(Team team, float roll, boolean isAI){
		Player player;
		ArrayList<Player> maxPlayers = null;
		maxPlayers = team.forwards;
		if (maxPlayers.size() < team.midfields.size())
			maxPlayers = team.midfields;
		if (maxPlayers.size() < team.fullbacks.size())
			maxPlayers = team.fullbacks;
		
		int vx = 0;
		int i;
		int count;
		int moveDir = 0;
		if (isAI == false)
		{
			if (roll < 0)
				moveDir = 1;
			else if (roll > 0)
				moveDir = -1;
			
			if (moveDir == 0)
				vx = 0;
			else if (moveDir > 0)
			{
				count = maxPlayers.size();
				for (i = 0 ; i < count; i ++)
				{
					player = maxPlayers.get(i);
					if (player.x >= field.getRight() - player.r)
						return;
				}
				
				vx = 1;
			}
			else if (moveDir < 0)
			{
				count = maxPlayers.size();
				for (i = 0 ; i < count; i ++)
				{
					player = maxPlayers.get(i);
					if (player.x <= field.getLeft() + player.r)
						return;
				}
				
				vx = -1;
			}
			
			for (i = 0; i < Team.MAX_PALYER_NUM; i++){
				player = team.players.get(i);
				player.moveDir = moveDir;
				player.x += (vx * Player.MOVE_SSPEED);
			}
		}
		else
		{
			player = team.players.get(0);
			moveDir = player.moveDir;

			if (moveDir >= 0)
			{
				count = maxPlayers.size();
				for (i = 0 ; i < count; i ++)
				{
					player = maxPlayers.get(i);
					if (player.x >= field.getRight() - player.r)
					{
						moveDir = -1;
						break;
					}
				}
			}
			else if (moveDir < 0)
			{
				count = maxPlayers.size();
				for (i = 0 ; i < count; i ++)
				{
					player = maxPlayers.get(i);
					if (player.x <= field.getLeft() + player.r)
					{
						moveDir = 1;
						break;
					}
				}
			}
			
			vx = (moveDir < 0 ? -1 : 1);
			for (i = 0; i < Team.MAX_PALYER_NUM; i++){
				player = team.players.get(i);
				player.moveDir = moveDir;
				player.x += (vx * Player.MOVE_SSPEED);
			}
		}
	}
}
