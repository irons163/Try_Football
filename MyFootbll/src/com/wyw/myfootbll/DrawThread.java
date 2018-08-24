package com.wyw.myfootbll;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	GameView father;				//FieldView����ޥ�
	SurfaceHolder surfaceHolder;	//FieldView����SurfaceHolder
	boolean isGameOn = true;		//��������檺�X�Ц줸
	int sleepSpan = 20;				//���������v�ɶ�
	
	public DrawThread(GameView father, SurfaceHolder surfaceHolder){
		super.setName("##-DrawThread");			//��������]�m�W�١A��������
		this.father = father;				
		this.surfaceHolder = surfaceHolder;
		isGameOn = true;
	}
	
	public void run(){
		Canvas canvas = null;
		while(isGameOn){
			try{
				canvas = surfaceHolder.lockCanvas(null);	//���e���[��
				synchronized(surfaceHolder){
					father.doDraw(canvas);					//��ø�e��
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(canvas != null){		//�N������öǦ^�e��
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{						
				Thread.sleep(sleepSpan);		//��v�@�q�ɶ�
			}
			catch(Exception e){
				e.printStackTrace();			//����æC�L���`
			}
		}
	}
}
