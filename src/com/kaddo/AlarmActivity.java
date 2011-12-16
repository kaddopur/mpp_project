package com.kaddo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AlarmActivity extends Activity {
    /** Called when the activity is first created. */
	ImageButton bt_back;
	ListView list;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);
        findViews();
        setListeners();
        
     // 绑定Layout里面的ListView
     	list = (ListView) findViewById(R.id.listView1);
     	listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> mpp = new HashMap<String, Object>();
		mpp.put("clockTime", " 9:00上午");
		mpp.put("level", R.drawable.ic_launcher);
		listItem.add(mpp);
		HashMap<String, Object> mpp2 = new HashMap<String, Object>();
		mpp2.put("clockTime", " 9:10上午");
		mpp2.put("level", R.drawable.ic_launcher);
		listItem.add(mpp2);
		HashMap<String, Object> mpp3 = new HashMap<String, Object>();
		mpp3.put("clockTime", " 9:20上午");
		mpp3.put("level", R.drawable.ic_launcher);
		listItem.add(mpp3);
		
     	// 生成适配器的Item和动态数组对应的元素
    	listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
    	R.layout.time_list,// ListItem的XML实现
    	// 动态数组与ImageItem对应的子项
    	new String[] {  "clockTime","level" },
    	// ImageItem的XML文件里面的一个ImageView,两个TextView ID
    	new int[] { R.id.clockTime,R.id.imageView1});
    	//添加并且显示
    	list.setAdapter(listItemAdapter);
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
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
	}
}