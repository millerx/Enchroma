package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;

public class PhotoUtils {
	public static File createSubjectFilePath( Activity a ) {
		// /storage/sdcard0/Pictures/Enchroma/subject.jpg
		
		File f = new File(
			Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
			a.getResources().getString( R.string.app_name ) );
		f.mkdir();
		
		return new File(
			f.toString(),
			"subject.jpg" );
	}
	
	public static Bitmap createThumbnail( Bitmap b ) {
		// TODO: Scale to exact size?
		final int THUMBNAIL_HEIGHT = 336;
		//final int THUMBNAIL_WIDTH = 66;

		Float width  = Float.valueOf( b.getWidth() );
		Float height = Float.valueOf( b.getHeight() );
		Float ratio = width / height;
		int newWidth = (int) (THUMBNAIL_HEIGHT * ratio);
		
		return Bitmap.createScaledBitmap( b, newWidth, THUMBNAIL_HEIGHT, false );
	}
}
