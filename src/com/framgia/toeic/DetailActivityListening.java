package com.framgia.toeic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.toeic.until.AppConst;

public class DetailActivityListening extends Activity {
	int amoutOfQuestion = 10;
	int index = 0, dbType;
	MediaPlayer ourSong;
	String userAnswer = "";
	List<Question> list_question;
	Question currentQuestion;
	int countCorrectAnswer = 0;
	// variable get index of LISTENING Test type
	private int indexTest;

	TextView tv_question_title, tv_question, tv_Scores;
	ImageView imv_Picture;
	RadioButton rbtn_a, rbtn_b, rbtn_c, rbtn_d;
	RadioGroup radioGroup1;
	Button btn_previousQuestion, btn_nextQuestion, btn_Scores;

	int[] imageList = null;
	int[] musicList = null;

	int[] myImageList1 = new int[] { R.drawable.four_1, R.drawable.four_2,
			R.drawable.four_3, R.drawable.four_4, R.drawable.four_5,
			R.drawable.four_6, R.drawable.four_7, R.drawable.four_8,
			R.drawable.four_9, R.drawable.four_10, };
	int[] myMusic1 = new int[] { R.raw.four_1, R.raw.four_2, R.raw.four_3,
			R.raw.four_4, R.raw.four_5, R.raw.four_6, R.raw.four_7,
			R.raw.four_8, R.raw.four_9, R.raw.four_10 };
	int[] myImageList2 = new int[] { R.drawable.five_1, R.drawable.five_2,
			R.drawable.five_3, R.drawable.five_4, R.drawable.five_5,
			R.drawable.five_6, R.drawable.five_7, R.drawable.five_8,
			R.drawable.five_9, R.drawable.five_10, };
	int[] myMusic2 = new int[] { R.raw.five_1, R.raw.five_2, R.raw.five_3,
			R.raw.five_4, R.raw.five_5, R.raw.five_6, R.raw.five_7,
			R.raw.five_8, R.raw.five_9, R.raw.five_10 };
	int[] myImageList3 = new int[] { R.drawable.first_1, R.drawable.first_2,
			R.drawable.first_3, R.drawable.first_4, R.drawable.first_5,
			R.drawable.first_6, R.drawable.first_7, R.drawable.first_8,
			R.drawable.first_9, R.drawable.first_10, };
	int[] myMusic3 = new int[] { R.raw.first_1, R.raw.first_2, R.raw.first_3,
			R.raw.first_4, R.raw.first_5, R.raw.first_6, R.raw.first_7,
			R.raw.first_8, R.raw.first_9, R.raw.first_10 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detail_listening);

		// initial defined
		tv_question_title = (TextView) findViewById(R.id.tv_question_title);
		tv_question = (TextView) findViewById(R.id.tv_question);
		tv_Scores = (TextView) findViewById(R.id.tv_Scores);

		rbtn_a = (RadioButton) findViewById(R.id.rbtn_a);
		rbtn_b = (RadioButton) findViewById(R.id.rbtn_b);
		rbtn_c = (RadioButton) findViewById(R.id.rbtn_c);
		rbtn_d = (RadioButton) findViewById(R.id.rbtn_d);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		imv_Picture = (ImageView) findViewById(R.id.imv_Picture);

		Button btn_previousQuestion = (Button) findViewById(R.id.btn_previousQuestion);
		btn_nextQuestion = (Button) findViewById(R.id.btn_nextQuestion);
		Button btn_Scores = (Button) findViewById(R.id.btn_Scores);
		btn_previousQuestion.setVisibility(View.GONE);

		QuestionManagement db = new QuestionManagement(this);

		try {
			db.createDatabase();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(this, "Database creator error", Toast.LENGTH_SHORT)
					.show();
		}

		indexTest = getIntent().getExtras().getInt(AppConst.TEST_INDEX);

		list_question = new ArrayList<Question>();

		// get data base question form database name
		list_question = db.getQuestion(amoutOfQuestion,
				getDatabaseName(indexTest));

		show(index, imageList, musicList);

		tv_Scores.setText("Total correct answer(s): " + countCorrectAnswer
				+ "/" + amoutOfQuestion);

