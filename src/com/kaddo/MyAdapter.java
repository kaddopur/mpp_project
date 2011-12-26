package com.kaddo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


	public class MyAdapter extends SimpleAdapter {    
	    public MyAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			myInflater = LayoutInflater.from(context);
			this.context=context;
		}
//
	    boolean[] launch = new boolean[]{false,false,false}; 
		private LayoutInflater myInflater;
		public Context context;
//	    CharSequence[] list = null;
//	   
////	    public MyAdapter(Context ctxt, CharSequence[] list){
////	        myInflater = LayoutInflater.from(ctxt);
////	        this.list = list;
////	    }
//	    
//	    @Override
//	    public int getCount()
//	    {
//	        return list.length;
//	    }
//
//	    @Override
//	    public Object getItem(int position)
//	    {
//	        return list[position];
//	    }
//	  
//	    @Override
//	    public long getItemId(int position)
//	    {
//	        return position;
//	    }
//	  
    @Override
    public View getView(final int position,View convertView,ViewGroup parent)
    {
        //自訂類別，表達個別listItem中的view物件集合。
        ViewTag viewTag;
//        
//        if(convertView == null){
//            //取得listItem容器 view
            convertView = myInflater.inflate(R.layout.time_list, null);
            
            //建構listItem內容view
            viewTag = new ViewTag(
                    (ImageView)convertView.findViewById(R.id.imageView1),
                    (TextView) convertView.findViewById(R.id.clockTime),
                    (TextView) convertView.findViewById(R.id.day0),
                    (TextView) convertView.findViewById(R.id.day1),
                    (TextView) convertView.findViewById(R.id.day2),
                    (TextView) convertView.findViewById(R.id.day3),
                    (TextView) convertView.findViewById(R.id.day4),
                    (TextView) convertView.findViewById(R.id.day5),
                    (TextView) convertView.findViewById(R.id.day6),
                    (CheckBox) convertView.findViewById(R.id.checkBox1));
            
            
            //設置容器內容
            convertView.setTag(viewTag);
//        }
//        else{
//            viewTag = (ViewTag) convertView.getTag();
//        }
        
      //設定內容圖案與
        viewTag.levelImg.setBackgroundResource((Integer) AlarmActivity.listItem.get(position).get("levelImg"));
//        
//        
//        //設定內容時間
        viewTag.clockTime.setText((String)AlarmActivity.listItem.get(position).get("clockTime"));
        //設定內容日期
        viewTag.day0.setTextColor(Color.GRAY);
        viewTag.day1.setTextColor(Color.GRAY);
        viewTag.day2.setTextColor(Color.GRAY);
        viewTag.day3.setTextColor(Color.GRAY);
        viewTag.day4.setTextColor(Color.GRAY);
        viewTag.day5.setTextColor(Color.GRAY);
        viewTag.day6.setTextColor(Color.GRAY);
        for(int i=0; i<7; i++) {  
            if(Boolean.valueOf((String)(AlarmActivity.listItem.get(position).get("day"+i)))==true) {  
               switch(i){
               case 0:
            	   viewTag.day0.setTextColor(Color.WHITE);
            	   break;
               case 1:
            	   viewTag.day1.setTextColor(Color.WHITE);
            	   break;
               case 2:
            	   viewTag.day2.setTextColor(Color.WHITE);
            	   break;
               case 3:
            	   viewTag.day3.setTextColor(Color.WHITE);
            	   break;
               case 4:
            	   viewTag.day4.setTextColor(Color.WHITE);
            	   break;
               case 5:
            	   viewTag.day5.setTextColor(Color.WHITE);
            	   break;
               case 6:
            	   viewTag.day6.setTextColor(Color.WHITE);
            	   break;
               } 
            }  
        }
//        //設定是否啓動
        viewTag.checkBox.setChecked(launch[position]);
        

        viewTag.checkBox.setOnCheckedChangeListener(
				//監聽user 是否有選取
				new CheckBox.OnCheckedChangeListener()
				{
					
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
				{
					launch[position]=isChecked;
//					Toast.makeText(context,(String)AlarmActivity.listItem.get(position).get("clockTime"), Toast.LENGTH_LONG).show();	
					Intent intent = new Intent(context,AlarmReceiver.class);  
	                Bundle bundle=new Bundle();
		            
//					PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);  
	                
					
					if(isChecked){
				    	Calendar calendar = Calendar.getInstance();
				    	calendar.set(Calendar.HOUR_OF_DAY,  Integer.parseInt((String)AlarmActivity.listItem.get(position).get("clockTimeHour")));
				    	calendar.set(Calendar.MINUTE,  Integer.parseInt((String)AlarmActivity.listItem.get(position).get("clockTimeMinute")));
				    	//将秒和毫秒设置为0  
			            calendar.set(Calendar.SECOND, 0);  
			            calendar.set(Calendar.MILLISECOND, 0);  
				    	
			            
			            if(calendar.getTimeInMillis()<System.currentTimeMillis())
				    		calendar.setTimeInMillis(calendar.getTimeInMillis()+(long)(24*60*60*1000));
			            
//			            Intent intent = new Intent(context,AlarmReceiver.class);  
//			            Bundle bundle=new Bundle();
			            bundle.putInt("level", Integer.parseInt((String)AlarmActivity.listItem.get(position).get("level")));
    	                bundle.putInt("volumn", Integer.parseInt((String)AlarmActivity.listItem.get(position).get("volumn")));
    	                bundle.putBoolean("vibrate", Boolean.valueOf((String)AlarmActivity.listItem.get(position).get("vibrate")));
    	                intent.putExtras(bundle);
    	                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, intent, 0);  
				        //获取闹钟管理器  
				        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);  
				        //设置闹钟  
				        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);  
//				        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);  
				        Toast.makeText(context, "鬧鐘時間為："+ (String)AlarmActivity.listItem.get(position).get("clockTime"), Toast.LENGTH_SHORT).show(); 
				    }
				    else if(isChecked==false)
				        {
//				        	Intent intent = new Intent(context,AlarmReceiver.class); 
			                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, intent, 0);  
			                //获取闹钟管理器  
			                AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);  
			                alarmManager.cancel(pendingIntent);  
			                Toast.makeText(context, "取消鬧鐘", Toast.LENGTH_SHORT).show();
				        }
				    
				    
				}
				}
				);
        
        
        
        return convertView;
    }
    
    //自訂類別，表達個別listItem中的view物件集合。
    class ViewTag{
        ImageView levelImg;
        TextView clockTime,day0,day1,day2,day3,day4,day5,day6;
        CheckBox checkBox;
    
        public ViewTag(ImageView levelImg, TextView clockTime,TextView day0,TextView day1,TextView day2,TextView day3,TextView day4,TextView day5,TextView day6, CheckBox checkBox){
            this.levelImg = levelImg;
            this.clockTime = clockTime;
            this.day0=day0;
            this.day1=day1;
            this.day2=day2;
            this.day3=day3;
            this.day4=day4;
            this.day5=day5;
            this.day6=day6;
            this.checkBox = checkBox;
        }
    }
}