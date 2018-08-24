package com.wyw.myfootbll;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	GameView father;				//FieldView物件引用
	SurfaceHolder surfaceHolder;	//FieldView物件的SurfaceHolder
	boolean isGameOn = true;		//執行緒執行的旗標位元
	int sleepSpan = 20;				//執行緒的休眠時間
	
	public DrawThread(GameView father, SurfaceHolder surfaceHolder){
		super.setName("##-DrawThread");			//為執行緒設置名稱，做偵錯用
		this.father = father;				
		this.surfaceHolder = surfaceHolder;
		isGameOn = true;
	}
	
	public void run(){
		Canvas canvas = null;
		while(isGameOn){
			try{
				canvas = surfaceHolder.lockCanvas(null);	//為畫布加鎖
				synchronized(surfaceHolder){
					father.doDraw(canvas);					//重繪畫布
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(canvas != null){		//將鎖釋放並傳回畫布
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{						
				Thread.sleep(sleepSpan);		//休眠一段時間
			}
			catch(Exception e){
				e.printStackTrace();			//捕獲並列印異常
			}
		}
	}
}
