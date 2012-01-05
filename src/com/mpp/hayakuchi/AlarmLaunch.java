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

	String[][] question = { { "魯肉飯", "漢堡吃到飽", "中永和萬磁王", "寶傑我跟你說", "語音辨識帥到掉渣", "球給志傑", "潮到出水", "在釣竿港釣魚", "薛丁格的哆啦a夢", "李組長眉頭一皺發現案情並不單純", "魯夫人好嗎", "史詩大師", "時事雜誌", "四十四隻石獅子", "和尚端湯上塔", "" },
			{ "花卉博覽會", "南港展覽館", "連我爸都沒打過我", "四小時韓式小吃","城市南市屈臣氏", "寬版電單車登場", "山前有個崔腿粗", "人中出呂布馬中出赤兔", "吃葡萄不吐葡萄皮","官方網站", "觀光果園", "光芒萬丈", "剛果國際觀光果園", "剛彈吊單槓", "光芒萬丈的官方網站","" },
			{ "財團法人資訊工業策進會", "你的東西就是我的東西我的東西還是我的東西","抽煙肺會飛", "小菲也會飛", "第一化肥發灰", "化肥會揮發",  "黑化肥發灰", "吃葡萄不吐葡萄皮不吃葡萄倒吐葡萄", "和尚端湯上塔塔滑湯灑湯燙塔","施氏食獅史", "唐三藏上梁山", "銘傳商專是山莊上的商專", "脫光光到南港展覽館觀光", "光芒萬丈的南港展覽館官方網站", "黑化肥發灰灰化肥發黑", "" } };
	

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

		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, question[Level][index - 1]);
		Log.e("crash", "before start new activity");
		startActivityForResult(intent, index);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            double bestAccuracy = -1.0;
            String bestMatch = "";
            
            for(String match: matches){
            	double curAccuracy = calAccuracy(question[Level][requestCode-1], match);
            	if(curAccuracy > bestAccuracy){
            		bestMatch = match;
            		bestAccuracy = curAccuracy;
            	}
            }
			Toast.makeText(this, "你說的是: " +matches.get(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "正確率: "+bestAccuracy+"%", Toast.LENGTH_SHORT).show();
            
			if (bestAccuracy >= 80) {
				finish();
			}else{
				bt_start.setImageResource(R.drawable.retry_big);
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
}
