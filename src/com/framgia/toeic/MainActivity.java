package com.framgia.toeic;

import com.framgia.toeic.until.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	// initial creator
	Button btn_reading, btn_listening, btn_writing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		btn_reading = (Button) findViewById(R.id.btn_reading);
		btn_listening = (Button) findViewById(R.id.btn_listening);
		btn_writing = (Button) findViewById(R.id.btn_writing);
		btn_reading.setOnClickListener(this);
		btn_listening.setOnClickListener(this);
		btn_writing.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_reading:
			startActivityShowDB(AppConst.READING);
			break;
		case R.id.btn_listening:
			startActivityShowDB(AppConst.LISTENING);
			break;
		case R.id.btn_writing:
			startActivityShowDB(AppConst.WRITING);
			break;
		}

	}

	private void startActivityShowDB(int requestDB) {
		Intent intentDB = new Intent(MainActivity.this, DetailActivity.class);
		intentDB.putExtra(AppConst.DB_TYPE, requestDB);
		startActivity(intentDB);
	}
}
