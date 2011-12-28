package com.kaddo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AlarmActivity extends Activity {
    /** Called when the activity is first created. */
	ImageButton bt_back;
	ListView list;
	static ArrayList<HashMap<String, Object>> listItem;
	MyAdapter listItemAdapter;
    static boolean[] launch = new boolean[]{false,false,false}; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);
        findViews();
        setListeners();
        setPrefer();
        

		
     	// 生成适配器的Item和动态数组对应的元素
    	listItemAdapter = new MyAdapter(AlarmActivity.this, listItem,// 数据源
    	R.layout.time_list,// ListItem的XML实现
    	// 动态数组与ImageItem对应的子项
    	new String[] {  "clockTime","levelImg" },
    	// ImageItem的XML文件里面的一个ImageView,两个TextView ID
    	new int[] { R.id.clockTime,R.id.imageView1});
    	//添加并且显示
    	list.setAdapter(listItemAdapter);
    	
    	// 添加点击
    			list.setOnItemClickListener(new OnItemClickListener() {

    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    						long arg3) {
    					//select = arg2;
//    			        TextView title = (TextView)arg1.findViewById(R.id.textView5);
//                        title.setTextColor(Color.DKGRAY);


    					HashMap<String, Object> map2 = (HashMap<String, Object>) list.getItemAtPosition(arg2);
//    					Toast.makeText(AlarmActivity.this, "選取了" + map2.get("clockTimeHour"), Toast.LENGTH_SHORT).show();
    					Intent intent = new Intent();
    	                intent.setClass(AlarmActivity.this, AlarmSetMenu.class); 
    	                Bundle bundle=new Bundle();
    	                bundle.putInt("clockTimeHour", Integer.parseInt((String)map2.get("clockTimeHour")));
    	                bundle.putInt("clockTimeMinute", Integer.parseInt((String)map2.get("clockTimeMinute")));
    	                bundle.putInt("level", Integer.parseInt((String)map2.get("level")));
    	                bundle.putInt("volumn", Integer.parseInt((String)map2.get("volumn")));
    	                bundle.putBoolean("vibrate", Boolean.valueOf((String)map2.get("vibrate")));
    	                bundle.putBoolean("day0", Boolean.valueOf((String)map2.get("day0")));
    	                bundle.putBoolean("day1", Boolean.valueOf((String)map2.get("day1")));
    	                bundle.putBoolean("day2", Boolean.valueOf((String)map2.get("day2")));
    	                bundle.putBoolean("day3", Boolean.valueOf((String)map2.get("day3")));
    	                bundle.putBoolean("day4", Boolean.valueOf((String)map2.get("day4")));
    	                bundle.putBoolean("day5", Boolean.valueOf((String)map2.get("day5")));
    	                bundle.putBoolean("day6", Boolean.valueOf((String)map2.get("day6")));
//    	                switch(Integer.parseInt((String)map2.get("vibrate"))){
//    	                case 0:
//    	                	bundle.putBoolean("vibrate", false);
//        	            	break;
//    	                case 1:
//    	                	bundle.putBoolean("vibrate", true);
//    	                	break;
//    	                }
    	                intent.putExtras(bundle);
    	                startActivityForResult(intent, arg2);
    			
    				}
    			});
    			
    
    
    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
    	HashMap<String, Object> map3 = (HashMap<String, Object>) list.getItemAtPosition(requestCode);
    	Bundle bundle=data.getExtras();
    	map3.put("clockTime", bundle.get("clockTime"));
		map3.put("clockTimeHour", Integer.toString(bundle.getInt("clockTimeHour")));
		map3.put("clockTimeMinute",Integer.toString(bundle.getInt("clockTimeMinute")));
		map3.put("level", Integer.toString(bundle.getInt("level")));
