package com.mpp.hayakuchi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GroupSelect extends Activity {
	ImageButton bt_back,bt_alarm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_select);
        findViews();
        setListeners();

        Gallery g = (Gallery) findViewById(R.id.g_level);
        g.setAdapter(new ImageAdapter(this));

        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(GroupSelect.this, "" + position, Toast.LENGTH_SHORT).show();
            	Music.playPress(GroupSelect.this);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("groupID", position+1);
                intent.putExtras(bundle);
                intent.setClass(GroupSelect.this, GameStage.class);                   
                startActivity(intent);
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {

        private final int mGalleryItemBackground;
        private final Context mContext;
     

        private final Integer[] mImageIds = {
        		R.drawable.level_1,
        		R.drawable.level_2,
        		R.drawable.level_3
        };

        public ImageAdapter(Context c) {
            mContext = c;
            // See res/values/attrs.xml for the <declare-styleable> that defines
            // Gallery1.
            TypedArray a = obtainStyledAttributes(R.styleable.level_wrap);
            mGalleryItemBackground = a.getResourceId(R.styleable.level_wrap_android_galleryItemBackground, 0);
            a.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	ImageView imgView = new ImageView(mContext);
            // do stuff initializing your imgView as before
            RelativeLayout borderImg = new RelativeLayout(mContext);
            borderImg.setPadding(1,1,1,1);
            borderImg.setBackgroundColor(Color.TRANSPARENT);
            imgView.setImageResource(mImageIds[position]);
            borderImg.addView(imgView);
            return borderImg;
        }
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
					Music.playPress(GroupSelect.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_back.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	bt_alarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                intent.setClass(GroupSelect.this, AlarmActivity.class);                   
                startActivity(intent);
			}
		});
		
    	bt_alarm.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_alarm.setAlpha(200);
					Music.playPress(GroupSelect.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_alarm.setAlpha(255);
					break;
				}
				return false;
			}
		});
	}
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
		bt_alarm = (ImageButton)findViewById(R.id.bt_alarm);
	}
}