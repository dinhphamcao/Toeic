package com.framgia.toeic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framgia.toeic.until.AppConst;

public class ListTestActivity extends Activity implements OnClickListener {
	private String[] listItem;
	private int inputButton_Type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_list);

		inputButton_Type = getIntent().getExtras().getInt(AppConst.DB_TYPE);

		listItem = getListItem(inputButton_Type);
		if (listItem == null) {
			return;
		}

		initView(listItem, inputButton_Type);

	}

	private void initView(String[] listItem, int typeButton) {
		LinearLayout linearLayoutList = (LinearLayout) findViewById(R.id.test_list);
		LayoutInflater inflater = (LayoutInflater) ListTestActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < listItem.length; i++) {
			View row = inflater.inflate(R.layout.test_row, null);
			TextView tv_test_row = (TextView) row
					.findViewById(R.id.tv_test_row);
			tv_test_row.setTag(Integer.valueOf(i));
			tv_test_row.setText(listItem[i]);
			tv_test_row.setOnClickListener(this);
			tv_test_row.setTextSize(getResources().getDimension(
					R.dimen.textsize));

			linearLayoutList.addView(row);
		}
	}

	private String[] getListItem(int buttonType) {

		String[] arrList = null;
		switch (buttonType) {
		case AppConst.READING:
			arrList = getResources().getStringArray(R.array.list_reading);
			break;
		case AppConst.LISTENING:
			arrList = getResources().getStringArray(R.array.list_listening);
			break;
		}
		return arrList;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v instanceof TextView) {
			int indexTest = ((Integer) v.getTag()).intValue();
			inputButton_Type = getIntent().getExtras().getInt(AppConst.DB_TYPE);
			if (inputButton_Type == AppConst.LISTENING) {
				Intent startActivityListening = new Intent(
						ListTestActivity.this, DetailActivityListening.class);
				startActivityListening.putExtra(AppConst.DB_TYPE,
						inputButton_Type);
				startActivityListening.putExtra(AppConst.TEST_INDEX, indexTest);
				startActivityListening.putExtra(AppConst.TEST_TYPE,
						inputButton_Type);
				startActivity(startActivityListening);

			} else if (inputButton_Type == AppConst.READING) {
				Intent startActivityReading = new Intent(ListTestActivity.this,
						DetailActivity.class);
				startActivityReading.putExtra(AppConst.DB_TYPE,
						inputButton_Type);
				startActivityReading.putExtra(AppConst.TEST_INDEX, indexTest);
				startActivityReading.putExtra(AppConst.TEST_TYPE,
						inputButton_Type);
				startActivity(startActivityReading);
			}

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();

	}

}
