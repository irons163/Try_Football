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
				//���ʨ��y
				move();
				//�I���˴�
				checkCollision();
				//��v����
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
				Thread.sleep(500);//���y�����ʮɰ��������
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
		ball.velocity = (float)(ball.velocity * (1 + Ball.ACCELERATION));		//�I��t��
		if (ball.velocity < Ball.MIN_VELOCITY){		//�I��쨬�y���̤p�t�׫h����I��
			ball.velocity = Ball.MIN_VELOCITY;
		}
	}
	
	public void checkCollision(){
		checkForBorders();		//�ˬd�O�_�X���
		checkForTopPlayers();	//�ˬd�O�_�I��AI
		checkForBottomPlayers();	//�ˬd�O�_�I�쪱�a
		checkIfScoreAGoal();	//�ˬd�O�_�i�y��
		//checkForBonus();		//�ˬd�O�_�I��Bonus
	}
	
	private void checkForBorders(){
		boolean isHint = false;
		if (ball.x < field.getLeft() + ball.r)// ���F�����
		{
			ball.x = field.getLeft() + ball.r;
			ball.dx = -ball.dx;
			isHint = true;
		}
		else if(ball.x > field.getRight() - ball.r)// ����k���
		{
			ball.x = field.getRight() - ball.r;
			ball.dx = -ball.dx;
			isHint = true;
		}
		else if(ball.y < field.getTop() + ball.r)//�P�_�O�_����W���	
		{
			ball.y = field.getTop() + ball.r;
			ball.dy = -ball.dy;
			isHint = true;
		}
		else if(ball.y > field.getBottom() - ball.r)//�P�_�O�_����U���
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
			if((p.x - ball.x) * (p.x - ball.x) + (p.y - ball.y) * (p.y - ball.y) <= r * r) // �o�͸I��
			{		
				handleCollision(ball, p);			//�B�z�I��
				//ball.velocity = p.power;
			}
		}		
	}
	
	private void checkForBottomPlayers(){
		int r = 0;
		for (Player p:bottomTeam.players)
		{
			r = ball.r + p.r;
			if((p.x - ball.x) * (p.x - ball.x) + (p.y - ball.y) * (p.y - ball.y) <= r * r) // �o�͸I��
			{		
				handleCollision(ball, p);			//�B�z�I��
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
			//�W��y���i�y
			isPlaying = false;
			bottomScores.Scores++;
			father.checkIfLevelUp();
		}
		else if(ball.y > bottomGoal.getTop() && ball.x > bottomGoal.getLeft() && ball.x < bottomGoal.getRight()){
			//�U��y���i�y
			isPlaying = false;
			topScores.Scores++;
			father.checkIfLevelUp();
		}
	}
}
