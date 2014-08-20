package com.app.dressitup.domain;

import android.database.Cursor;

import com.app.dressitup.database.DBManager;

public class Clothing {
	private int id;
	private String type;
	private String category;
	private String brand;
	private String reference;
	private String color;
	
	public Clothing(int id, String type, String category, String brand, String reference, String color){
		this.id = id;
		this.type = type;
		this.category = category;
		this.brand = brand;
		this.reference = reference;
		this.color = color;
	}

	//Converts a Cursor to a Clothing object
	
	public static Clothing cursorToClothing(Cursor cursor){
		if(!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex(DBManager.KEY_CLOTHING_ID));
			String type = cursor.getString(cursor.getColumnIndex(DBManager.KEY_CLOTHING_TYPE));
			String category = cursor.getString(cursor.getColumnIndex(DBManager.KEY_CLOTHING_CATEGORY));
			String brand = cursor.getString(cursor.getColumnIndex(DBManager.KEY_CLOTHING_BRAND));
			String reference = cursor.getString(cursor.getColumnIndex(DBManager.KEY_CLOTHING_REFERENCE));
			String color = cursor.getString(cursor.getColumnIndex(DBManager.KEY_CLOTHING_COLOR));
			
			return new Clothing(id, type, category, brand, reference, color);
		} else {
			return null;
		}		
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public String getReference() {
		return reference;
	}

	public String getColor() {
		return color;
	}
}
