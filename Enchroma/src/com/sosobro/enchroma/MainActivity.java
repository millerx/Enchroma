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
		
		printState( "restoreInstanceState" );
	}

	private void printState( String header ) {
		System.out.println( header );
		System.out.printf( "subjectFile=%s\n", _subjectFile==null ? "" : _subjectFile.toString() );
		System.out.printf( "subjectBitmap=%d\n", _subjectThumb==null ? 0 : _subjectThumb.getByteCount() );
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

		printState( "saveInstanceState" );
		
		if (_subjectFile != null)
			state.putString( BUNDLE_SUBJECTFILE, _subjectFile.toString() );
		if (_subjectThumb != null)
			state.putParcelable( BUNDLE_SUBJECTTHUMB, _subjectThumb );
	}
	
	public void subjectImg_onClick( View v ) {
		_subjectFile = PhotoUtils.createSubjectFilePath( this );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _subjectFile ) );
		startActivityForResult( i, REQ_CAPTURESUBJECTPHOTO );
	}

	private void onPictureTaken( ) {
		Bitmap subjectBmp = BitmapFactory.decodeFile( _subjectFile.toString() );
		_subjectThumb = PhotoUtils.createThumbnail( subjectBmp );
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