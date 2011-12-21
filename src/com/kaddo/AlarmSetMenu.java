package com.kaddo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class AlarmSetMenu extends Activity{
	ImageButton bt_back;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_menu);
        findViews();
        setListeners();}
    private void setListeners() {
    	bt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
    	bt_back.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_back.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					bt_back.setAlpha(255);
					break;
				}
				return false;
			}
		});
	}
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
		
	}
}
