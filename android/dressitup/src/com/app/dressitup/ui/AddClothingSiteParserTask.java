package com.app.dressitup.ui;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.app.dressitup.database.DBManager;
import com.app.dressitup.httpwrapper.GenericSiteParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class launch an async task to access and get clothing info from the website
 * Being asynchronous allows to build a queue of pending clothing to add
 * @author hugo
 *
 */
public class AddClothingSiteParserTask extends AsyncTask<String, Void, GenericSiteParser> {
	
	private Context appContext;
	private DBManager dbManager;
	private ImageView clothingImage;
	private TextView clothingBrand;
    private TextView clothingReference;
    private TextView clothingCategory;
    private TextView clothingColor;
    private String type, category, brand, reference, color;
    
    public AddClothingSiteParserTask(Context appContext, DBManager dbManager, ImageView clothingImage, TextView clothingBrand, TextView clothingReference, TextView clothingCategory, TextView clothingColor) {
    	this.appContext = appContext;
    	this.dbManager = dbManager;
    	this.clothingImage = clothingImage;
    	this.clothingBrand = clothingBrand;
    	this.clothingReference = clothingReference;
    	this.clothingCategory = clothingCategory;
    	this.clothingColor = clothingColor;
    }
    
	@Override
	protected GenericSiteParser doInBackground(String... url) {
		GenericSiteParser genericSiteParser = null;
        try {
        	genericSiteParser = new GenericSiteParser(url[0]);
        	
        	category = genericSiteParser.getCategory();
			brand = genericSiteParser.getBrand();
			reference = genericSiteParser.getReference();
			color = genericSiteParser.getColor();
			
			// Save clothing image to internal storage with reference as filename
        	URL imageUrl = new URL(genericSiteParser.getImageUrl());
        	HttpURLConnection imageConnection = (HttpURLConnection) imageUrl.openConnection();
        	InputStream imageInputStream = imageConnection.getInputStream();
        	Bitmap imageBitmap = BitmapFactory.decodeStream(imageInputStream);
        	FileOutputStream imageOutputStream = appContext.openFileOutput(reference, Context.MODE_PRIVATE);
        	imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutputStream);
        	imageOutputStream.close();
        	
        } catch(Exception e) {}
        
        return genericSiteParser;
	}

	@Override
	protected void onPostExecute(GenericSiteParser genericSiteParser) {
		if(genericSiteParser == null) {
			clothingBrand.setText("Can't get clothing info :(");
		}
		else {
			clothingCategory.setText(category);
			clothingBrand.setText(brand);
        	clothingReference.setText(reference);
        	clothingColor.setText(color);
        	
        	/* Example of how to get image from internal storage
        	try{
	        	FileInputStream imageInputStream = appContext.openFileInput(reference);
	        	Bitmap storedImage = BitmapFactory.decodeStream(imageInputStream);
	        	clothingImage.setImageBitmap(storedImage);
        	} catch(Exception e) {System.err.println("------ "); e.printStackTrace();}
        	*/
        	
        	dbManager.insertClothing(type, category, brand, reference, color);
		}
	}
}