//		switch(bundle.getInt("level"))
//		{
//		case 0:
//			map3.put("levelImg", R.drawable.tag_1);
//			break;
//		case 1:
//			map3.put("levelImg", R.drawable.tag_2);
//			break;
//		case 2:
//			map3.put("levelImg", R.drawable.tag_3);
//			break;
//		}
//		
		map3.put("volumn", Integer.toString(bundle.getInt("volumn")));
		map3.put("vibrate", Boolean.toString(bundle.getBoolean("vibrate")));
		map3.put("day0", Boolean.toString(bundle.getBoolean("day0")));
		map3.put("day1", Boolean.toString(bundle.getBoolean("day1")));
		map3.put("day2", Boolean.toString(bundle.getBoolean("day2")));
		map3.put("day3", Boolean.toString(bundle.getBoolean("day3")));
		map3.put("day4", Boolean.toString(bundle.getBoolean("day4")));
		map3.put("day5", Boolean.toString(bundle.getBoolean("day5")));
		map3.put("day6", Boolean.toString(bundle.getBoolean("day6")));
    	listItemAdapter.notifyDataSetChanged();
    	
    	 
        launchAlarm(requestCode);
    	
    }
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
    public void launchAlarm(int position){
    	if(launch[position]){
    	HashMap<String, Object> map4 = (HashMap<String, Object>) list.getItemAtPosition(position);
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY,  Integer.parseInt((String)map4.get("clockTimeHour")));
    	calendar.set(Calendar.MINUTE,  Integer.parseInt((String)map4.get("clockTimeMinute")));
    	if(calendar.getTimeInMillis()<System.currentTimeMillis())
    		calendar.setTimeInMillis(calendar.getTimeInMillis()+(long)(24*60*60*1000));
    	
    	//将秒和毫秒设置为0  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0); 
    	Intent intent = new Intent(AlarmActivity.this,AlarmReceiver.class);  
    	Bundle bundle=new Bundle();
        bundle.putInt("level", Integer.parseInt((String)map4.get("level")));
        bundle.putInt("volumn", Integer.parseInt((String)map4.get("volumn")));
        bundle.putBoolean("vibrate", Boolean.valueOf((String)map4.get("vibrate")));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, position, intent, 0);  
        //获取闹钟管理器  
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);  
        //设置闹钟  
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);  
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);  
        Toast.makeText(AlarmActivity.this, "鬧鐘時間為："+ (String)map4.get("clockTime"), Toast.LENGTH_SHORT).show();  
    	}
    	}
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
		list = (ListView) findViewById(R.id.listView1);
	}
	private void setPrefer(){
		SharedPreferences settings = getSharedPreferences("Preference1", 0);
	     // 绑定Layout里面的ListView
     	listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mpp = new HashMap<String, Object>();
		mpp.put("clockTime", settings.getString("clockTime", "09:00上午"));
		mpp.put("clockTimeHour", settings.getString("clockTimeHour", "9"));
		mpp.put("clockTimeMinute", settings.getString("clockTimeMinute", "0"));
		mpp.put("level", settings.getString("level", "0"));
		mpp.put("volumn", settings.getString("volumn", "50"));
		mpp.put("vibrate", settings.getString("vibrate", "true"));
		mpp.put("day0", settings.getString("day0", "false"));
		mpp.put("day1", settings.getString("day1", "false"));
		mpp.put("day2", settings.getString("day2", "true"));
		mpp.put("day3", settings.getString("day3", "false"));
		mpp.put("day4", settings.getString("day4", "false"));
		mpp.put("day5", settings.getString("day5", "true"));
		mpp.put("day6", settings.getString("day6", "false"));
//		mpp.put("levelImg", settings.getInt("levelImg",R.drawable.tag_1 ));
		listItem.add(mpp);
		settings = getSharedPreferences("Preference2", 0);
		HashMap<String, Object> mpp2 = new HashMap<String, Object>();
		mpp2.put("clockTime", settings.getString("clockTime", "09:10上午"));
		mpp2.put("clockTimeHour", settings.getString("clockTimeHour", "9"));
		mpp2.put("clockTimeMinute", settings.getString("clockTimeMinute", "10"));
		mpp2.put("level", settings.getString("level", "0"));
		mpp2.put("volumn", settings.getString("volumn", "20"));
		mpp2.put("vibrate", settings.getString("vibrate", "true"));
		mpp2.put("day0", settings.getString("day0", "false"));
		mpp2.put("day1", settings.getString("day1", "true"));
		mpp2.put("day2", settings.getString("day2", "true"));
		mpp2.put("day3", settings.getString("day3", "true"));
		mpp2.put("day4", settings.getString("day4", "true"));
		mpp2.put("day5", settings.getString("day5", "true"));
		mpp2.put("day6", settings.getString("day6", "false"));
//		mpp2.put("levelImg", settings.getInt("levelImg",R.drawable.tag_1 ));
		listItem.add(mpp2);
		settings = getSharedPreferences("Preference3", 0);
		HashMap<String, Object> mpp3 = new HashMap<String, Object>();
		mpp3.put("clockTime", settings.getString("clockTime", "09:20上午"));
		mpp3.put("clockTimeHour", settings.getString("clockTimeHour", "9"));
		mpp3.put("clockTimeMinute", settings.getString("clockTimeMinute", "20"));
		mpp3.put("level", settings.getString("level", "1"));
		mpp3.put("volumn", settings.getString("volumn", "80"));
		mpp3.put("vibrate", settings.getString("vibrate", "false"));
		mpp3.put("day0", settings.getString("day0", "true"));
		mpp3.put("day1", settings.getString("day1", "true"));
		mpp3.put("day2", settings.getString("day2", "true"));
		mpp3.put("day3", settings.getString("day3", "true"));
		mpp3.put("day4", settings.getString("day4", "true"));
		mpp3.put("day5", settings.getString("day5", "true"));
		mpp3.put("day6", settings.getString("day6", "true"));
//		mpp3.put("levelImg", settings.getInt("levelImg",R.drawable.tag_2 ));
		listItem.add(mpp3);
		launch[0]=settings.getBoolean("launch1", false);
		launch[1]=settings.getBoolean("launch2", false);
		launch[2]=settings.getBoolean("launch3", false);
		
	}
	
	
	
	
	@Override
	protected void onStop(){
		super.onStop();
		SharedPreferences settings = getSharedPreferences("Preference1", 0);
		//置入name屬性的字串
		HashMap<String, Object> map5 = (HashMap<String, Object>) list.getItemAtPosition(0);
		settings.edit().putString("clockTime",(String)map5.get("clockTime") )
		.putString("clockTimeHour",(String)map5.get("clockTimeHour") )
		.putString("clockTimeMinute",(String)map5.get("clockTimeMinute") )
		.putString("level",(String)map5.get("level") )
		.putString("volumn", (String)map5.get("volumn"))
		.putString("vibrate",(String)map5.get("vibrate") )
		.putString("day0",(String)map5.get("day0") )
		.putString("day1",(String)map5.get("day1") )
		.putString("day2", (String)map5.get("day2"))
		.putString("day3",(String)map5.get("day3") )
		.putString("day4",(String)map5.get("day4") )
		.putString("day5", (String)map5.get("day5"))
		.putString("day6", (String)map5.get("day6")).commit();
		
		settings = getSharedPreferences("Preference2", 0);
		map5 = (HashMap<String, Object>) list.getItemAtPosition(1);
		settings.edit().putString("clockTime",(String)map5.get("clockTime") ).commit();
		settings.edit().putString("clockTimeHour",(String)map5.get("clockTimeHour") ).commit();
		settings.edit().putString("clockTimeMinute",(String)map5.get("clockTimeMinute") ).commit();
		settings.edit().putString("level",(String)map5.get("level") ).commit();
		settings.edit().putString("volumn", (String)map5.get("volumn")).commit();
		settings.edit().putString("vibrate",(String)map5.get("vibrate") ).commit();
		settings.edit().putString("day0",(String)map5.get("day0") ).commit();
		settings.edit().putString("day1",(String)map5.get("day1") ).commit();
		settings.edit().putString("day2", (String)map5.get("day2")).commit();
		settings.edit().putString("day3",(String)map5.get("day3") ).commit();
		settings.edit().putString("day4",(String)map5.get("day4") ).commit();
		settings.edit().putString("day5", (String)map5.get("day5")).commit();
		settings.edit().putString("day6", (String)map5.get("day6")).commit();
//		settings.edit().putInt("levelImg",Integer.parseInt((String)map5.get("levelImg"))).commit();
//		
		settings = getSharedPreferences("Preference3", 0);
		map5 = (HashMap<String, Object>) list.getItemAtPosition(2);
		settings.edit().putString("clockTime",(String)map5.get("clockTime") ).commit();
		settings.edit().putString("clockTimeHour",(String)map5.get("clockTimeHour") ).commit();
		settings.edit().putString("clockTimeMinute",(String)map5.get("clockTimeMinute") ).commit();
		settings.edit().putString("level",(String)map5.get("level") ).commit();
		settings.edit().putString("volumn", (String)map5.get("volumn")).commit();
		settings.edit().putString("vibrate",(String)map5.get("vibrate") ).commit();
		settings.edit().putString("day0",(String)map5.get("day0") ).commit();
		settings.edit().putString("day1",(String)map5.get("day1") ).commit();
		settings.edit().putString("day2", (String)map5.get("day2")).commit();
		settings.edit().putString("day3",(String)map5.get("day3") ).commit();
		settings.edit().putString("day4",(String)map5.get("day4") ).commit();
		settings.edit().putString("day5", (String)map5.get("day5")).commit();
		settings.edit().putString("day6", (String)map5.get("day6")).commit();
//		settings.edit().putInt("levelImg",Integer.parseInt((String)map5.get("levelImg"))).commit();
		settings.edit().putBoolean("launch1", launch[0]).commit();
		settings.edit().putBoolean("launch2", launch[1]).commit();
		settings.edit().putBoolean("launch3", launch[2]).commit();
	}
	
	
}