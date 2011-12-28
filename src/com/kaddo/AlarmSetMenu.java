package com.kaddo;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;  
import android.app.AlertDialog.Builder;  
import android.content.DialogInterface;  
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class AlarmSetMenu extends Activity{
	ImageButton bt_back;
	TimePicker tp;
	int setHour,setMinute,setLevel,setVolumn;
	boolean setVibrate;
    boolean[] repeatDay = new boolean[]{true,true,true,true,true,true,true,true};  
    CharSequence[] item = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	String clockTime;
	RadioGroup levelradiogroup;
	SeekBar seekBar;
	CheckBox checkBox;
	TextView textView0,textView1,textView2,textView3,textView4,textView5,textView6;
    private final int MUTI_CHOICE_DIALOG = 1;  

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_menu);
        Bundle bundle = this.getIntent().getExtras();  
        setHour=bundle.getInt("clockTimeHour");
        setMinute=bundle.getInt("clockTimeMinute");
        setLevel=bundle.getInt("level");
        setVolumn=bundle.getInt("volumn");
        setVibrate=bundle.getBoolean("vibrate");
        for(int i=0;i<7;i++)
        {
        	repeatDay[i]=bundle.getBoolean("day"+i);
        }


        findViews();
        setListeners();
        setTimePicker();
        setLevelRadio();
        setVolumnSeekBar();
        setVibrateCheck();
        setRepeatDayColor();
	}
    private void setListeners() {
    	bt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				timeToString();
//				Toast.makeText(AlarmSetMenu.this, 
//					       clockTime, Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(AlarmSetMenu.this, AlarmActivity.class);
				//設定傳送參數
				Bundle bundle = new Bundle();
				bundle.putInt("clockTimeHour", setHour);
				bundle.putInt("clockTimeMinute", setMinute);
				bundle.putInt("level", setLevel);
				bundle.putInt("volumn", setVolumn);
				bundle.putBoolean("vibrate", setVibrate);
				for(int i=0;i<7;i++)
		        {
					bundle.putBoolean("day"+i, repeatDay[i]);
				}
				bundle.putString("clockTime", clockTime);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
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
	
    private void timeToString(){
    	String minute;
    	if(setMinute>=10)
    		minute=Integer.toString(setMinute);
    	else
    		minute="0"+setMinute;
    	
    	
    	if(setHour>=22)
			clockTime=(setHour-12)+":"+minute+"下午";
		else if(setHour>12)
			clockTime="0"+(setHour-12)+":"+minute+"下午";
		else if(setHour==12)
			clockTime=setHour+":"+minute+"下午";
		else if(setHour>=10)
			clockTime=setHour+":"+minute+"上午";
		else if(setHour==0) 
			clockTime="00:"+minute+"上午";
		else 
			clockTime="0"+setHour+":"+minute+"上午";
    }
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
        tp=(TimePicker)findViewById(R.id.timePicker1);
		levelradiogroup=(RadioGroup)findViewById(R.id.radioGroup1);
		seekBar = (SeekBar)findViewById(R.id.seekBar1);
		checkBox=(CheckBox)findViewById(R.id.checkBox1);
		textView0=(TextView)findViewById(R.id.textView5);
		textView1=(TextView)findViewById(R.id.textView6);
		textView2=(TextView)findViewById(R.id.textView7);
		textView3=(TextView)findViewById(R.id.textView8);
		textView4=(TextView)findViewById(R.id.textView9);
		textView5=(TextView)findViewById(R.id.textView10);
		textView6=(TextView)findViewById(R.id.textView11);
		
	}
	
	public void repeatOnClick(View view){
        showDialog(MUTI_CHOICE_DIALOG);  

	    }
	
	public void setTimePicker(){
        tp.setIs24HourView(true);//是否显示24小时制？默认false
        tp.setCurrentHour(setHour);
        tp.setCurrentMinute(setMinute);
        //设置显示时间为6:20
        tp.setOnTimeChangedListener(tpLis);
        //时间改变时触发
        	
    }
	private OnTimeChangedListener tpLis=new OnTimeChangedListener() {   
    	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {   
    		setHour=hourOfDay;
    		setMinute=minute;
        }   
    }; //时间改变后执行的内容，用两种方式显示在tv上
	public void setLevelRadio(){
		switch(setLevel){
		case 0:
			levelradiogroup.check(R.id.radio0);
			break;
		case 1:
			levelradiogroup.check(R.id.radio1);
			break;
		case 2:
			levelradiogroup.check(R.id.radio2);
			break;
		}
		
		levelradiogroup.setOnCheckedChangeListener(changeradio);

	}
	//監聽radiogroup裡面的radiobutton是否有被使用者圈選
	private RadioGroup.OnCheckedChangeListener changeradio=new RadioGroup.OnCheckedChangeListener()
	{
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
	//透過id來辨認不同的radiobutton
	switch(checkedId)
	{
	case R.id.radio0:
		setLevel=0;
		break;
	case R.id.radio1:
		setLevel=1;
		break;
	case R.id.radio2:
		setLevel=2;
		break;
		
	}
	}
	};
	public void setVolumnSeekBar(){
		seekBar.setProgress(setVolumn);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){  
			  
			   @Override  
			   public void onProgressChanged(SeekBar seekBar, int progress,  
			     boolean fromUser) {  
			    // TODO Auto-generated method stub  
			    setVolumn=progress;  
			   }  
			  
			   @Override  
			   public void onStartTrackingTouch(SeekBar seekBar) {  
			    // TODO Auto-generated method stub  
			   }  
			  
			   @Override  
			   public void onStopTrackingTouch(SeekBar seekBar) {  
			    // TODO Auto-generated method stub  
			   }  
			        });  
	}
	public void setVibrateCheck(){
		checkBox.setChecked(setVibrate);
		checkBox.setOnCheckedChangeListener(
				//監聽user 是否有選取
				new CheckBox.OnCheckedChangeListener()
				{
					
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
			
					setVibrate=isChecked;
				
				}
				}
				);
		
	}
	
	@Override  
    protected Dialog onCreateDialog(int id) {  
        Dialog dialog = null;  
        switch(id) {  
            case MUTI_CHOICE_DIALOG:  
                Builder builder = new AlertDialog.Builder(this);  
                builder.setTitle("選擇日期");
//                builder.setIcon(R.drawable.basketb);  
                DialogInterface.OnMultiChoiceClickListener mutiListener =   
                    new DialogInterface.OnMultiChoiceClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialogInterface,   
                                int which, boolean isChecked) {  
                            repeatDay[which] = isChecked;  
                        }  
                    };  
                builder.setMultiChoiceItems(item, repeatDay, mutiListener);  
                DialogInterface.OnClickListener btnListener =   
                    new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface dialogInterface, int which) {   

                           setRepeatDayColor();
                              
                        }  
                    };  
                builder.setPositiveButton("確定", btnListener);  
                dialog = builder.create();  
                break;  
        }  
        return dialog;  
    }   
	public void setRepeatDayColor(){
		 textView0.setTextColor(Color.GRAY);
         textView1.setTextColor(Color.GRAY);
         textView2.setTextColor(Color.GRAY);
         textView3.setTextColor(Color.GRAY);
         textView4.setTextColor(Color.GRAY);
         textView5.setTextColor(Color.GRAY);
         textView6.setTextColor(Color.GRAY);
         for(int i=0; i<repeatDay.length; i++) {  
             if(repeatDay[i] == true) {  
                switch(i){
                case 0:
             	   textView0.setTextColor(Color.WHITE);
             	   break;
                case 1:
             	   textView1.setTextColor(Color.WHITE);
             	   break;
                case 2:
             	   textView2.setTextColor(Color.WHITE);
             	   break;
                case 3:
             	   textView3.setTextColor(Color.WHITE);
             	   break;
                case 4:
             	   textView4.setTextColor(Color.WHITE);
             	   break;
                case 5:
             	   textView5.setTextColor(Color.WHITE);
             	   break;
                case 6:
             	   textView6.setTextColor(Color.WHITE);
             	   break;
                } 
             }  
         }  
	}
}
