package com.mpp.hayakuchi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Service;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AlarmLaunch extends Activity {
	int Level, Volumn;
	boolean Vibrate;
	MediaPlayer mp;
	Vibrator myVibrator;
	ImageButton bt_start;
	Random r = new java.util.Random();

	String question[] = {"四十四隻石獅子", "塔滑湯灑湯燙塔", "山前有個崔腿粗", "魯夫人在哪", "李組長眉頭一皺", 
                         "光芒萬丈", "官方網站", "觀光果園", "南港展覽館", "剛彈吊單槓", 
                         "四小時韓式小吃", "剛果國際觀光果園", "光芒萬丈的官方網站", "球給志傑", "球傳給萬磁王"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_launch);
		findViews();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		Bundle bundle = this.getIntent().getExtras();
		Level = bundle.getInt("level");
		Volumn = bundle.getInt("volumn");
		Vibrate = bundle.getBoolean("vibrate");
		bindListeners();

		mp = MediaPlayer.create(this, R.raw.ring);

		// mp.prepare();
		mp.setVolume((float) (Volumn / 100.0), (float) (Volumn / 100.0));
		mp.setLooping(true);
		mp.start();

		if (Vibrate) {
			// ?��??��??��?
			myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
			myVibrator.vibrate(new long[] { 1000, 1000 }, 0);
		}
	}

	private void bindListeners() {
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity(Math.abs(r.nextInt())%15+1);
			}
		});
	}

	private void findViews() {
		bt_start = (ImageButton) findViewById(R.id.imageButton1);
	}

	@Override
	public void onPause() {
		super.onPause();
		mp.release();
		if(Vibrate){
			myVibrator.cancel();
		}
	}

	 @Override
	 public void onBackPressed() {
	 // 这�?处�??��?�??，大家注?��?该方法�??�用�?.0?�更?��??�sdk
	 return;
	 }
//	@Override
//	public void onAttachedToWindow()
//
//	{ // TODO Auto-generated method stub
//
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//
//		super.onAttachedToWindow();
//
//	}

	private void startVoiceRecognitionActivity(int index) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

		// Display an hint to the user about what he should say.
		// intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "繞口令啦");

		// Given an hint to the recognizer about what the user is going to say
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		// Specify how many results you want to receive. The results will be
		// sorted
		// where the first result is the one with higher confidence.
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, question[index - 1]);
		Log.e("crash", "before start new activity");
		startActivityForResult(intent, index);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			int num_correct = 0;
			double accuracy;
			for (int i = 0; i < question[requestCode - 1].length() && i < matches.get(0).length(); i++) {
				if (question[requestCode - 1].charAt(i) == matches.get(0).charAt(i))
					num_correct++;
			}
			accuracy = 100.0 * num_correct / question[requestCode - 1].length();
			Toast.makeText(this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "正確率: "+accuracy+"%", Toast.LENGTH_SHORT).show();
            
			if (accuracy >= 80) {
				finish();
			}else{
				mp = MediaPlayer.create(this, R.raw.ring);
				mp.setVolume((float) (Volumn / 100.0), (float) (Volumn / 100.0));
				mp.setLooping(true);
				mp.start();
				
				if (Vibrate) {
					// ?��??��??��?
					myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(new long[] { 1000, 1000 }, 0);
				}
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
