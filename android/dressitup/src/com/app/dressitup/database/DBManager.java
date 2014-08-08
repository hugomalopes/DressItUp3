package com.app.dressitup.database;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBManager extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "dressitup.db";
	private static final int DATABASE_VERSION = 1;
	
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
}