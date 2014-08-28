package com.app.dressitup.httpwrapper;

import java.io.IOException;
import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class ZaraSiteParser implements SiteParser {
	
	private Document document;
	
	public ZaraSiteParser(String url) throws IOException {
		/** HTMLUnit block used to run javascript url redirection and get the clothing specific url */
		//turn off all the annoying log warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		
		WebClient webClient = new WebClient();
		//Prevents crashing from bad javascript functions
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		//Injection of necessary cookie to be redirect to the specific clothing url - contains country and language info
		webClient.getCookieManager().addCookie(new Cookie(".www.zara.com", "WC_ZaraStoreId", "pt%2Cpt"));
		HtmlPage htmlPage = webClient.getPage(url);
		String redirectedUrl = htmlPage.getUrl().toString();
		/** End of HTMLUnit block */
		
		/** JSOUP block used to scrap static html page */
		document = Jsoup.connect(redirectedUrl).get();
		/** End of JSOUP block */
	}
	
	public String getReference() {
		// Get the clothing reference number
		Elements referenceElements = document.getElementsByClass("reference");
		String reference = referenceElements.text();
		return reference;
	}
	
	public String getCategory() {
		// Get the clothing category
		Elements categoryElements = document.getElementsByAttributeValueContaining("class", "current selected");
		if(categoryElements.isEmpty())
			categoryElements = document.getElementsByAttributeValueContaining("class", "current  selected");
		Element selectedCategoryElement = categoryElements.get(0).child(0);
		String category = selectedCategoryElement.text();
		return category;
	}
	
	public String getColor() {
		// Get the color value
		Elements colorElements = document.getElementsByClass("colorEl");
		Element selectedColorElement = colorElements.get(0);
		String color = selectedColorElement.text();
		return color;
	}
	
	public String getImageUrl() {
		// Get image URL
		Elements imageElements = document.getElementsByClass("media-wrap").select(".plain");
		Element image = imageElements.get(0);
		String imageURL = image.getAllElements().attr("src");
		return imageURL;
	}

}
