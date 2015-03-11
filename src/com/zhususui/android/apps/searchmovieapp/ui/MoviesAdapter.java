package com.zhususui.android.apps.searchmovieapp.ui;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhususui.android.apps.searchmovieapp.R;
import com.zhususui.android.apps.searchmovieapp.io.FlushedInputStream;
import com.zhususui.android.apps.searchmovieapp.model.Movie;
import com.zhususui.android.apps.searchmovieapp.services.HttpRetriever;

// Create a custom layout which include aforementioned data for each of the movies
public class MoviesAdapter extends ArrayAdapter<Movie> {
	
	private HttpRetriever httpRetriever = new HttpRetriever();
	
	private ArrayList<Movie> movieDataItems;
	
	private Activity context;
	
	public MoviesAdapter(Activity context, int textViewResourceId, ArrayList<Movie> movieDataItems) {
        super(context, textViewResourceId, movieDataItems);
        this.context = context;
        this.movieDataItems = movieDataItems;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
		View view = convertView;
		if (view == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.movie_data_row, null);
		}
		
		Movie movie = movieDataItems.get(position);
		
		if (movie != null) {
			
			// title
			TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
			nameTextView.setText(movie.title);
			
			// rating
			TextView ratingTextView = (TextView) view.findViewById(R.id.rating_text_view);
			ratingTextView.setText("Rating: " + movie.imdbRating);
			
			// released
			TextView releasedTextView = (TextView) view.findViewById(R.id.released_text_view);
			releasedTextView.setText("Release Date: " + movie.released);
			
			// awards
			TextView certificationTextView = (TextView) view.findViewById(R.id.certification_text_view);
			certificationTextView.setText("Certification: " + movie.awards);
			
			// language
			TextView languageTextView = (TextView) view.findViewById(R.id.language_text_view);
			languageTextView.setText("Language: " + movie.language);
			
			// thumb image
			ImageView imageView = (ImageView) view.findViewById(R.id.movie_thumb_icon);
			String url = movie.poster;
			
			if (url!=null) {
				Bitmap bitmap = fetchBitmapFromCache(url);
				if (bitmap==null) {				
					new BitmapDownloaderTask(imageView).execute(url);
				}
				else {
					imageView.setImageBitmap(bitmap);
				}
			}
			else {
				imageView.setImageBitmap(null);
			}
			
		}
		
		return view;
		
	}
	
	private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();
	
	private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }
	
	private Bitmap fetchBitmapFromCache(String url) {
		
		synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        return null;
        
	}
	
	private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		
        private String url;
		private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            InputStream is = httpRetriever.retrieveStream(url);
			if (is==null) {
				return null;
			}
			return BitmapFactory.decodeStream(new FlushedInputStream(is));
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {        	
            if (isCancelled()) {
                bitmap = null;
            }
            
            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
	
}
