package com.zhususui.android.apps.searchmovieapp.handlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.zhususui.android.apps.searchmovieapp.model.Movie;

// Generate the handler for parsing the movies search responses (MovieHandler). 
public class MovieHandler extends DefaultHandler {
	
	private StringBuffer buffer = new StringBuffer();
	
	private ArrayList<Movie> moviesList;
	private Movie movie;
	
	// Called when a new element is found and initialize the appropriate field there
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		buffer.setLength(0);
		
		if (localName.equals("root")) {
			moviesList = new ArrayList<Movie>();
		}
		else if (localName.equals("movie")) {
			movie = new Movie();
			movie.title = atts.getValue("title");
			movie.year = atts.getValue("year");
			movie.rated= atts.getValue("rated");
			movie.released= atts.getValue("released");
			movie.runtime= atts.getValue("runtime");
			movie.genre= atts.getValue("genre");
			movie.director= atts.getValue("director");
			movie.writer= atts.getValue("writer");
			movie.actors= atts.getValue("actors");
			movie.plot= atts.getValue("plot");
			movie.language= atts.getValue("language");
			movie.country= atts.getValue("country");
			movie.awards= atts.getValue("awards");
			movie.poster= atts.getValue("poster");
			movie.metascore= atts.getValue("metascore");
			movie.imdbRating= atts.getValue("imdbRating");
			movie.imdbVotes= atts.getValue("imdbVotes");
			movie.imdbID= atts.getValue("imdbID");
			movie.type= atts.getValue("type");

		}
	
	}
	
	// Called when the element’s end has been reached. 
	// The corresponding field gets populated there.
	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException {
		
		if (localName.equals("movie")) {
			moviesList.add(movie);
			
		}
		
	}
	
	// Called when new text has been found inside an element. 
	@Override
	public void characters(char[] ch, int start, int length) {
		buffer.append(ch, start, length);
	}
		
	public ArrayList<Movie> retrieveMoviesList() {
		return moviesList;
	}

}
