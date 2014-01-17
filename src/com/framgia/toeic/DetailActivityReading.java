package com.framgia.toeic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.framgia.toeic.until.AppConst;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivityReading extends Activity {

	int amoutOfQuestion = 10;
	int index = 0;

	// variable get index of READING Test type
	private int indexTest;

	String userAnswer = "";
	List<Question> list_question;
	Question currentQuestion;
	int countCorrectAnswer = 0;
	TextView tv_question_title, tv_question, tv_Scores;
	RadioButton rbtn_a, rbtn_b, rbtn_c, rbtn_d;
	RadioGroup radioGroup1;
	Button btn_Confirm, btn_nextQuestion, btn_Reset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		// init defined
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
		btn_Confirm = (Button) findViewById(R.id.btn_Confirm);
		btn_Confirm.setTextSize(getResources().getDimension(R.dimen.textsize));
		btn_nextQuestion = (Button) findViewById(R.id.btn_nextQuestion);
		btn_nextQuestion.setTextSize(getResources().getDimension(
				R.dimen.textsize));
		btn_Reset = (Button) findViewById(R.id.btn_Reset);
		btn_Reset.setTextSize(getResources().getDimension(R.dimen.textsize));

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
		list_question = db.getQuestion(amoutOfQuestion,
				getDatabaseName(indexTest));
		show(index);
		tv_Scores.setText("Total correct answer(s): " + countCorrectAnswer
				+ "/" + amoutOfQuestion);

		// Button Confirm answer click
		btn_Confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (radioGroup1.getCheckedRadioButtonId() == -1)
					Toast.makeText(getBaseContext(), "Please select an answer",
							Toast.LENGTH_SHORT).show();
				else {
					CheckAnswer(userAnswer);
					btn_Confirm.setEnabled(false);

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
					} else if (currentQuestion.correctAnswer
							.equalsIgnoreCase("b")) {
						rbtn_b.setTextColor(Color.BLUE);
					} else if (currentQuestion.correctAnswer
							.equalsIgnoreCase("c")) {
						rbtn_c.setTextColor(Color.BLUE);
					} else if (currentQuestion.correctAnswer
							.equalsIgnoreCase("d")) {
						rbtn_d.setTextColor(Color.BLUE);
					}

					rbtn_a.setEnabled(false);
					rbtn_b.setEnabled(false);
					rbtn_c.setEnabled(false);
					rbtn_d.setEnabled(false);

					if (index == amoutOfQuestion - 1) {
						btn_nextQuestion.setEnabled(false);
						Toast.makeText(
								getApplicationContext(),
								"The Test Was Completed" + "\n"
										+ "Click Back to Return",
								Toast.LENGTH_LONG).show();
					} else
						btn_nextQuestion.setEnabled(true);

				}

			}
		});

		// Button RESET clicked
		btn_Reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index = 0;
				show(index);
				btn_Confirm.setEnabled(true);

				rbtn_a.setEnabled(true);
				rbtn_b.setEnabled(true);
				rbtn_c.setEnabled(true);
				rbtn_d.setEnabled(true);
				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);
				countCorrectAnswer = 0;
				tv_Scores.setText("Total correct answer(s): "
						+ countCorrectAnswer + "/" + amoutOfQuestion);

			}
		});

		// Button next question was clicked
		btn_nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nextQuestion();

			}
		});

	}

	// nextQuestion function will be call when btn_question was clicked
	private void nextQuestion() {
		btn_Confirm.setEnabled(true);
		rbtn_a.setTextColor(Color.BLACK);
		rbtn_b.setTextColor(Color.BLACK);
		rbtn_c.setTextColor(Color.BLACK);
		rbtn_d.setTextColor(Color.BLACK);
		if (radioGroup1.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getBaseContext(), "Please select an answer",
					Toast.LENGTH_SHORT).show();
			show(index);
		} else {

			index++;
			if (index < amoutOfQuestion) {
				show(index);
				rbtn_a.setEnabled(true);
				rbtn_b.setEnabled(true);
				rbtn_c.setEnabled(true);
				rbtn_d.setEnabled(true);
				if (index == amoutOfQuestion - 1) {
					btn_nextQuestion.setEnabled(false);
				}
			} else if (index == amoutOfQuestion) {
				index = amoutOfQuestion - 1;
			} else {
				index = -1;
			}
		}

	}

	private String getDatabaseName(int kindOfTest) {
		String testKind = null;
		switch (kindOfTest) {
		case AppConst.READING1:
			testKind = AppConst.TABLE_READING_1;
			break;
		case AppConst.READING2:
			testKind = AppConst.TABLE_READING_2;
			break;
		case AppConst.READING3:
			testKind = AppConst.TABLE_READING_3;
			break;

		// case AppConst.LISTENING:
		// testKind = AppConst.TABLE_LISTENING;
		// break;
		// case AppConst.WRITING:
		// testKind = AppConst.TABLE_WRITING;
		// break;
		}
		return testKind;
	}

	// check answer
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
			Toast.makeText(getApplicationContext(), "Correct Answer",
					Toast.LENGTH_SHORT).show();
			tv_Scores.setText("Total correct answer(s): " + countCorrectAnswer
					+ "/" + amoutOfQuestion);

		}

	}

	// function show the question
	public void show(int position) {
		tv_question_title.setText("Question: " + (position + 1) + "/"
				+ amoutOfQuestion);
		currentQuestion = list_question.get(position);
		tv_question.setText(currentQuestion.question);
		rbtn_a.setText(currentQuestion.answer_a);
		rbtn_b.setText(currentQuestion.answer_b);
		rbtn_c.setText(currentQuestion.answer_c);
		rbtn_d.setText(currentQuestion.answer_d);
		radioGroup1.clearCheck();
		btn_nextQuestion.setEnabled(false);
	}

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
				//CheckAnswer(userAnswer);

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
