package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class PhotoUtils {
	public File createSubjectFilePath( Activity a ) {
		// /storage/sdcard0/Pictures/Enchroma/subject.jpg
		
		File f = new File(
			Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
			a.getResources().getString( R.string.app_name ) );
		f.mkdir();
		
		return new File(
			f.toString(),
			"subject.jpg" );
	}

	public Bitmap createThumbnail( File f ) {
		// TODO: Sub-sample for purposes of thumbnail?
		Bitmap b = BitmapFactory.decodeFile( f.toString() );
		return createThumbnail( b );
	}
	
	public Bitmap createThumbnail( Bitmap b ) {
		// TODO: Scale to exact size?
		final int THUMBNAIL_HEIGHT = 336;
		//final int THUMBNAIL_WIDTH = 66;

		Float width  = Float.valueOf( b.getWidth() );
		Float height = Float.valueOf( b.getHeight() );
		Float ratio = width / height;
		int newWidth = (int) (THUMBNAIL_HEIGHT * ratio);
		
		return Bitmap.createScaledBitmap( b, newWidth, THUMBNAIL_HEIGHT, false );
	}
	
	public static PhotoUtils instance = new PhotoUtils();
}
