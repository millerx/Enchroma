package com.sosobro.enchroma;

import java.io.FileOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class ChromakeyActivity extends Activity {

	Bitmap _outputBmp;
	
	@Override
	protected void onCreate( Bundle state ) {
		super.onCreate( state );
		setContentView( R.layout.activity_chromakey );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.chromakey, menu );
		return true;
	}

	@Override
	protected void onResume( ) {
		super.onResume();
		System.out.println("ChromakeyActivity resume");
		
		String subjectFn = "";
		String backgroundFn = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			subjectFn = extras.getString( Common.EXTRA_SUBJECT_FN );
			backgroundFn = extras.getString( Common.EXTRA_BACKGROUND_FN );
		}

		System.out.println("resume subject=" + subjectFn);
		System.out.println("resume background=" + backgroundFn);

		// TODO: Reduce memory as much as possible.
		// TODO: Convert to ints then dispose bitmap.
		// TODO: Use as few buffers as possible.
		
		// 3264 x 2448
		// 816 x 612
		Bitmap foreBmp = decodeSampledBitmapFromFile( subjectFn, 1632, 1224 );
		Bitmap backBmp = decodeSampledBitmapFromFile( backgroundFn, 1632, 1224 );
		
		try {
			_outputBmp = ChromaKey.key( foreBmp, backBmp );
			backBmp.recycle();
			foreBmp.recycle();
			displayBitmap( _outputBmp );
		}
		catch (Exception ex) {
			System.out.println( ex.getMessage() );
		}
	}

  public Bitmap decodeSampledBitmapFromFile(String fileName, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(fileName, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(fileName, options);
	}

	public static int calculateInSampleSize(
		BitmapFactory.Options options, int reqWidth, int reqHeight) {
		
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	        if (width > height) {
	            inSampleSize = Math.round((float) height / (float) reqHeight);
	        } else {
	            inSampleSize = Math.round((float) width / (float) reqWidth);
	        }
	    }
	    return inSampleSize;
	}
		
	private void displayBitmap( Bitmap bmp ) {
		ImageView iv = (ImageView) this.findViewById( R.id.imageView1 );
		iv.setImageBitmap( bmp );
	}
	
	public void imageView_onClick( View v ) {
		if (_outputBmp == null)
			return;
		
		String filePath = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + "/output.png";
		saveBitmap( _outputBmp, filePath );
		Uri uri = Uri.parse( filePath );

		// Use with Email app, not Gmail app.
		Intent i = new Intent( Intent.ACTION_SEND );
		i.setType( "image/png" );
		i.putExtra( Intent.EXTRA_EMAIL, new String[]{ "millerx@gmail.com" } );
		i.putExtra( Intent.EXTRA_SUBJECT, "ChromaKey Test Image" );
		//i.putExtra( Intent.EXTRA_TEXT, "This is a chroma key test image" );
		i.putExtra( Intent.EXTRA_STREAM, uri );
		Intent ic = Intent.createChooser( i, "Send Chroma-Key" );
		startActivity( ic );
		
		finish();
	}

	private void saveBitmap( Bitmap bmp, String filename ) {
		try
		{
			FileOutputStream fs = new FileOutputStream( filename );
			bmp.compress( CompressFormat.PNG, 100, fs );
			fs.close();
		}
		catch (Exception ex)
		{
			System.out.println( ex.getMessage() );
			return;
		}
	}
}