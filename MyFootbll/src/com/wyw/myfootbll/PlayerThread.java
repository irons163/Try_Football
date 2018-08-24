package com.wyw.myfootbll;

import java.util.ArrayList;

public class PlayerThread extends Thread {

	GameView father = null;
	Field field = null;
	Team topTeam;
	Team bottomTeam;
	
	boolean outerFlag;			//執行緒執行旗標位元
	boolean flag;				//是否需要移動球員的位置旗標位元
	int sleepSpan = 20;			//執行緒休眠時間
	boolean myMoving;			//為true表示玩家可移動，為false表示玩家不可動
	boolean aiMoving;			//為true表示AI可移動，為false表示AI不可動
	
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
				//修改玩家和AI運動員的位置				
				if(myMoving){					//如果玩家的球員是可移動的
					move(bottomTeam, father.roll, false);
				}			
				if(aiMoving){					//判斷AI是否可以移動
					move(topTeam, 0, true);
				}	
				try{
					Thread.sleep(sleepSpan);		//執行緒休眠一段時間
				}
				catch(Exception e){
					e.printStackTrace();			//列印並捕獲異常
				}			
			}
			try{
				Thread.sleep(300);			//當不需要移動玩家時，執行緒空轉後睡眠一段時間
			}
			catch(Exception e){
				e.printStackTrace();		//捕獲並列印異常
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
