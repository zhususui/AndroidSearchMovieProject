package com.zhususui.android.apps.searchmovieapp.services;

import java.util.ArrayList;

import android.util.Log;

import com.zhususui.android.apps.searchmovieapp.model.Movie;

// Detail service classes responsible for performing HTTP requests, parsing the XML responses, 
// & create the corresponding model objects and returning those to the calling Activity. 
public class MovieSeeker extends GenericSeeker<Movie> {
		
	private static final String MOVIE_SEARCH_PATH = "Movie.search/";
	
	public ArrayList<Movie> find(String query) {
		ArrayList<Movie> moviesList = retrieveMoviesList(query);
		return moviesList;
	}
	
	public ArrayList<Movie> find(String query, int maxResults) {
		ArrayList<Movie> moviesList = retrieveMoviesList(query);
		return retrieveFirstResults(moviesList, maxResults);
	}
	
	private ArrayList<Movie> retrieveMoviesList(String query) {
		String url = constructSearchUrl(query);
		String response = httpRetriever.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		return xmlParser.parseMoviesResponse(response);
	}

	@Override
	public String retrieveSearchMethodPath() {
		return MOVIE_SEARCH_PATH;
	}

}
