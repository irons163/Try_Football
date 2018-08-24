package com.wyw.myfootbll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public Scores topScores;
	public Scores bottomScores;
	public Field field;
	public Goal topGoal;
	public Goal bottomGoal;
	public Ball ball;
	public Team topTeam;
	public Team bottomTeam;
	
	public boolean isScoredAGoal = false;
	public boolean isGameOver = false;
	public int winTeam = Team.TEAM_TYPE_NONE;
	public int timeCounter = 0;
	public float roll;
	
	MyFootbll father;
	BallThread ballThread;
	PlayerThread playerThread;
	DrawThread drawThread;	
	
	Drawable drawTopGoal;
	Drawable drawBottomGoal;
	Drawable drawTopPlayer;
	Drawable drawBottomPlayer;
	Drawable drawBall;
	
	Bitmap[] bmpScores;
	Bitmap bmpGoal;	
	Bitmap bmpGameOver;	
	
	public GameView(MyFootbll father, int x, int y, int width, int height) {
		super(father);
		getHolder().addCallback(this);
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.father = father;
		
		topScores = new Scores(Team.TEAM_TYPE_TOP);
		bottomScores = new Scores(Team.TEAM_TYPE_BOTTOM);
		field = new Field();
		topGoal = new Goal(Team.TEAM_TYPE_TOP);
		bottomGoal = new Goal(Team.TEAM_TYPE_BOTTOM);
		ball = new Ball(Ball.RADIUS);
		topTeam = new Team(Team.TEAM_TYPE_TOP);
		bottomTeam = new Team(Team.TEAM_TYPE_BOTTOM);

		initBitmap(father);
		initGame();
		
		ballThread = new BallThread(this);
		playerThread = new PlayerThread(this);
		drawThread = new DrawThread(this, getHolder());
		
		startGame();
	}

	public void initPlayerPos(Team team, int forwardNum, int midfieldNum, int fullbackNum){
		team.setPosNum(forwardNum, midfieldNum, fullbackNum);
		
		int i = 0;
		int gapX = 0;
		int gapY = field.getHeight() / 9;
		int centerY = field.getTop() + ((field.getBottom() - field.getTop()) / 2);
		Player player = null;
		if (team.teamType == Team.TEAM_TYPE_TOP) // 上方隊伍
		{
			gapX = field.getWidth() / (forwardNum + 1);
			for (i = 0; i < forwardNum; i++)
			{
				player = team.forwards.get(i);
				player.y = centerY + gapY / 2 + gapY * 1;
				player.x = gapX * (i + 1);
			}
			
			gapX = field.getWidth() / (midfieldNum + 1);
			for (i = 0; i < midfieldNum; i++)
			{
				player = team.midfields.get(i);
				player.y = centerY - gapY / 2;
				player.x = gapX * (i + 1);
			}
			
			gapX = field.getWidth() / (fullbackNum + 1);
			for (i = 0; i < fullbackNum; i++)
			{
				player = team.fullbacks.get(i);
				player.y = centerY - gapY / 2 - gapY * 2;
				player.x = gapX * (i + 1);
			}
			
			player = team.goalkeepers.get(0);
			player.y = centerY - gapY / 2 - gapY * 3;
			player.x = field.getWidth() / 2;
		}
		else if (team.teamType == Team.TEAM_TYPE_BOTTOM) // 下方隊伍
		{
			gapX = field.getWidth() / (forwardNum + 1);
			for (i = 0; i < forwardNum; i++)
			{
				player = team.forwards.get(i);
				player.y = centerY - gapY / 2 - gapY * 1;
				player.x = gapX * (i + 1);
			}
			
			gapX = field.getWidth() / (midfieldNum + 1);
			for (i = 0; i < midfieldNum; i++)
			{
				player = team.midfields.get(i);
				player.y = centerY  + gapY / 2;
				player.x = gapX * (i + 1);
			}
			
			gapX = field.getWidth() / (fullbackNum + 1);
			for (i = 0; i < fullbackNum; i++)
			{
				player = team.fullbacks.get(i);
				player.y = centerY  + gapY / 2 + gapY * 2;
				player.x = gapX * (i + 1);
			}
			
			player = team.goalkeepers.get(0);
			player.y = centerY + gapY / 2 + gapY * 3;
			player.x = field.getWidth() / 2;
		}
	}
	
	public void initBitmap(Context context){
		Resources r = context.getResources();									//獲得Resources物件
		
		drawTopGoal = r.getDrawable(R.drawable.top_goal);
		drawBottomGoal = r.getDrawable(R.drawable.bottom_goal);
		drawTopPlayer = r.getDrawable(R.drawable.b_player);
		drawBottomPlayer = r.getDrawable(R.drawable.g_player);
		drawBall = r.getDrawable(R.drawable.ball);
		
		bmpScores = new Bitmap[10];										 		//數位圖片初始化
		bmpScores[0] = BitmapFactory.decodeResource(r, R.drawable.digit_0);		//數位圖片0
		bmpScores[1] = BitmapFactory.decodeResource(r, R.drawable.digit_1);		//數位圖片1
		bmpScores[2] = BitmapFactory.decodeResource(r, R.drawable.digit_2);		//數位圖片2
		bmpScores[3] = BitmapFactory.decodeResource(r, R.drawable.digit_3);		//數位圖片3
		bmpScores[4] = BitmapFactory.decodeResource(r, R.drawable.digit_4);		//數位圖片4
		bmpScores[5] = BitmapFactory.decodeResource(r, R.drawable.digit_5);		//數位圖片5
		bmpScores[6] = BitmapFactory.decodeResource(r, R.drawable.digit_6);		//數位圖片6
		bmpScores[7] = BitmapFactory.decodeResource(r, R.drawable.digit_7);		//數位圖片7 
		bmpScores[8] = BitmapFactory.decodeResource(r, R.drawable.digit_8);		//數位圖片8  
		bmpScores[9] = BitmapFactory.decodeResource(r, R.drawable.digit_9);		//數位圖片9
		
		bmpGoal = BitmapFactory.decodeResource(r, R.drawable.goal);
		bmpGameOver = BitmapFactory.decodeResource(r, R.drawable.gameover);
	}
	
	public void initGame(){
		field.setLeft(10);
		field.setTop(100);
		field.setWidth(width - field.getLeft() * 2);
		field.setHeight(height - field.getTop() * 2); 
		
		topScores.setLeft((width - bmpScores[0].getWidth()) / 2);
		topScores.setTop(field.getTop() - bmpScores[0].getHeight() - 10);
		topScores.Scores = 0;
		
		bottomScores.setLeft((width - bmpScores[0].getWidth()) / 2);
		bottomScores.setTop(field.getBottom() + 10);
		bottomScores.Scores = 0;
		
		topGoal.setWidth(field.getWidth() / 4);
		topGoal.setHeight(Goal.DEPTH);	
		topGoal.setLeft(field.getLeft() + (field.getWidth() - topGoal.getWidth()) / 2);
		topGoal.setTop(field.getTop());
		
		bottomGoal.setWidth(field.getWidth() / 4);
		bottomGoal.setHeight(Goal.DEPTH);	
		bottomGoal.setLeft(field.getLeft() + (field.getWidth() - bottomGoal.getWidth()) / 2);;
		bottomGoal.setTop(field.getBottom() - bottomGoal.getHeight());
		
		initRound();
	}
	
	public void startGame(){
		ballThread.start();
		playerThread.start();
	}
	
	public void initRound(){
		ball.x = width / 2;
		ball.y = height / 2;
		ball.setDir((((int)Math.random() * 3) + 1) * 45);	//隨機指定小球的方向
		
		Formation.setCurrIndex(father.currFormation);
		int[] formations = Formation.getFormation();

		initPlayerPos(bottomTeam, formations[0], formations[1], formations[2]);
		initPlayerPos(topTeam, 3, 3, 4);
	}
	
	protected void doDraw(Canvas canvas) {
		drawBG(canvas);
		drawScores(canvas);
		drawField(canvas);
		drawBothGoal(canvas);;
		drawBall(canvas);
		drawBothPlayers(canvas);
		drawIfGoal(canvas);
		drawOver(canvas);
	}
	
	private void drawBG(Canvas canvas) {
		canvas.drawColor(Color.GRAY);
	}
	
	private void drawScores(Canvas canvas){
		canvas.drawBitmap(bmpScores[bottomScores.Scores], bottomScores.getLeft(), bottomScores.getTop(), null);
		
		canvas.drawBitmap(bmpScores[topScores.Scores], topScores.getLeft(), topScores.getTop(), null);
		
		/*
		//畫左邊比分從左往右畫
		String score = bottomScores.Scores+"";	
		int l = score.length();	//獲得比分字串長度
		for(int j=0;j<l;j++){	//根據字串內容繪製數位圖片
			canvas.drawBitmap(bmpScores[score.charAt(j)-'0'], bottomScores.getLeft()+j*32, 2, null);
		}
		//畫右邊比分，從右往左畫
		String scoreR = topScores.Scores+"";
		int l2 = scoreR.length();//獲得比分字串長度
		for(int i=l2-1;i>=0;i--){//根據字串內容繪製數位圖片
			canvas.drawBitmap(bmpScores[scoreR.charAt(i)-'0'], topScores.getLeft()-(l2-i)*32, 2, null);
		}*/
	}
	
	private void drawField(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		paint.setColor(0xFF228B22);
		canvas.drawRect(field.getLeft(), field.getTop(), field.getRight(), field.getBottom(), paint);
		
		paint.setColor(Color.WHITE);
		canvas.drawLine(field.getLeft(), 
						field.getTop() + field.getHeight() / 2, 
						field.getRight(),
						field.getTop() + field.getHeight() / 2, 
						paint);
	}
	
	private void drawBothGoal(Canvas canvas) {
		drawTopGoal.setBounds(topGoal.getLeft(), topGoal.getTop(), topGoal.getRight(), topGoal.getBottom());
		drawTopGoal.draw(canvas);
		
		drawBottomGoal.setBounds(bottomGoal.getLeft(), bottomGoal.getTop(), bottomGoal.getRight(), bottomGoal.getBottom());
		drawBottomGoal.draw(canvas);
	}
	
	private void drawBall(Canvas canvas) {
		drawBall.setBounds(ball.x - ball.r, 
							ball.y - ball.r, 
							ball.x + ball.r, 
							ball.y + ball.r);
		drawBall.draw(canvas);
	}
	
	private void drawBothPlayers(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		Player player = null;
		for (int i = 0; i < Team.MAX_PALYER_NUM; i++)
		{
			player = topTeam.players.get(i);
			
			drawTopPlayer.setBounds(player.x - player.r, 
									player.y - player.r, 
									player.x + player.r, 
									player.y + player.r);
			drawTopPlayer.draw(canvas);
		}
		
		paint.setColor(Color.BLUE);
		for (int i = 0; i < Team.MAX_PALYER_NUM; i++)
		{
			player = bottomTeam.players.get(i);
			
			drawBottomPlayer.setBounds(player.x - player.r, 
										player.y - player.r, 
										player.x + player.r, 
										player.y + player.r);
			drawBottomPlayer.draw(canvas);
		}	
	}
	
	private void drawIfGoal(Canvas canvas){
		if(isScoredAGoal){		//如果進了一球，繪製進球圖片
			timeCounter++;		//每畫一次，計時器增加
			if(timeCounter > 50){			//如果計時器增加到50
				isScoredAGoal = false;		//設定進球旗標位元為false
				initRound();				//初始化遊戲回合
				timeCounter = 0;			//清空計時器
				ballThread.isPlaying = true;//小球運動旗標位元設為true
			}
			
			canvas.drawBitmap(bmpGoal, 
								field.getLeft() + (field.getWidth() - bmpGoal.getWidth()) / 2, 
								field.getTop() + (field.getHeight() - bmpGoal.getHeight()) / 2, 
								null);
		}
	}
	
	private void drawOver(Canvas canvas){
		if(isGameOver){//一場比賽結束，根據勝負，繪製對應圖片	
			timeCounter++;		//每畫一次，計時器增加
			if(timeCounter > 50){			//如果計時器增加到50
				isGameOver = false;		//設定進球旗標位元為false
				initGame();				//初始化遊戲回合
				timeCounter = 0;			//清空計時器
				ballThread.isPlaying = true;//小球運動旗標位元設為true
			}
			
			canvas.drawBitmap(bmpGameOver, 
								field.getLeft() + (field.getWidth() - bmpGameOver.getWidth()) / 2, 
								field.getTop() + (field.getHeight() - bmpGameOver.getHeight()) / 2, 
								null);
		}
	}
	
	public void checkIfLevelUp(){
		if (Math.max(topScores.Scores, bottomScores.Scores) == Scores.WIN_POINT){	//如果兩隊中有一隊的比分達到了勝利點
			isGameOver = true;							//將遊戲結束旗標位置為true
			if(topScores.Scores == Scores.WIN_POINT){					//是上方隊伍贏得比賽
				winTeam = Team.TEAM_TYPE_TOP;
			}
			else{										//是下方隊伍贏得比賽
				winTeam = Team.TEAM_TYPE_BOTTOM;
			}
		}
		else{											//如果只是單純的進了一個球
			isScoredAGoal = true;						//將進球旗標位元設為true			
		}
		
		father.vibrator.vibrate(300);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!drawThread.isAlive()){
			drawThread.start();
		}	

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.isGameOn = false;

	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
	}
	
	// ORIENTATION SENSOR
	public void onValueChanged(float[] values) {
		roll = values[2];
	}
}
