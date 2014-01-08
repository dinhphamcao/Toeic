package com.framgia.toeic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intentMainScreen = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intentMainScreen);
				SplashActivity.this.finish();

			}
		}, 2000);

	}
}
