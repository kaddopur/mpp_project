package com.mpp.hayakuchi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	int Level,Volumn;
	boolean Vibrate;
	@Override
	public void onReceive(Context arg0, Intent data) {
        Bundle bundle = data.getExtras();  
        Level=bundle.getInt("level");
        Volumn=bundle.getInt("volumn");
        Vibrate=bundle.getBoolean("vibrate");
//		Toast.makeText(arg0, "é¬§é??‚é??°ä?ï¼Œé??¡ç?ç´šï?"+Level+" é¬§éˆ´?³é?ï¼?+Volumn+" ?‡å?ï¼?+Vibrate, Toast.LENGTH_LONG).show();
		Intent alaramIntent = new Intent(arg0, AlarmLaunch.class); 
        Bundle bundleRet = new Bundle(); 
        bundleRet.putInt("level", Level);
        bundleRet.putInt("volumn", Volumn);
        bundleRet.putBoolean("vibrate", Vibrate);
        alaramIntent.putExtras(bundleRet);
         
        alaramIntent.putExtras(bundleRet); 
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        arg0.startActivity(alaramIntent); 
	
	}


}