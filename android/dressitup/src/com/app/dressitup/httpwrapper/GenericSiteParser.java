package com.app.dressitup.httpwrapper;

import java.io.IOException;

public class GenericSiteParser {
	
	private SiteParser siteParser;
	private String brand;
	
	public GenericSiteParser(String url) throws IOException {
		
		String[] urlSplitted = url.split("\\.");
		brand = urlSplitted[1];
		
		switch(brand) {
		case "zara":
			siteParser = new ZaraSiteParser(url);
			break;
		}
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public String getReference() {
		return siteParser.getReference();
	}
	
	public String getCategory() {
		return siteParser.getCategory();
	}
	
	public String getColor() {
		return siteParser.getColor();
	}
	
	public String getImageUrl() {
		return siteParser.getImageUrl();
	}

}
