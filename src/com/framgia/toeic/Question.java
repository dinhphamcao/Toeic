package com.framgia.toeic;

public class Question {
	public int _id;
	public String question, answer_a, answer_b, answer_c, answer_d, correctAnswer,answer;

	public Question() {
	}

	public Question(int _id, String question, String a, String b, String c,
			String d, String correctAnswer, String answer) {
		this._id = _id;
		this.answer_a = a;
		this.answer_b = b;
		this.answer_c = c;
		this.answer_d = d;
		this.correctAnswer = correctAnswer;
		this.answer = answer;

	}
}
