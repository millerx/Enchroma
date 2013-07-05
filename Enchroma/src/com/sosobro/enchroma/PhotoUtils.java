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
	
	private static Bitmap rotateBitmap( Bitmap b, int orient ) {
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
		
		return Bitmap.createBitmap( b, 0, 0, b.getWidth(), b.getHeight(), m, false );
	}
	
	public Bitmap createThumbnail( File f ) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap thumb = BitmapFactory.decodeFile( f.toString(), options );
		return rotateBitmap( thumb, getOrientation( f.toString() ) );
	}
	
	public static PhotoUtils instance = new PhotoUtils();
}
