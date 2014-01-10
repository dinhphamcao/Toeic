package com.framgia.toeic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.framgia.toeic.until.AppConst;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionManagement extends SQLiteOpenHelper {
	private SQLiteDatabase myDatabase;
	private final Context myContext;

	public QuestionManagement(Context context) {
		super(context, AppConst.DB_NAME, null, AppConst.DB_VERSION);
		// TODO Auto-generated constructor stub
		myContext = context;
	}

	public void openDatabase() throws SQLException {
		String myPath = AppConst.DB_PATH + AppConst.DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	public synchronized void close() {
		if (myDatabase != null)
			myDatabase.close();
		super.close();
	}

	private boolean checkDatabase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = AppConst.DB_PATH + AppConst.DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLException e) {
			// TODO: handle exception
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;

	}

	private void copyDatabase() throws IOException {
		InputStream myInput = myContext.getAssets().open(AppConst.DB_NAME);
		String outFileName = AppConst.DB_PATH + AppConst.DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void createDatabase() throws IOException {
		boolean dbExits = checkDatabase();
		if (dbExits) {

		} else {
			this.getReadableDatabase();
			try {
				copyDatabase();
			} catch (IOException e) {
				// TODO: handle exception]
				throw new Error("Error copy database");
			}
		}
	}

	// public Cursor getAllQuestions() {
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor pointer = db.rawQuery("select * from " + AppConst.TABLE_READING,
	// null);
	// return pointer;
	// }

	public List<Question> autoGetQuestion(int numOfQuestion, String tableName) {
		List<Question> list_question = new ArrayList<Question>();
		String limit = "0, " + numOfQuestion;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor pointer = db.query(tableName, null, null, null, null, null,
				"random()", limit);
		pointer.moveToFirst();
		do {
			Question x = new Question();
			x._id = Integer.parseInt(pointer.getString(0));
			x.question = pointer.getString(1);
			x.answer_a = pointer.getString(2);
			x.answer_b = pointer.getString(3);
			x.answer_c = pointer.getString(4);
			x.answer_d = pointer.getString(5);
			x.correctAnswer = pointer.getString(6);
			x.answer = "";
			list_question.add(x);
		} while (pointer.moveToNext());
		return list_question;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
