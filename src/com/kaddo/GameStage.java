package com.kaddo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GameStage extends Activity  {
	ImageButton bt_back;
	ImageButton stage[] = new ImageButton[15];
	ImageButton star[] = new ImageButton[15];
	int score[] = new int[16];
	String question[] = {"四十四隻石獅子", "塔滑湯灑湯燙塔", "山前有個崔腿粗", "魯夫人在哪", "李組長眉頭一皺", 
			             "光芒萬丈", "官方網站", "觀光果園", "南港展覽館", "剛彈吊單槓", 
			             "四小時韓式小吃", "剛果國際觀光果園", "光芒萬丈的官方網站", "球給志傑", "球傳給萬磁王"};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_select);
        findViews();
        setListeners();
        
        SharedPreferences settings = getSharedPreferences("Preference", 0);
		score[0] = settings.getInt("score0", 0);
		for (int i=1; i<15; i++) {
			score[i] = settings.getInt("score" + i, -1);
		}
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		for(int i=0; i<15; i++){
			String viewID = "stage1_"+(i+1);
			int resID = getResources().getIdentifier(viewID, "drawable", getPackageName());
			
        	switch(score[i]){
        	case -1:
        		star[i].setImageResource(R.drawable.star0);
        		stage[i].setImageResource(R.drawable.lock);
        		break;
        	case 0:
        		star[i].setImageResource(R.drawable.star0);
        		stage[i].setImageResource(resID);
        		break;
        	case 1:
        		star[i].setImageResource(R.drawable.star1);
        		break;
        	case 2:
        		star[i].setImageResource(R.drawable.star2);
        		break;
        	case 3:
        		star[i].setImageResource(R.drawable.star3);
        		break;
        	}
        }
	}
    
    private void startVoiceRecognitionActivity(int index) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "繞口令啦");
        

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, question[index-1]);
    	startActivityForResult(intent, index);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            int num_correct = 0;
            double accuracy;
            for(int i=0; i<question[requestCode-1].length() && i<matches.get(0).length(); i++){
            	if(question[requestCode-1].charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            accuracy = 100.0*num_correct/question[requestCode-1].length();
            Toast.makeText(GameStage.this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(GameStage.this, "正確率: "+accuracy+"%", Toast.LENGTH_SHORT).show();
            
            
            
            if(accuracy >= 90){
            	if(score[requestCode-1] < 3)
            		score[requestCode-1]  = 3;
            	if(score[requestCode] == -1)
            		score[requestCode]  = 0;
            }else if(accuracy >= 70){
            	if(score[requestCode-1] < 2)
            		score[requestCode-1]  = 2;
            	if(score[requestCode] == -1)
            		score[requestCode]  = 0;
            }else if(accuracy >= 50){
            	if(score[requestCode-1] < 1)
            		score[requestCode-1]  = 1;
            	if(score[requestCode] == -1)
            		score[requestCode]  = 0;
            }  
        }
    	super.onActivityResult(requestCode, resultCode, data);
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
	
    	for(int i=0; i<15; i++){
    		final int index = i;
    		
			stage[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(score[index] > -1){
						startVoiceRecognitionActivity(index+1);
					}
				}
			});
			
			stage[i].setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						((ImageButton)v).setAlpha(200);
						break;
					case MotionEvent.ACTION_UP:
						((ImageButton)v).setAlpha(255);
						break;
					}
					return false;
				}
			});
			
    	}
    }
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
		for(int i=0; i<15; i++){
			String viewID = "imageButton"+(i+1);
			int resID = getResources().getIdentifier(viewID, "id", getPackageName());
			stage[i] = (ImageButton)findViewById(resID);
			
			viewID = "star"+(i+1);
			resID = getResources().getIdentifier(viewID, "id", getPackageName());
			star[i] = (ImageButton)findViewById(resID);
		}
	}
	@Override
	protected void onPause(){
		super.onPause();
		SharedPreferences settings = getSharedPreferences("Preference", 0);
		
		for(int i=0;i<15;i++){
			settings.edit().putInt("score"+i, score[i]).commit();
		}
	}
}

