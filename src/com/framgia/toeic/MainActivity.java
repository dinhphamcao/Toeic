package com.framgia.toeic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.framgia.toeic.until.AppConst;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity implements OnClickListener {

	TextView tv_Practive, tv_Test;
	AdView adview;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home0);
		tv_Practive = (TextView) findViewById(R.id.tv_Practice);
		tv_Test = (TextView) findViewById(R.id.tv_Test);
		tv_Practive.setOnClickListener(this);
		tv_Test.setOnClickListener(this);

		adview = (AdView) findViewById(R.id.adView);
		AdRequest re = new AdRequest();
		re.setTesting(true);
		adview.loadAd(re);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_Practice:
			StartActivityHome(AppConst.PRACTICE_AREA);

			break;
		case R.id.tv_Test:
			StartActivityHome(AppConst.TEST_AREA);
			break;
		}

	}

	private void StartActivityHome(int area) {
		Intent areaList = new Intent(MainActivity.this, MainAreaActivity.class);
		areaList.putExtra(AppConst.AREA_TYPE, area);
		startActivity(areaList);
	}

	@Override
	public void onStart() {
		super.onStart();

		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();

		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}
}
