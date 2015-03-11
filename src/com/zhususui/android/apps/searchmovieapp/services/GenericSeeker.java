package com.zhususui.android.apps.searchmovieapp.services;

import java.net.URLEncoder;
import java.util.ArrayList;

// Abstract base class to be extended by service class 
public abstract class GenericSeeker<E> {
	
	protected static final String BASE_URL = "http://www.omdbapi.com/?t=";
	protected static final String STAR = "*";
	protected static final String XML_FORMAT = "&&r=xml";
	
	protected HttpRetriever httpRetriever = new HttpRetriever();
	protected XmlParser xmlParser = new XmlParser();
	
	public abstract ArrayList<E> find(String query);
	public abstract ArrayList<E> find(String query, int maxResults);

	public abstract String retrieveSearchMethodPath();
	
	protected String constructSearchUrl(String query) {
		StringBuffer sb = new StringBuffer();
		sb.append(BASE_URL);
		sb.append(URLEncoder.encode(query));
		sb.append(STAR);
		sb.append(XML_FORMAT);
		
		return sb.toString();
	}
	
	public ArrayList<E> retrieveFirstResults(ArrayList<E> list, int maxResults) {
		ArrayList<E> newList = new ArrayList<E>();
		int count = Math.min(list.size(), maxResults);
		for (int i=0; i<count; i++) {
			newList.add(list.get(i));
		}
		return newList;
	}

}
