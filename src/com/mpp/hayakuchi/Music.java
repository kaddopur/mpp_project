package com.mpp.hayakuchi;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	static MediaPlayer mp;
	static MediaPlayer bgm;
	
	static void playPress(Context context){
		mp = MediaPlayer.create(context, R.raw.press);
		mp.setLooping(false);
		mp.start();
	}
	
	static void playQuiz(Context context){
		mp = MediaPlayer.create(context, R.raw.quiz);
		mp.setLooping(false);
		mp.start();
	}
	
	static void playGood(Context context){
		mp = MediaPlayer.create(context, R.raw.good);
		mp.setLooping(false);
		mp.start();
	}
	
	static void playBad(Context context){
		mp = MediaPlayer.create(context, R.raw.bad);
		mp.setLooping(false);
		mp.start();
	}
	
	static void playBGM(Context context){
		bgm = MediaPlayer.create(context, R.raw.ring);
		bgm.setLooping(true);
		bgm.setVolume(0.5f, 0.5f);
		bgm.start();
	}
	
	static void lessBGM(Context context){
		bgm.setVolume(0.05f, 0.05f);
	}
	
	static void moreBGM(Context context){
		bgm.setVolume(0.5f, 0.5f);
	}
	
	static void stopBGM(Context context){
		bgm.stop();
	}

}
