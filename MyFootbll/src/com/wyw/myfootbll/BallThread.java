package com.wyw.myfootbll;

public class BallThread extends Thread {
	
	GameView father = null;
	Scores topScores;
	Scores bottomScores;
	Field field = null;
	Goal topGoal;
	Goal bottomGoal;
	Ball ball = null;
	Team topTeam;
	Team bottomTeam;
	boolean isStarted = false;
	boolean isPlaying = false;	
	int sleepSpan = 20;
	
	public BallThread(GameView father){
		this.father = father;
		topScores = father.topScores;
		bottomScores = father.bottomScores;
		field = father.field;
		topGoal = father.topGoal;
		bottomGoal = father.bottomGoal;
		ball = father.ball;
		topTeam = father.topTeam;
		bottomTeam = father.bottomTeam;
		isStarted = true;
		isPlaying = true;
	}
	
	public void run(){
		
		while(isStarted){
			while(isPlaying){
				//移動足球
				move();
				//碰撞檢測
				checkCollision();
				//休眠片刻
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
				Thread.sleep(500);//足球不移動時執行緒空轉
			}
			catch(Exception e){		e.printStackTrace();
			}
		}
	}
	
	public void move(){
		ball.x =  ball.x + (int)(ball.dx * ball.velocity);
		ball.y =  ball.y - (int)(ball.dy * ball.velocity);
		
		//decreamentVelocity();
	}
	
	public void decreamentVelocity(){
		ball.velocity = (float)(ball.velocity * (1 + Ball.ACCELERATION));		//衰減速度
		if (ball.velocity < Ball.MIN_VELOCITY){		//衰減到足球的最小速度則停止衰減
			ball.velocity = Ball.MIN_VELOCITY;
		}
	}
	
	public void checkCollision(){
		checkForBorders();		//檢查是否出邊界
		checkForTopPlayers();	//檢查是否碰到AI
		checkForBottomPlayers();	//檢查是否碰到玩家
		checkIfScoreAGoal();	//檢查是否進球啦
		//checkForBonus();		//檢查是否碰到Bonus
	}
	
	private void checkForBorders(){
		boolean isHint = false;
		if (ball.x < field.getLeft() + ball.r)// 撞了左邊界
		{
			ball.x = field.getLeft() + ball.r;
			ball.dx = -ball.dx;
			isHint = true;
		}
		else if(ball.x > field.getRight() - ball.r)// 撞到右邊界
		{
			ball.x = field.getRight() - ball.r;
			ball.dx = -ball.dx;
			isHint = true;
		}
		else if(ball.y < field.getTop() + ball.r)//判斷是否撞到上邊界	
		{
			ball.y = field.getTop() + ball.r;
			ball.dy = -ball.dy;
			isHint = true;
		}
		else if(ball.y > field.getBottom() - ball.r)//判斷是否撞到下邊界
		{
			ball.y = field.getBottom() - ball.r;
			ball.dy = -ball.dy;
			isHint = true;
		}
		
		if (isHint){
			float rand = (float)Math.random() * Ball.CHANGE_RANGE;
			int x = (int) (ball.x - Ball.CHANGE_RANGE + rand);
			if (x >= field.getLeft() + ball.r && 
				x <= field.getRight() - ball.r)
			{
				ball.x = x;
			}
			
			int y = (int) (ball.y - Ball.CHANGE_RANGE + rand);
			if (y >= field.getTop() + ball.r && 
				y <= field.getBottom() - ball.r)
			{
				ball.y = y;
			}
			
			//ball.x = (int) (ball.x - Ball.CHANGE_RANGE + rand);
			//ball.y = (int) (ball.y - Ball.CHANGE_RANGE + rand);
		}
	}
	
	private void checkForTopPlayers(){
		int r = 0;
		for (Player p:topTeam.players)
		{
			r = ball.r + p.r;
			if((p.x - ball.x) * (p.x - ball.x) + (p.y - ball.y) * (p.y - ball.y) <= r * r) // 發生碰撞
			{		
				handleCollision(ball, p);			//處理碰撞
				//ball.velocity = p.power;
			}
		}		
	}
	
	private void checkForBottomPlayers(){
		int r = 0;
		for (Player p:bottomTeam.players)
		{
			r = ball.r + p.r;
			if((p.x - ball.x) * (p.x - ball.x) + (p.y - ball.y) * (p.y - ball.y) <= r * r) // 發生碰撞
			{		
				handleCollision(ball, p);			//處理碰撞
				//ball.velocity = p.power;
			}
		}
	}
	
	private void handleCollision(Ball ball, Player p){
		switch (p.type)
		{
		case Team.TEAM_TYPE_TOP:
			{
				if (p.moveDir > 0)
					ball.setDir(315);
				else if (p.moveDir < 0)
					ball.setDir(225);
				else
					ball.setDir(270);
			}
			break;
		case Team.TEAM_TYPE_BOTTOM:
			{
				if (p.moveDir > 0)
					ball.setDir(45);
				else if (p.moveDir < 0)
					ball.setDir(135);
				else
					ball.setDir(90);
			}
			break;
		default:
			return;
		}
		
		float rand = (float)Math.random() * Ball.CHANGE_RANGE;
		ball.x = (int) (ball.x - Ball.CHANGE_RANGE + rand);
		ball.y = (int) (ball.y - Ball.CHANGE_RANGE + rand);
	}
	
	private void checkIfScoreAGoal(){
		if(ball.y < topGoal.getBottom() && ball.x > topGoal.getLeft() && ball.x < topGoal.getRight()){
			//上方球門進球
			isPlaying = false;
			bottomScores.Scores++;
			father.checkIfLevelUp();
		}
		else if(ball.y > bottomGoal.getTop() && ball.x > bottomGoal.getLeft() && ball.x < bottomGoal.getRight()){
			//下方球門進球
			isPlaying = false;
			topScores.Scores++;
			father.checkIfLevelUp();
		}
	}
}
