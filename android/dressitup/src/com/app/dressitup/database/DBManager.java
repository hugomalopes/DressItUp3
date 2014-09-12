package com.app.dressitup.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.app.dressitup.domain.Clothing;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * The Class DBManager.
 */
public class DBManager extends SQLiteAssetHelper {

	// Database information
	private static final String DATABASE_NAME = "dressitup.db";
	private static final int DATABASE_VERSION = 1;
	private final String TABLE_USER = "user";
	private final String TABLE_CLOTHING = "clothing";
	private final String TABLE_FUTURE_CLOTHING = "future_clothing";
	
	//User table columns
	public static final String KEY_USER_ID = "_id";
	public static final String KEY_USER_USERNAME = "username";

	//Clothing table columns
	public static final String KEY_CLOTHING_ID = "_id";
	public static final String KEY_CLOTHING_TYPE = "type";
	public static final String KEY_CLOTHING_CATEGORY = "category";
	public static final String KEY_CLOTHING_BRAND = "brand";
	public static final String KEY_CLOTHING_REFERENCE = "reference";
	public static final String KEY_CLOTHING_COLOR = "color";
	
	//FutureClothing columns
	public static final String KEY_FUTURE_CLOTHING_URL = "url";
	
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
		qb.setTables(TABLE_CLOTHING);
		Cursor c = qb.query(db, null, "_id=?", new String[] { String.valueOf(id)}, null, null, null, null);
		if(c != null)
			c.moveToFirst();
		return c;
	}
	
	public ArrayList<Clothing> getAllClothing(){
		ArrayList<Clothing> list = new ArrayList<Clothing>();
		
		SQLiteDatabase db = getReadableDatabase();
		final String QUERY = "SELECT * FROM " + TABLE_CLOTHING;
		Cursor cursor = db.rawQuery(QUERY, null);
		
		while(cursor.moveToNext()) {
			Clothing c = Clothing.cursorToClothing(cursor);
			list.add(c);
		}
		cursor.close();
		
		return list;
	}
	
	public void insertClothing(String type, String category, String brand, String reference, String color) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CLOTHING_TYPE, type);
		cv.put(KEY_CLOTHING_CATEGORY, category);
		cv.put(KEY_CLOTHING_BRAND, brand);
		cv.put(KEY_CLOTHING_REFERENCE, reference);
		cv.put(KEY_CLOTHING_COLOR, color);
		SQLiteDatabase db = getReadableDatabase();
		db.insert(TABLE_CLOTHING, null, cv);
	}
	
}
