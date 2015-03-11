package com.zhususui.android.apps.searchmovieapp.services;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.zhususui.android.apps.searchmovieapp.handlers.MovieHandler;
import com.zhususui.android.apps.searchmovieapp.model.Movie;

// Choose SAX for XML parsers implementation
// Since the application need to leave a rather resource-constrained environment for mobile device.
// Create class XmlParser to use SAX approach in order to parse the XML responses
public class XmlParser {
	
	private XMLReader initializeReader() throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// create a parser
		SAXParser parser = factory.newSAXParser();
		// create the reader (scanner)
		XMLReader xmlreader = parser.getXMLReader();
		return xmlreader;
	}
	
	public ArrayList<Movie> parseMoviesResponse(String xml) {
		
		try {
			
			XMLReader xmlreader = initializeReader();
			
			MovieHandler movieHandler = new MovieHandler();

			// assign our handler
			xmlreader.setContentHandler(movieHandler);
			// perform the synchronous parse
			xmlreader.parse(new InputSource(new StringReader(xml)));
			
			return movieHandler.retrieveMoviesList();			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
