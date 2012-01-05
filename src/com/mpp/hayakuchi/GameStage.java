package com.mpp.hayakuchi;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameStage extends Activity  {
	ImageButton bt_back;
	ImageButton stage[] = new ImageButton[15];
	ImageButton star[] = new ImageButton[15];
	ImageButton outside, menu, retry, next, dialog;
	TextView whatYouSaid, accuracyField, matchField;
	boolean isScoring = false;
	final int questionLength = 15;
	final int stageNumber = 3;
	
	int[][] score = new int[stageNumber][16];
	int groupID = 1;
	String[][] question = { { "火雞肉飯", "魯肉飯", "球傳給萬磁王", "�|�ҤH�b��", "���ժ���Y�@�K", "��~�U�V", "�x�����", "�[��G��", "�n��i���]", "��u�Q��b", "�|�p����p�Y", "��G����[��G��", "��~�U�V���x�����", "資訊系", "台大", "政大" },
			{ "車車", "科科", "球傳給萬磁王", "�|�ҤH�b��", "���ժ���Y�@�K", "��~�U�V", "�x�����", "�[��G��", "�n��i���]", "��u�Q��b", "�|�p����p�Y", "��G����[��G��", "��~�U�V���x�����", "資訊系", "台大", "政大" },
			{ "蘋果", "戴爾", "球傳給萬磁王", "�|�ҤH�b��", "���ժ���Y�@�K", "��~�U�V", "�x�����", "�[��G��", "�n��i���]", "��u�Q��b", "�|�p����p�Y", "��G����[��G��", "��~�U�V���x�����", "資訊系", "台大", "政大" } };
	RelativeLayout background;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_select);
        groupID = this.getIntent().getExtras().getInt("groupID");
        background = (RelativeLayout)findViewById(R.id.stage_bg);
		switch (groupID) {
		case 1:
			background.setBackgroundResource(R.drawable.back_stage_1);
			break;
		case 2:
			background.setBackgroundResource(R.drawable.back_stage_2);
			break;
		case 3:
			background.setBackgroundResource(R.drawable.back_stage_3);
			break;
		default:
			background.setBackgroundResource(R.drawable.back_stage_1);
		}
     
        findViews();
        setListeners();
        clearScore();
        
        SharedPreferences settings = getSharedPreferences("Preference", 0);
        
        for(int i=1; i<=stageNumber; i++){
			score[i-1][0] = settings.getInt("score"+i+"_0", 0);
			for (int j=1; j<15; j++) {
				score[i-1][j] = settings.getInt("score"+i+"_"+j, -1);
			}
        }
    }
	
	private void showScore(int index, String match, double accuracy) {
		final int target = index;
		
		accuracyField.setText("正確率："+accuracy+"%");
		
		matchField.setText(match);
		
		if(accuracy >= 90){
			dialog.setImageResource(R.drawable.dialog_star3);
			Music.playGood(GameStage.this);
		}else if(accuracy >= 70){
			dialog.setImageResource(R.drawable.dialog_star2);
			Music.playGood(GameStage.this);
		}else if(accuracy >= 50){
			dialog.setImageResource(R.drawable.dialog_star1);
			Music.playGood(GameStage.this);
		}else {
			dialog.setImageResource(R.drawable.dialog_star0);
			Music.playBad(GameStage.this);
		}
		
		retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(target);
			}
		});
		
		if(score[groupID-1][index] >= 0){
			next.setImageResource(R.drawable.right_arrow);
			next.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(target != questionLength){
						startVoiceRecognitionActivity(target+1);
					}else{
						clearScore();
					}
				}
			});
		}else{
			next.setImageResource(R.drawable.lock);
			next.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//do nothing
				}
			});
		}
		isScoring = true;
		
		whatYouSaid.setVisibility(View.VISIBLE);
		accuracyField.setVisibility(View.VISIBLE);
		matchField.setVisibility(View.VISIBLE);
		outside.setVisibility(View.VISIBLE);
		dialog.setVisibility(View.VISIBLE);
		menu.setVisibility(View.VISIBLE);
		retry.setVisibility(View.VISIBLE);
		next.setVisibility(View.VISIBLE);
	}

	private void clearScore() {
		whatYouSaid.setVisibility(View.INVISIBLE);
		accuracyField.setVisibility(View.INVISIBLE);
		matchField.setVisibility(View.INVISIBLE);
		outside.setVisibility(View.INVISIBLE);
		dialog.setVisibility(View.INVISIBLE);
		menu.setVisibility(View.INVISIBLE);
		retry.setVisibility(View.INVISIBLE);
		next.setVisibility(View.INVISIBLE);
		isScoring = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		for(int i=0; i<15; i++){
			String viewID = "stage"+ groupID + "_" +(i+1);
			int resID = getResources().getIdentifier(viewID, "drawable", getPackageName());
			
        	switch(score[groupID-1][i]){
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
    	Music.lessBGM(GameStage.this);
    	Music.playQuiz(GameStage.this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¶�f�O��");
        

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, question[groupID-1][index-1]);
    	startActivityForResult(intent, index);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            double bestAccuracy = -1.0;
            String bestMatch = "";
            
            for(String match: matches){
            	double curAccuracy = calAccuracy(question[groupID-1][requestCode-1], match);
            	if(curAccuracy > bestAccuracy){
            		bestMatch = match;
            		bestAccuracy = curAccuracy;
            	}
            }
            	
            //Toast.makeText(GameStage.this, "你說的是: " +bestMatch, Toast.LENGTH_SHORT).show();
            //Toast.makeText(GameStage.this, "正確率: "+bestAccuracy+"%", Toast.LENGTH_SHORT).show();
    
            
            if(bestAccuracy >= 90){
            	if(score[groupID-1][requestCode-1] < 3)
            		score[groupID-1][requestCode-1]  = 3;
            	if(score[groupID-1][requestCode] == -1)
            		score[groupID-1][requestCode]  = 0;
            }else if(bestAccuracy >= 70){
            	if(score[groupID-1][requestCode-1] < 2)
            		score[groupID-1][requestCode-1]  = 2;
            	if(score[groupID-1][requestCode] == -1)
            		score[groupID-1][requestCode]  = 0;
            }else if(bestAccuracy >= 50){
            	if(score[groupID-1][requestCode-1] < 1)
            		score[groupID-1][requestCode-1]  = 1;
            	if(score[groupID-1][requestCode] == -1)
            		score[groupID-1][requestCode]  = 0;
            }  
            
            showScore(requestCode, bestMatch, bestAccuracy);
            Music.moreBGM(GameStage.this);
        }
    	
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
	private double calAccuracy(String question, String answer) {
		int aHead = 0;
		int okCount = 0;
		
		for(int i=0; i<question.length(); i++){
			if(aHead >= answer.length())
				break;
			try{
				int j = aHead;
				while(answer.charAt(j) != question.charAt(i)){
					j++;
				}
				aHead = j+1;
				okCount += 1;
			}catch(StringIndexOutOfBoundsException e){
				continue;
			}
		}
		
		double result = Math.max(0.0, 100.0 * okCount/question.length() - Math.abs(question.length() - answer.length()) * 3.0);
		return Math.round(result*10)/10;
	}

	@Override
	public void onBackPressed() {
		if (!isScoring) {
			super.onBackPressed();
		} else {
			clearScore();
		}
	}

	private void setListeners() {
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearScore();
			}
		});
		
		retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
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
					Music.playPress(GameStage.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_back.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	menu.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_back.setAlpha(200);
					Music.playPress(GameStage.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_back.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	retry.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_back.setAlpha(200);
					Music.playPress(GameStage.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_back.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	next.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_back.setAlpha(200);
					Music.playPress(GameStage.this);
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
					if(score[groupID-1][index] > -1){
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
						Music.playPress(GameStage.this);
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
			String resName = "imageButton"+(i+1);
			String imageName = "stage"+ groupID + "_" +(i+1);
			int resID = getResources().getIdentifier(resName, "id", getPackageName());
			int imageID = getResources().getIdentifier(imageName, "drawable", getPackageName());
			stage[i] = (ImageButton)findViewById(resID);
			stage[i].setImageResource(imageID);
			stage[i].setBackgroundColor(Color.TRANSPARENT);
			
			resName = "star"+(i+1);
			resID = getResources().getIdentifier(resName, "id", getPackageName());
			star[i] = (ImageButton)findViewById(resID);
			star[i].setImageResource(R.drawable.star0);
			star[i].setBackgroundColor(Color.TRANSPARENT);
		}
		outside = (ImageButton)findViewById(R.id.outside);
		outside.setBackgroundColor(Color.argb(192, 0, 0, 0));
		menu = (ImageButton)findViewById(R.id.menu);
		retry = (ImageButton)findViewById(R.id.retry);
		next = (ImageButton)findViewById(R.id.next);
		dialog = (ImageButton)findViewById(R.id.dialog);
		whatYouSaid = (TextView)findViewById(R.id.whatYouSaid);
		accuracyField = (TextView)findViewById(R.id.accuracyField);
		matchField = (TextView)findViewById(R.id.matchField);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		SharedPreferences settings = getSharedPreferences("Preference", 0);
		
		for(int i=1; i<=stageNumber; i++){
			for(int j=0;j<15;j++){
				settings.edit().putInt("score"+i+"_"+j, score[i-1][j]).commit();
			}
		}
	}
	
}

