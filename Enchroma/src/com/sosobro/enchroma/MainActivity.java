package com.sosobro.enchroma;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

// TODO: Optimize layout
// TODO: http://stackoverflow.com/questions/5254562/is-there-a-simpler-better-way-to-put-a-border-outline-around-my-textview

public class MainActivity extends Activity {

	private static final int REQ_CAPTURESUBJECTPHOTO = 1;
	
	public static final String BUNDLE_SUBJECTFILE = "subjectFile";
	public static final String BUNDLE_SUBJECTTHUMB = "subjectThumb";
	
	private File _subjectFile;
	private Bitmap _subjectThumb;
	//private File _backgroundFile;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	@Override
	protected void onRestoreInstanceState( Bundle state ) {
		super.onRestoreInstanceState( state );

		if (state.containsKey( BUNDLE_SUBJECTFILE ))
			_subjectFile = new File( state.getString( BUNDLE_SUBJECTFILE ) );
		_subjectThumb = (Bitmap) state.getParcelable( BUNDLE_SUBJECTTHUMB );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.printf("onActivityResult req=%d res=%d\n", requestCode, resultCode);
		
		if (requestCode == REQ_CAPTURESUBJECTPHOTO && resultCode == RESULT_OK) {
			onPictureTaken();
		}
		else if (requestCode == Common.REQ_SELECTBACKGROUND && resultCode == RESULT_OK) {
			onBackgroundSelected();
		}
	}

	@Override
	protected void onResume( ) {
		super.onResume();

		if (_subjectThumb != null) {
			ImageView iv = (ImageView) findViewById( R.id.subjectImg );
			iv.setImageBitmap( _subjectThumb );
		}
	}

	@Override
	protected void onSaveInstanceState( Bundle state ) {
		super.onSaveInstanceState( state );

		if (_subjectFile != null)
			state.putString( BUNDLE_SUBJECTFILE, _subjectFile.toString() );
		if (_subjectThumb != null)
			state.putParcelable( BUNDLE_SUBJECTTHUMB, _subjectThumb );
	}
	
	public void subjectImg_onClick( View v ) {
		TakeSubjectPhoto();
	}
	
	private void TakeSubjectPhoto( ) {
		_subjectFile = createSubjectFile();
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _subjectFile ) );
		startActivityForResult( i, REQ_CAPTURESUBJECTPHOTO );
	}

	private File createSubjectFile( ) {
		// /storage/sdcard0/Pictures/Enchroma/subject.jpg
		
		File f = new File(
			Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
			getResources().getString( R.string.app_name ) );
		// TODO: Is this necessary?
		// TODO: Fake
		f.mkdir();
		
		return new File(
			f.toString(),
			"subject.jpg" );
	}
	
	private void onPictureTaken( ) {
		// TODO: Load _subjectFile from state
		_subjectFile = createSubjectFile();
		
		Bitmap subjectBmp = BitmapFactory.decodeFile( _subjectFile.toString() );
		_subjectThumb = createThumbnail( subjectBmp );
	}
	
	private Bitmap createThumbnail( Bitmap b ) {
		// TODO: Scale to exact size?
		final int THUMBNAIL_HEIGHT = 336;
		//final int THUMBNAIL_WIDTH = 66;

		Float width  = Float.valueOf( b.getWidth() );
		Float height = Float.valueOf( b.getHeight() );
		Float ratio = width / height;
		int newWidth = (int) (THUMBNAIL_HEIGHT * ratio);
		
		return Bitmap.createScaledBitmap( b, newWidth, THUMBNAIL_HEIGHT, false );
	}
	
	public void backgroundImg_onClick( View v ) {
		// TODO: Pick bg image
	}

	private void onBackgroundSelected( ) {
	}
	
	public void engageBtn_onClick( View v ) {
		// TODO: Enage
	}
}