package com.app.dressitup.httpwrapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** This is a test to wrapp clothing sites like Zara to extract clothing info
 * 
 * @author Hugo
 * 
 * Redireccionamento site Zara
 * 
 * Primeiro o link do código qr é algo assim:
 * http://www.zara.com/qr/01446301403048
 *
 * Que após a configuração das cookies vai gravar a língua e o país e no fim dá origem a um link assim:
 * http://www.zara.com/webapp/wcs/stores/servlet/qr/01446301403048
 *
 * Nessa página dentro de uma função javascript há algo assim:
 * result = servlet + '/' + storeId + '/' + langId  + '/-c0p' + productId + '.html';
 *
 * Que dá origem a um link deste género, que redirecciona automaticamente para o link final da roupa (sem javascript):
 * http://www.zara.com/pt/pt/-c0p1983094.html
 *
 */
public class ZaraSiteParser implements SiteParser {
	
	private Document document;
	
	public ZaraSiteParser(String qrUrl) throws IOException {

		// Follow the first url redirection to get productId
		String redirectedUrl = "http://www.zara.com/webapp/wcs/stores/servlet" + qrUrl.replace("http://www.zara.com", "");
		document = Jsoup.connect(redirectedUrl).get();
		Pattern pattern = Pattern.compile("productId = '[0-9]+");
		Matcher matcher = pattern.matcher(document.select("script").toString());
		String productId = null;
		while(matcher.find())
			productId = matcher.group(0).replace("productId = '", "");

		// Get the final clothing url using productId
		String clothingUrl = "http://www.zara.com/pt/pt/-c0p" + productId + ".html";
		document = Jsoup.connect(clothingUrl).get();
	}
	
	public String getReference() {
		// Get the clothing reference number
		Elements referenceElements = document.getElementsByClass("reference");
		String reference = referenceElements.text();
		return reference;
	}
	
	public String getCategory() {
		// Get the clothing category
		String[] baseUriSplit = document.baseUri().split("/");
		String category = baseUriSplit[6];
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
