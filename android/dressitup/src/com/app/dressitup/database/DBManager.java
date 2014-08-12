package com.app.dressitup.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * The Class DBManager.
 */
public class DBManager extends SQLiteAssetHelper {

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "dressitup.db";
	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;
	private final String CLOTHING = "clothing";
	
	/**
	 * Instantiates a new DB manager.
	 *
	 * @param context the context
	 */
	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	
	public Cursor getDataBaseTable(String tableName) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tableName);
		// Gets cursor to tableName
		Cursor c = qb.query(db, null, null, null, null, null, null);
		if(c != null)
			c.moveToFirst();
		
		return c;
	}
	
	public Cursor getClothingById(int id) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(CLOTHING);
		Cursor c = qb.query(db, null, "_id=?", new String[] { String.valueOf(id)}, null, null, null, null);
		if(c != null)
			c.moveToFirst();
		return c;
	}
	
}
