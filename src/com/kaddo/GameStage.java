package com.kaddo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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
	int score[] = new int[15];
	String question[] = {"官方網站", "觀光果園", "剛彈吊單槓", "紅粉鳳凰", "球傳給萬磁王", "四小時韓式小吃", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"};
	int lockStart = 6;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_select);
        findViews();
        setListeners();
        
        for(int i=lockStart-1; i<15; i++){
        	stage[i].setImageResource(R.drawable.lock);
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
            for(int i=0; i<question[requestCode-1].length() && i<matches.get(0).length(); i++){
            	if(question[requestCode-1].charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(GameStage.this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(GameStage.this, "正確率: "+100.0*num_correct/question[requestCode-1].length()+"%", Toast.LENGTH_SHORT).show();
            
            
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
					startVoiceRecognitionActivity(index+1);
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
}

