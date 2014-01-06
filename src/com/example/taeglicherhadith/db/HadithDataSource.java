package com.example.taeglicherhadith.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HadithDataSource {

	public static final String LOGTAG = "DAILY_HADITH";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] hadithColumns = {
		HadithDBOpenHelper.COLUMN_ID,
		HadithDBOpenHelper.COLUMN_TITLE,
		HadithDBOpenHelper.COLUMN_HADITH
	};
	
	public HadithDataSource(Context context) {
		dbhelper = new HadithDBOpenHelper(context);
	}
	
	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}
	
	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}
	
	public Hadith create(Hadith hadith) {
		ContentValues values = new ContentValues();
		values.put(HadithDBOpenHelper.COLUMN_TITLE, hadith.getTitle());
		values.put(HadithDBOpenHelper.COLUMN_HADITH, hadith.getHadith());
		long insertid = database.insert(HadithDBOpenHelper.TABLE_HADITH, null, values);
		hadith.setId(insertid);
		return hadith;
	}
	
	public List<Hadith> findAllHadith() {
		List<Hadith> ahadith = new ArrayList<Hadith>();
		
		Cursor cursor = dbhelper.getReadableDatabase().query(HadithDBOpenHelper.TABLE_HADITH , hadithColumns, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Hadith hadith = new Hadith(cursor.getString(cursor.getColumnIndex(HadithDBOpenHelper.COLUMN_TITLE)), 
						cursor.getString(cursor.getColumnIndex(HadithDBOpenHelper.COLUMN_HADITH)));
				hadith.setId(cursor.getLong(cursor.getColumnIndex(HadithDBOpenHelper.COLUMN_ID)));
				
				ahadith.add(hadith);
			}
		}
		return ahadith;
	}
	
}
