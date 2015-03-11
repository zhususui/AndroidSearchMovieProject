package com.zhususui.android.apps.searchmovieapp.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zhususui.android.apps.searchmovieapp.io.FlushedInputStream;
import com.zhususui.android.apps.searchmovieapp.util.Utils;

//Perform all the HTTP requests and will return the responses
public class HttpRetriever {
	
	private DefaultHttpClient client = new DefaultHttpClient();	
	
	public String retrieve(String url) {
		
        HttpGet getRequest = new HttpGet(url);
        
		try {
			
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			// Retrieve the response status code 
			// & Compare it against the code for successful HTTP requests (HttpStatus.SC_OK)
			if (statusCode != HttpStatus.SC_OK) { 
	            Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
	            return null;
	        }
			
			// For successful requests
			// We take reference of the enclosed HttpEntity object 
			HttpEntity getResponseEntity = getResponse.getEntity();
			
			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}
			
		} 
		catch (IOException e) {
			getRequest.abort();
	        Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		
		return null;
		
	}
	
	public InputStream retrieveStream(String url) {
		
		HttpGet getRequest = new HttpGet(url);
        
		try {
			
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) { 
	            Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
	            return null;
	        }

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
			
		} 
		catch (IOException e) {
			getRequest.abort();
	        Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		
		return null;
		
	}
	
	// Help to download images from the internet
	public Bitmap retrieveBitmap(String url) throws Exception {
		
		InputStream inputStream = null;
        try {
            inputStream = this.retrieveStream(url);
            final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
            return bitmap;
        } 
        finally {
            Utils.closeStreamQuietly(inputStream);
        }
		
	}

}
