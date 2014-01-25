package com.framgia.toeic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.toeic.until.AppConst;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

public class DetailActivityListening extends Activity {
	int amoutOfQuestion = 10;
	int index = 0, dbType;
	MediaPlayer ourSong;
	String userAnswer = "";
	List<Question> list_question;
	Question currentQuestion;
	int countCorrectAnswer = 0;

	TextView tv_Practive, tv_Test;
	AdView adview;
	// variable get index of LISTENING Test type
	private int indexTest;

	TextView tv_question_title, tv_question, tv_Scores;
	ImageView imv_Picture;
	RadioButton rbtn_a, rbtn_b, rbtn_c, rbtn_d;
	RadioGroup radioGroup1;
	Button btn_ConfirmListening, btn_nextQuestion, btn_ResetListening;

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

		adview = (AdView) findViewById(R.id.adView);
		AdRequest re = new AdRequest();
		re.setTesting(true);
		adview.loadAd(re);

		// initial defined
		tv_question_title = (TextView) findViewById(R.id.tv_question_title);
		tv_question_title.setTextSize(getResources().getDimension(
				R.dimen.textsize));
		tv_question = (TextView) findViewById(R.id.tv_question);
		tv_question.setTextSize(getResources().getDimension(R.dimen.textsize));
		tv_Scores = (TextView) findViewById(R.id.tv_Scores);
		tv_Scores.setTextSize(getResources().getDimension(R.dimen.textsize));

		rbtn_a = (RadioButton) findViewById(R.id.rbtn_a);
		rbtn_a.setTextSize(getResources().getDimension(R.dimen.textsize));
		rbtn_b = (RadioButton) findViewById(R.id.rbtn_b);
		rbtn_b.setTextSize(getResources().getDimension(R.dimen.textsize));
		rbtn_c = (RadioButton) findViewById(R.id.rbtn_c);
		rbtn_c.setTextSize(getResources().getDimension(R.dimen.textsize));
		rbtn_d = (RadioButton) findViewById(R.id.rbtn_d);
		rbtn_d.setTextSize(getResources().getDimension(R.dimen.textsize));
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		imv_Picture = (ImageView) findViewById(R.id.imv_Picture);
		btn_ConfirmListening = (Button) findViewById(R.id.btn_ConfirmListening);
		btn_ConfirmListening.setTextSize(getResources().getDimension(
				R.dimen.textsize));
		btn_ResetListening = (Button) findViewById(R.id.btn_ResetListening);
		btn_ResetListening.setTextSize(getResources().getDimension(
				R.dimen.textsize));
		btn_nextQuestion = (Button) findViewById(R.id.btn_nextQuestion);
		btn_ConfirmListening = (Button) findViewById(R.id.btn_ConfirmListening);
		btn_ConfirmListening.setTextSize(getResources().getDimension(
				R.dimen.textsize));

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

