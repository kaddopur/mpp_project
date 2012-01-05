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
        Bundle bundle3 = new Bundle();
        bundle3 = data.getExtras();  
        Level=bundle3.getInt("level");
        Volumn=bundle3.getInt("volumn");
        Vibrate=bundle3.getBoolean("vibrate");
//		Toast.makeText(arg0, "等級："+Level+"音量:"+Volumn+"震動:"+Vibrate, Toast.LENGTH_LONG).show();
		Intent alaramIntent = new Intent(arg0, AlarmLaunch.class); 
        Bundle bundleRet = new Bundle(); 
        bundleRet.putInt("level", Level);
        bundleRet.putInt("volumn", Volumn);
        bundleRet.putBoolean("vibrate", Vibrate);
        alaramIntent.putExtras(bundleRet); 
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        arg0.startActivity(alaramIntent); 
        
	}


}