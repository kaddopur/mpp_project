package com.mpp.hayakuchi;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	static MediaPlayer mp;
	
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

}
