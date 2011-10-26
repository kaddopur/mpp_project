package com.kaddo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MPP_PROJECTActivity extends Activity {
    private Button bt1, bt2;
    private static final String TAG = "VoiceRecognition";
    private static final int VOICE_RECOGNITION_REQUEST_CODE_1 = 1234;
    private static final int VOICE_RECOGNITION_REQUEST_CODE_2 = 2345;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViews();
        
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
        	setListeners();
        } else {
        	bt1.setEnabled(false);
        	bt1.setText("Recognizer not present");
        	bt2.setEnabled(false);
        	bt2.setText("Recognizer not present");
        }
    }
    
    private void startVoiceRecognitionActivity(int index) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "繞口令啦");

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
            String q = "吃葡萄不吐葡萄皮";
            int num_correct = 0;
            for(int i=0; i<q.length() && i<matches.get(0).length(); i++){
            	if(q.charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(MPP_PROJECTActivity.this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(MPP_PROJECTActivity.this, "正確率: "+100.0*num_correct/q.length()+"%", Toast.LENGTH_SHORT).show();
            
            
        } else if (requestCode == VOICE_RECOGNITION_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String q = "四十四隻石獅子";
            int num_correct = 0;
            for(int i=0; i<q.length() && i<matches.get(0).length(); i++){
            	if(q.charAt(i) == matches.get(0).charAt(i))
            		num_correct++;
            }
            Toast.makeText(MPP_PROJECTActivity.this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(MPP_PROJECTActivity.this, "正確率: "+100.0*num_correct/q.length()+"%", Toast.LENGTH_SHORT).show();
            
        }
    	super.onActivityResult(requestCode, resultCode, data);
    }
	
	private void setListeners() {
		OnClickListener twist = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(v.getId()){
				case R.id.twist_1:
					startVoiceRecognitionActivity(1);
					break;
				case R.id.twist_2:
					startVoiceRecognitionActivity(2);
					break;
				}
			}
		};
		
		bt1.setOnClickListener(twist);
		bt2.setOnClickListener(twist);
	}
	
	private void findViews() {
		bt1 = (Button)findViewById(R.id.twist_1);
		bt2 = (Button)findViewById(R.id.twist_2);
	}
}