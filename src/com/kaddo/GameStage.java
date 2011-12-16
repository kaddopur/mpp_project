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
import android.widget.Toast;

public class GameStage extends Activity  {
	ImageButton bt_back;
	ImageButton stage01,stage02,stage03,stage04,stage05,stage06,stage07,stage08,stage09,stage10,stage11,stage12,stage13,stage14,stage15;
	String q;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_select);
        findViews();
        setListeners();
      
   
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
        switch(index){
        case 1:
            // Display an hint to the user about what he should say.
        	q="吃葡萄不吐葡萄皮";
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 1);
        	break;
        case 2:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 2);
        	break;
        case 3:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 3);
        	break;
        case 4:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 4);
        	break;
        case 5:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 5);
        	break;
        case 6:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 6);
        	break;
        case 7:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 7);
        	break;
        case 8:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 8);
        	break;
        case 9:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 9);
        	break;
        case 10:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 10);
        	break;
        case 11:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 11);
        	break;
        case 12:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 12);
        	break;
        case 13:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 13);
        	break;
        case 14:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 14);
        	break;
        case 15:
        	q="四十四隻石獅子";
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, q);
        	startActivityForResult(intent, 15);
        	break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            int num_correct = 0;
            for(int i=0; i<q.length() && i<matches.get(0).length(); i++){
            	if(q.charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(GameStage.this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(GameStage.this, "正確率: "+100.0*num_correct/q.length()+"%", Toast.LENGTH_SHORT).show();
            
            
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
	
    	
    	
    	stage01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(1);
			   }});
    	stage01.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage01.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage01.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(2);
			   }});
    	stage02.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage02.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage02.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage03.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(3);
			   }});
    	stage03.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage03.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage03.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage04.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(4);
			   }});
    	stage04.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage04.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage04.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage05.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(5);
			   }});
    	stage05.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage05.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage05.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage06.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(6);
			   }});
    	stage06.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage06.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage06.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage07.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(7);
			   }});
    	stage07.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage07.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage07.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage08.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(8);
			   }});
    	stage08.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage08.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage08.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage09.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(9);
			   }});
    	stage09.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage09.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage09.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(10);
			   }});
    	stage10.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage10.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage10.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(11);
			   }});
    	stage11.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage11.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage11.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(12);
			   }});
    	stage12.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage12.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage12.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(13);
			   }});
    	stage13.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage13.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage13.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(14);
			   }});
    	stage14.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage14.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage14.setAlpha(255);
					break;
				}
				return false;
			}
		});
    	
    	stage15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(15);
			   }});
    	stage15.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					stage15.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					stage15.setAlpha(255);
					break;
				}
				return false;
			}
		});
    
    }
	
	private void findViews() {
		bt_back = (ImageButton)findViewById(R.id.bt_back);
		stage01 = (ImageButton)findViewById(R.id.imageButton1);
		stage02 = (ImageButton)findViewById(R.id.imageButton2);
		stage03 = (ImageButton)findViewById(R.id.imageButton3);
		stage04 = (ImageButton)findViewById(R.id.imageButton4);
		stage05 = (ImageButton)findViewById(R.id.imageButton5);
		stage06 = (ImageButton)findViewById(R.id.imageButton6);
		stage07 = (ImageButton)findViewById(R.id.imageButton7);
		stage08 = (ImageButton)findViewById(R.id.imageButton8);
		stage09 = (ImageButton)findViewById(R.id.imageButton9);
		stage10 = (ImageButton)findViewById(R.id.imageButton10);
		stage11 = (ImageButton)findViewById(R.id.imageButton11);
		stage12 = (ImageButton)findViewById(R.id.imageButton12);
		stage13 = (ImageButton)findViewById(R.id.imageButton13);
		stage14 = (ImageButton)findViewById(R.id.imageButton14);
		stage15 = (ImageButton)findViewById(R.id.imageButton15);
	}

	
	
	
}

