package com.wyw.myfootbll;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);	//³]©w¥þ¿Ã¹õ
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        		);    
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.main);  
        
        Button btnIcon = (Button)findViewById(R.id.icon);
        btnIcon.setBackgroundResource(R.drawable.game);
        
        
        final Button btnFormation = (Button)findViewById(R.id.btnFormation);
        
        String[] str = Formation.getFormationString();
        btnFormation.setText(str[2] + "-" + str[1] + "-" + str[0]);
        
        btnFormation.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) 
			{
				int index = Formation.getCurrIndex(); 
				if (index >= Formation.MAX_FORMATION_NUM)
					Formation.setCurrIndex(0);
				else
					Formation.setCurrIndex(index + 1);
					
				String[] str = Formation.getFormationString();
		        btnFormation.setText(str[2] + "-" + str[1] + "-" + str[0]);
			}
		});
        
        Button btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) 
			{
				String[] str = Formation.getFormationString();
				if (str == null) return;
				
				Intent intent = new Intent(Home.this, MyFootbll.class);
				intent.putExtra("FormationIndex", Formation.getCurrIndex());
				startActivity(intent);
				
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		//System.exit(0);
		
		super.onDestroy();
	}
}
