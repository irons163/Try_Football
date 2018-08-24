package com.wyw.myfootbll;

import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MyFootbll extends Activity{
    /** Called when the activity is first created. */
	Vibrator vibrator;
	SensorManager sensorManager;
	SensorEventListener sensorEventListener;
	GameView gv = null;
	
	int currFormation = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
		if (extras != null) 
			currFormation = extras.getInt("FormationIndex");
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);	//³]©w¥þ¿Ã¹õ
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        		);    
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		vibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		gv = new GameView(this, 0, 0, dm.widthPixels, dm.heightPixels);
        setContentView(gv);  
        
        sensorEventListener = new SensorEventListener(){

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				gv.onValueChanged(event.values);
			}
        	
        };
    }
      
    @Override
	protected void onResume() {
		super.onResume();
		
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			sensorManager.registerListener(sensorEventListener, sensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
		}
	}
    
    @Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(sensorEventListener);
	}
    
	@Override
	protected void onDestroy() {
		System.exit(0);
		
		super.onDestroy();
	}
}