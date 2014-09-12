package com.app.dressitup.ui;

import com.app.dressitup.database.DBManager;
import com.app.dressitup.httpwrapper.GenericSiteParser;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * This class launch an async task to access and get clothing info from the website
 * Being asynchronous allows to build a queue of pending clothing to add
 * @author hugo
 *
 */
public class AddClothingSiteParserTask extends AsyncTask<String, Void, GenericSiteParser> {
	
	private DBManager dbManager;
	private TextView clothingBrand;
    private TextView clothingReference;
    private TextView clothingCategory;
    private TextView clothingColor;
    private String type, category, brand, reference, color;
    
    public AddClothingSiteParserTask(DBManager dbManager, TextView clothingBrand, TextView clothingReference, TextView clothingCategory, TextView clothingColor) {
    	this.dbManager = dbManager;
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
        } catch(Exception e) {}
        
        return genericSiteParser;
	}

	@Override
	protected void onPostExecute(GenericSiteParser genericSiteParser) {
		if(genericSiteParser == null) {
			clothingBrand.setText("Can't get clothing info :(");
		}
		else {
			category = genericSiteParser.getCategory();
			brand = genericSiteParser.getBrand();
			reference = genericSiteParser.getReference();
			color = genericSiteParser.getColor();
			clothingCategory.setText(category);
			clothingBrand.setText(brand);
        	clothingReference.setText(reference);
        	clothingColor.setText(color);
        	dbManager.insertClothing(type, category, brand, reference, color);
		}
	}
}
