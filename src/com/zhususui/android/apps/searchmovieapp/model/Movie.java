package com.zhususui.android.apps.searchmovieapp.model;

import java.io.Serializable;
import java.util.ArrayList;

// Based on XML response, build up model class of movie
// to map the XML elements to Java classes
public class Movie implements Serializable {
	
	private static final long serialVersionUID = -6814886315783830255L;
	
	public String title;
	public String year;
	public String rated;
	public String released;
	public String runtime;
	public String genre;
	public String director;
	public String writer;	
	public String actors;
	public String plot;
	public String language;
	public String country;
	public String awards;
	public String poster;
	public String metascore;
	public String imdbRating;
	public String imdbVotes;
	public String imdbID;
	public String type;
	public ArrayList<Image> imagesList;
	
	public String retrieveCoverImage() {
		if (imagesList!=null && !imagesList.isEmpty()) {
			for (Image movieImage : imagesList) {
				if (movieImage.size.equalsIgnoreCase(Image.SIZE_COVER) &&
						movieImage.type.equalsIgnoreCase(Image.TYPE_POSTER)) {
					return movieImage.url;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie [name=");
		builder.append(title);
		builder.append("]");
		return builder.toString();
	}

}
