package com.kaddo;

import java.io.IOException;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.Toast;

public class AlarmLaunch extends Activity {
	int Level,Volumn;
	boolean Vibrate;
	MediaPlayer mp;
	Vibrator myVibrator;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_launch);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
        
        Bundle bundle = this.getIntent().getExtras();  
        Level=bundle.getInt("level");
        Volumn=bundle.getInt("volumn");
        Vibrate=bundle.getBoolean("vibrate");
        Toast.makeText(this, "�x���ɶ���F�A���d���šG"+Level+" �x�a���q�G"+Volumn+" �_�ʡG"+Vibrate, Toast.LENGTH_LONG).show();
		
        mp = MediaPlayer.create(this, R.raw.ring);  
       
//        	mp.prepare();
        	mp.setVolume((float)(Volumn/100.0), (float)(Volumn/100.0));
        	mp.setLooping(true);
        	mp.start();  
		
        
      if(Vibrate){
    	//���o�_�ʪA��
    	 myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
    	 myVibrator.vibrate(new long[]{1000, 1000}, 0);
      }
	}
	@Override
	public void onPause(){
		super.onPause();
		mp.release();
			myVibrator.cancel();
}}
