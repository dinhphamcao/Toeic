package com.framgia.toeic;

import com.framgia.toeic.until.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	// initial creator
	Button btn_reading, btn_listening;
	TextView tv_reading, tv_listening;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		tv_reading = (TextView) findViewById(R.id.tv_reading);
		tv_listening = (TextView) findViewById(R.id.tv_listening);

		// set text size
		tv_reading.setTextSize(getResources().getDimension(R.dimen.textsize));
		tv_listening.setTextSize(getResources().getDimension(R.dimen.textsize));

		tv_reading.setOnClickListener(this);
		tv_listening.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_reading:
			startActivityShowTestList(AppConst.READING);
			break;
		case R.id.tv_listening:
			startActivityShowTestList(AppConst.LISTENING);
			break;

		// case R.id.btn_listening:
		// Intent listening = new Intent(MainActivity.this,
		//
		// DetailActivityListening.class);
		// listening.putExtra(AppConst.DB_TYPE, AppConst.LISTENING);
		// startActivity(listening);
		// break;
		// case R.id.btn_writing:
		// startActivityShowDB(AppConst.WRITING);
		// break;
		}

	}

	private void startActivityShowTestList(int requestTestList) {
		Intent intentTestlist = new Intent(MainActivity.this,
				ListTestActivity.class);
		intentTestlist.putExtra(AppConst.DB_TYPE, requestTestList);
		startActivity(intentTestlist);
	}

}