		// btn Reset clicked
		btn_ResetListening.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);
				index = 0;
				ourSong.release();
				show(index, imageList, musicList);
				rbtn_a.setEnabled(true);
				rbtn_b.setEnabled(true);
				rbtn_c.setEnabled(true);
				rbtn_d.setEnabled(true);
				countCorrectAnswer = 0;
				tv_Scores.setText("Total correct answer(s): "
						+ countCorrectAnswer + "/" + amoutOfQuestion);

			}
		});

		// Button click next question
		btn_nextQuestion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nextQuestion();
			}
		});

		btn_ConfirmListening.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (radioGroup1.getCheckedRadioButtonId() == -1)
					Toast.makeText(getBaseContext(), "Please select an answer",
							Toast.LENGTH_SHORT).show();
				else {
					CheckAnswer(userAnswer);
					btn_ConfirmListening.setEnabled(false);
					makeColor();
					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);
					ourSong.release();
					if (index == amoutOfQuestion - 1) {
						btn_nextQuestion.setEnabled(false);
						Toast.makeText(
								getApplicationContext(),
								"The Test Was Completed" + "\n"
										+ "Click Back to Return",
								Toast.LENGTH_SHORT).show();
					} else
						btn_nextQuestion.setEnabled(true);
				}
			}
		});
	}

	// make color for answer
	public void makeColor() {
		// make user answer becomes RED
		if (currentQuestion.answer.equalsIgnoreCase("a")) {
			rbtn_a.setTextColor(Color.RED);
			rbtn_a.setChecked(true);
		}

		else if (currentQuestion.answer.equalsIgnoreCase("b")) {
			rbtn_b.setTextColor(Color.RED);
			rbtn_b.setChecked(true);

		} else if (currentQuestion.answer.equalsIgnoreCase("c")) {
			rbtn_c.setTextColor(Color.RED);
			rbtn_c.setChecked(true);

		} else if (currentQuestion.answer.equalsIgnoreCase("d")) {
			rbtn_d.setTextColor(Color.RED);
			rbtn_d.setChecked(true);

		}

		// make the correct answer is BLUE
		if (currentQuestion.correctAnswer.equalsIgnoreCase("a")) {
			rbtn_a.setTextColor(Color.BLUE);
		} else if (currentQuestion.correctAnswer.equalsIgnoreCase("b")) {
			rbtn_b.setTextColor(Color.BLUE);
		} else if (currentQuestion.correctAnswer.equalsIgnoreCase("c")) {
			rbtn_c.setTextColor(Color.BLUE);
		} else if (currentQuestion.correctAnswer.equalsIgnoreCase("d")) {
			rbtn_d.setTextColor(Color.BLUE);
		}

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
	public void CheckAnswer(String userAnswer) {
		if (rbtn_a.isChecked() == true)
			userAnswer = "a";
		else if (rbtn_b.isChecked() == true)
			userAnswer = "b";
		else if (rbtn_c.isChecked() == true)
			userAnswer = "c";
		else if (rbtn_d.isChecked() == true)
			userAnswer = "d";

		list_question.get(index).answer = userAnswer;

		if (userAnswer.equalsIgnoreCase(currentQuestion.correctAnswer)) {
			countCorrectAnswer++;
			Toast.makeText(getApplicationContext(), "Correct answer",
					Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(getApplicationContext(), "Wrong answer",
					Toast.LENGTH_SHORT).show();
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
		// Enable Confirm Button
		// btn_ConfirmListening.setEnabled(true);
		// Disable Next Button
		btn_nextQuestion.setEnabled(false);
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
		EasyTracker.getInstance(this).activityStop(this); // Add this method.

		if (ourSong.isPlaying()) {
			ourSong.stop();
			finish();
		} else
			return;
	}

	// NextQuestion function ... move to next question
	public void nextQuestion() {
		btn_ConfirmListening.setEnabled(true);
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
			if (index < amoutOfQuestion && index >= 0) {
				show(index, imageList, musicList);
				rbtn_a.setEnabled(true);
				rbtn_b.setEnabled(true);
				rbtn_c.setEnabled(true);
				rbtn_d.setEnabled(true);
				if (index == amoutOfQuestion - 1) {
					btn_nextQuestion.setText("Finish");
					btn_nextQuestion.setEnabled(false);
				}
			} else if (index == amoutOfQuestion) {
				index = amoutOfQuestion - 1;
			} else {
				index = -1;

			}

		}
	}

	// BackQuestion function ... move to previous question
	public void backQuestion() {
		btn_ConfirmListening.setEnabled(false);
		ourSong.release();
		index--;
		if (index < amoutOfQuestion && index >= 0) {
			show(index, imageList, musicList);
			makeColor();
			rbtn_a.setEnabled(false);
			rbtn_b.setEnabled(false);
			rbtn_c.setEnabled(false);
			rbtn_d.setEnabled(false);
			if (index == amoutOfQuestion - 1) {
				btn_nextQuestion.setText("Finish");
				btn_nextQuestion.setEnabled(false);
			}
		} else if (index == -1) {
			index = 0;
		}

		else {
			index = -1;

		}
	}

	// Swipe detector
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}

	SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float sensitvity = 50;

			// If swipe from right to left
			if ((e1.getX() - e2.getX()) > sensitvity) {

				// CheckAnswer(userAnswer);
				nextQuestion();

			}
			// if swipe from left to right (BACK TO PREVIOUS QUESTION)
			else if ((e2.getX() - e1.getX()) > sensitvity) {
				// backQuestion();
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}
	};

	GestureDetector gestureDetector = new GestureDetector(
			simpleOnGestureListener);

	@Override
	public void onStart() {
		super.onStart();

		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

}
