package com.mpp.hayakuchi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MPP_PROJECTActivity extends Activity {
    private ImageButton bt_start;
    private ImageView titleImage;
    AnimationDrawable fireAnimation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        findViews();
        setListeners();
        Music.playBGM(MPP_PROJECTActivity.this);
        /*
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
        	setListeners();
        } else {
        	bt1.setEnabled(false);
        	bt1.setText("Recognizer not present");
        	bt2.setEnabled(false);
        	bt2.setText("Recognizer not present");
        }*/
    }
    /*
    private void startVoiceRecognitionActivity(int index) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¶�f�O��");

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        switch(index){
        case 1:
        	startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE_1);
        	break;
        case 2:
        	startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE_2);
        	break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == VOICE_RECOGNITION_REQUEST_CODE_1 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String q = "�Y���夣�R�����?;
            int num_correct = 0;
            for(int i=0; i<q.length() && i<matches.get(0).length(); i++){
            	if(q.charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(MPP_PROJECTActivity.this, "�A�����O: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(MPP_PROJECTActivity.this, "���T�v: "+100.0*num_correct/q.length()+"%", Toast.LENGTH_SHORT).show();
            
            
        } else if (requestCode == VOICE_RECOGNITION_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String q = "�|�Q�|���۷�l";
            int num_correct = 0;
            for(int i=0; i<q.length() && i<matches.get(0).length(); i++){
            	if(q.charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(MPP_PROJECTActivity.this, "�A�����O: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(MPP_PROJECTActivity.this, "���T�v: "+100.0*num_correct/q.length()+"%", Toast.LENGTH_SHORT).show();
            
        }
    	super.onActivityResult(requestCode, resultCode, data);
    }
	*/
	private void setListeners() {
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MPP_PROJECTActivity.this, GroupSelect.class);
				startActivity(it);
			}
		});
		
		bt_start.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bt_start.setAlpha(200);
					Music.playPress(MPP_PROJECTActivity.this);
					break;
				case MotionEvent.ACTION_UP:
					bt_start.setAlpha(255);
					break;
				}
				return false;
			}
		});
	}
	
	private void findViews() {
		bt_start = (ImageButton)findViewById(R.id.bt_start);
		titleImage =(ImageView)findViewById(R.id.imageView1);
		titleImage.setBackgroundResource(R.anim.fireanim);
		fireAnimation =(AnimationDrawable)titleImage.getBackground(); 
		
	}
	
    @Override 
    public void onWindowFocusChanged(boolean hasFocus) { 
    	super.onWindowFocusChanged(hasFocus);
    	if (hasFocus) { 
    		fireAnimation.start();
    	} else { 
    		fireAnimation.stop();
    	} 
    } 
	

	@Override
	public void onBackPressed() {
		Music.stopBGM(MPP_PROJECTActivity.this);
		super.onBackPressed();
	}

	
}