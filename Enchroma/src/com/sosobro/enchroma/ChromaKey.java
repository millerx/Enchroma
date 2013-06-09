package com.sosobro.enchroma;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ChromaKey {
	
	// Perform a chroma key on the green-screen'ed foreground image.  Replaces the green-screen
	// with the background image.  Returns a new bitmap with the composite.
	public static Bitmap key( Bitmap foreBmp, Bitmap backBmp ) throws Exception {
		long tstart = java.lang.System.currentTimeMillis();
		
		// Images must be the same size.
		if (foreBmp.getWidth() != backBmp.getWidth() ||
			foreBmp.getHeight() != backBmp.getHeight())
			throw new Exception();
		
		// We want the color depth of the foreground image since that is the image we just took
		// with our camera. 
		Bitmap outputBmp = Bitmap.createBitmap( foreBmp.getWidth(), foreBmp.getHeight(), foreBmp.getConfig() );

		int[] pixels = new int[foreBmp.getWidth() * foreBmp.getHeight()];
		foreBmp.getPixels( pixels, 0, foreBmp.getWidth(), 0, 0, foreBmp.getWidth(), foreBmp.getHeight() );
		int[] backPixels = new int[backBmp.getWidth() * backBmp.getHeight()];
		backBmp.getPixels( backPixels, 0, backBmp.getWidth(), 0, 0, backBmp.getWidth(), backBmp.getHeight() );
		int[] newPixels = new int[foreBmp.getWidth() * foreBmp.getHeight()];
		
		// If the pixel is green then we replace it with the background color.
		float[] hsv = new float[3];
		for (int y = 0; y < foreBmp.getHeight(); ++y) {
			for (int x = 0; x < foreBmp.getWidth(); ++x) {
				int p = pixels[y * foreBmp.getWidth() + x];
				Color.colorToHSV( p, hsv );
				if (hsv[0] > 60 && hsv[0] < 180)
					p = backPixels[y * backBmp.getWidth() + x];

				newPixels[y * foreBmp.getWidth() + x] = p;
			}
		}
		
		outputBmp.setPixels( newPixels, 0, foreBmp.getWidth(), 0, 0, foreBmp.getWidth(), foreBmp.getHeight() );
		
		long tstop = java.lang.System.currentTimeMillis();
		long ttime = tstop - tstart;
		System.out.printf( "ChromaKey.key time %d", ttime );
		
		return outputBmp;
	}
	
}