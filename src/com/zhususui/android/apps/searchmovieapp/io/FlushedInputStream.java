package com.zhususui.android.apps.searchmovieapp.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

// The decodeStream method may cause problems when downloading an image over a slow connection. 
// The custom class FlushedInputStream extends FilterInputStream is to fix the problem. 
public class FlushedInputStream extends FilterInputStream {
	
    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public long skip(long n) throws IOException {
        long totalBytesSkipped = 0L;
        while (totalBytesSkipped < n) {
            long bytesSkipped = in.skip(n - totalBytesSkipped);
            if (bytesSkipped == 0L) {
                  int b = read();
                  if (b < 0) {
                      break;  // we reached EOF
                  } else {
                      bytesSkipped = 1; // we read one byte
                  }
           }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
    
}