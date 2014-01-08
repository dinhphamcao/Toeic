package com.framgia.toeic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	int amoutOfQuestion = 10;
	int index = 0;
	List<Question> list_question;
	Question currentQuestion;
	int countCorrectAnswer = 0;
	TextView tv_question_title, tv_question, tv_Scores;
	RadioButton rbtn_a, rbtn_b, rbtn_c, rbtn_d;
	RadioGroup radioGroup1;
	Button btn_previousQuestion, btn_nextQuestion, btn_Scores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// init defined
		tv_question_title = (TextView) findViewById(R.id.tv_question_title);
		tv_question = (TextView) findViewById(R.id.tv_question);
		tv_Scores = (TextView) findViewById(R.id.tv_Scores);
		rbtn_a = (RadioButton) findViewById(R.id.rbtn_a);
		rbtn_b = (RadioButton) findViewById(R.id.rbtn_b);
		rbtn_c = (RadioButton) findViewById(R.id.rbtn_c);
		rbtn_d = (RadioButton) findViewById(R.id.rbtn_d);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		btn_previousQuestion = (Button) findViewById(R.id.btn_previousQuestion);
		btn_nextQuestion = (Button) findViewById(R.id.btn_nextQuestion);
		btn_Scores = (Button) findViewById(R.id.btn_Scores);
		QuestionManagement db = new QuestionManagement(this);
		try {
			db.createDatabase();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(this, "Database creator error", Toast.LENGTH_SHORT)
					.show();
		}
		list_question = new ArrayList<Question>();
		list_question = db.autoGetQuestion(amoutOfQuestion);
		show(index);

		// Button Back to previous clicked
		btn_previousQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (index == 0) {
					Toast.makeText(getBaseContext(), "Can not back",
							Toast.LENGTH_SHORT).show();

				} else {
					index--;
					show(index);
				}

			}
		});

		// Event on Radiobutton group, change color whenever click was choosen
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String answer = "";
				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);
				switch (checkedId) {

				// check answer a
				case R.id.rbtn_a:
					answer = "a";
					if (currentQuestion.correctAnswer.equalsIgnoreCase(answer)) {
						rbtn_a.setTextColor(Color.GREEN);
					} else {
						rbtn_a.setTextColor(Color.RED);
					}
					break;

				// check answer b
				case R.id.rbtn_b:
					answer = "b";
					if (currentQuestion.correctAnswer.equalsIgnoreCase(answer)) {
						rbtn_b.setTextColor(Color.GREEN);
					} else {
						rbtn_b.setTextColor(Color.RED);
					}
					break;

				// check answer c
				case R.id.rbtn_c:
					answer = "c";
					if (currentQuestion.correctAnswer.equalsIgnoreCase(answer)) {
						rbtn_c.setTextColor(Color.GREEN);
					} else {
						rbtn_c.setTextColor(Color.RED);
					}
					break;

				// check answer d
				case R.id.rbtn_d:
					answer = "d";
					if (currentQuestion.correctAnswer.equalsIgnoreCase(answer)) {
						rbtn_d.setTextColor(Color.GREEN);
					} else {
						rbtn_d.setTextColor(Color.RED);
					}
					break;
				}

				if (currentQuestion.correctAnswer.equalsIgnoreCase(answer)) {
					countCorrectAnswer++;
					// tv_Scores.setText(countCorrectAnswer + "/10");
				}

			}
		});

		// Button next question was clicked
		btn_nextQuestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				rbtn_a.setTextColor(Color.BLACK);
				rbtn_b.setTextColor(Color.BLACK);
				rbtn_c.setTextColor(Color.BLACK);
				rbtn_d.setTextColor(Color.BLACK);

				if (radioGroup1.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getBaseContext(), "Please select an answer",
							Toast.LENGTH_SHORT).show();
				} else {

					index++;
					if (index < amoutOfQuestion) {
						show(index);
					} else {
						index = 0;
					}
				}
			}
		});

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
	}

	// public void CheckAnswer() {
	// String answer = "";
	// // if (rbtn_a.isChecked() == true) {
	// // answer = "a";
	// // if (currentQuestion.answer.equalsIgnoreCase(answer)) {
	// // rbtn_a.setTextColor(Color.GREEN);
	// // } else {
	// // rbtn_a.setTextColor(Color.RED);
	// // }
	// // } else if (rbtn_b.isChecked() == true) {
	// // answer = "b";
	// // if (currentQuestion.answer.equalsIgnoreCase(answer)) {
	// // rbtn_b.setTextColor(Color.GREEN);
	// // } else {
	// // rbtn_b.setTextColor(Color.RED);
	// // }
	// // } else if (rbtn_c.isChecked() == true) {
	// // answer = "c";
	// // if (currentQuestion.answer.equalsIgnoreCase(answer)) {
	// // rbtn_c.setTextColor(Color.GREEN);
	// // } else {
	// // rbtn_c.setTextColor(Color.RED);
	// // }
	// // } else if (rbtn_d.isChecked() == true) {
	// // answer = "d";
	// // if (currentQuestion.answer.equalsIgnoreCase(answer)) {
	// // rbtn_d.setTextColor(Color.GREEN);
	// // } else {
	// // rbtn_d.setTextColor(Color.RED);
	// // }
	// // }
	// // save the answer from user to the list
	// list_question.get(index).answer = answer;
	// // check answer with the correct answer
	// if (answer.equalsIgnoreCase(currentQuestion.correctAnswer)) {
	// countCorrectAnswer += 1;
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