		// Radiogroup clicked

		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);

				switch (checkedId) {
				// check answer a
				case R.id.rbtn_a:
					userAnswer = "a";
					if (currentQuestion.correctAnswer
							.equalsIgnoreCase(userAnswer)) {
						rbtn_a.setTextColor(Color.GREEN);
						CheckAnswer();
					} else
						rbtn_a.setTextColor(Color.RED);
					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);
					break;
				case R.id.rbtn_b:
					userAnswer = "b";
					if (currentQuestion.correctAnswer
							.equalsIgnoreCase(userAnswer)) {
						rbtn_b.setTextColor(Color.GREEN);
						CheckAnswer();
					} else
						rbtn_b.setTextColor(Color.RED);
					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);
					break;
				case R.id.rbtn_c:
					userAnswer = "c";
					if (currentQuestion.correctAnswer
							.equalsIgnoreCase(userAnswer)) {
						rbtn_c.setTextColor(Color.GREEN);
						CheckAnswer();
					} else
						rbtn_c.setTextColor(Color.RED);
					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);
					break;
				case R.id.rbtn_d:
					userAnswer = "d";
					if (currentQuestion.correctAnswer
							.equalsIgnoreCase(userAnswer)) {
						rbtn_d.setTextColor(Color.GREEN);
						CheckAnswer();
					} else
						rbtn_d.setTextColor(Color.RED);
					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);
					break;

				}

			}
		});

		// btn scores clicked
		btn_Scores.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index = 0;
				ourSong.release();
				show(index, imageList, musicList);
				rbtn_a.setEnabled(true);
				rbtn_b.setEnabled(true);
				rbtn_c.setEnabled(true);
				rbtn_d.setEnabled(true);
				btn_nextQuestion.setEnabled(true);
				countCorrectAnswer = 0;
				btn_nextQuestion.setText("Next Question");
				tv_Scores.setText("Total correct answer(s): "
						+ countCorrectAnswer + "/" + amoutOfQuestion);

			}
		});

		// Button click next question

		btn_nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ourSong.release();
				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);

				if (radioGroup1.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getBaseContext(), "Please select an answer",
							Toast.LENGTH_SHORT).show();
					show(index, imageList, musicList);
				} else {

					index++;
					if (index < amoutOfQuestion) {
						show(index, imageList, musicList);
						rbtn_a.setEnabled(true);
						rbtn_b.setEnabled(true);
						rbtn_c.setEnabled(true);
						rbtn_d.setEnabled(true);
						if (index == amoutOfQuestion - 1) {
							btn_nextQuestion.setText("Finish");
							btn_nextQuestion.setEnabled(false);

						}
					} else {
						index = -1;
					}

				}
			}
		});

	}

	// Get data base name and set Resource Image and Audio
	private String getDatabaseName(int kindOfTest) {
		String testKind = null;
		switch (kindOfTest) {
		case AppConst.LISTENING1:
			testKind = AppConst.TABLE_LISTENING_1;
			imageList = myImageList1;
			musicList = myMusic1;
			break;
		case AppConst.LISTENING2:
			testKind = AppConst.TABLE_LISTENING_2;
			imageList = myImageList2;
			musicList = myMusic2;
			break;
		case AppConst.LISTENING3:
			testKind = AppConst.TABLE_LISTENING_3;
			imageList = myImageList3;
			musicList = myMusic3;
			break;

		}
		return testKind;
	}

	// check answer function
	public void CheckAnswer() {
		String a = "";
		if (rbtn_a.isChecked() == true)
			a = "a";
		else if (rbtn_b.isChecked() == true)
			a = "b";
		else if (rbtn_c.isChecked() == true)
			a = "c";
		else if (rbtn_d.isChecked() == true)
			a = "d";

		list_question.get(index).answer = a;

		if (a.equalsIgnoreCase(currentQuestion.correctAnswer))
			countCorrectAnswer++;
		tv_Scores.setText("Total correct answer(s): " + countCorrectAnswer
				+ "/" + amoutOfQuestion);
	}

	// show to the layout from DB
	public void show(int position, int[] imageList, int[] musicList) {

		// open the mp3 file
		ourSong = MediaPlayer.create(DetailActivityListening.this,
				musicList[position]);
		ourSong.start();

		// set the picture
		imv_Picture.setImageResource(imageList[position]);

		// set the scores
		tv_question_title.setText("Question: " + (position + 1) + "/"
				+ amoutOfQuestion);

		currentQuestion = list_question.get(position);

		// write the question and choices currentQuestion.question
		int countQuestion = position + 1;
		tv_question_title.setText("Picture number: " + countQuestion);
		rbtn_a.setText(currentQuestion.answer_a);
		rbtn_b.setText(currentQuestion.answer_b);
		rbtn_c.setText(currentQuestion.answer_c);
		rbtn_d.setText(currentQuestion.answer_d);
		radioGroup1.clearCheck();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (ourSong.isPlaying()) {
			ourSong.stop();
			finish();
		} else
			return;
	}

}
