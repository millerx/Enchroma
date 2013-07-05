package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

	private static int getOrientation( String filename ) {
		try {
			ExifInterface exif = new ExifInterface( filename.toString() );
			return exif.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL );
		}
		catch(Exception e){
			System.err.println( e.getMessage() );
		}
		
		return ExifInterface.ORIENTATION_NORMAL;
	}
	
	private static Matrix getOrientationMatrix( int orient ) {
		Matrix m = new Matrix();
		switch (orient) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			m.postRotate( 90 );
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			m.postRotate( 180 );
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			m.postRotate( 270 );
			break;
		}
		return m;
	}

	private static Bitmap rotateBitmap( Bitmap b, int orient ) {
		return Bitmap.createBitmap( b, 0, 0, b.getWidth(), b.getHeight(),
			getOrientationMatrix( orient ), false );
	}
	
	public Bitmap createThumbnail( File f ) {
		// TODO: Sub-sample for purposes of thumbnail?
		// TODO: BitmapFactory.Options.inSampleSize instead of creating thumbnail?
		Bitmap b = BitmapFactory.decodeFile( f.toString() );
		Bitmap thumb = createThumbnail( b );
		return rotateBitmap( thumb, getOrientation( f.toString() ) );
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
