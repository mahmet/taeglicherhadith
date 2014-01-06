package com.example.taeglicherhadith.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HadithDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "DAILY_HADITH";
	
	private static final String DATABASE_NAME = "favorites.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_HADITH = "hadith";
	public static final String COLUMN_ID = "hadithID";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_HADITH = "hadith";
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_HADITH + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_TITLE + " TEXT, " +
			COLUMN_HADITH + " TEXT " +
			")";
	
	public HadithDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "TABLE HAS BEEN CREATED");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HADITH);
		onCreate(db);
	}

}
