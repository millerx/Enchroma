package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

// TODO: Optimize layout
//       http://stackoverflow.com/questions/5254562/is-there-a-simpler-better-way-to-put-a-border-outline-around-my-textview
// TODO: Thumbnail does not respect orientation.

public class MainActivity extends Activity {

	public static final int REQ_CAPTURESUBJECTPHOTO = 1;
	
	public static final String BN_SUBJECTFILE = "subjectFile";
	public static final String BN_SUBJECTTHUMB = "subjectThumb";
	
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

		if (state.containsKey( BN_SUBJECTFILE ))
			_subjectFile = new File( state.getString( BN_SUBJECTFILE ) );
		_subjectThumb = (Bitmap) state.getParcelable( BN_SUBJECTTHUMB );
		
		printState( "restoreInstanceState" );
	}

	private void printState( String header ) {
		System.out.println( header );
		System.out.printf( "subjectFile=%s\n", _subjectFile==null ? "" : _subjectFile.toString() );
		System.out.printf( "subjectBitmap=%d\n", _subjectThumb==null ? 0 : _subjectThumb.getByteCount() );
	}

	// TODO: Change back to protected.
	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
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
			state.putString( BN_SUBJECTFILE, _subjectFile.toString() );
		if (_subjectThumb != null)
			state.putParcelable( BN_SUBJECTTHUMB, _subjectThumb );
	}
	
	public void subjectImg_onClick( View v ) {
		_subjectFile = PhotoUtils.instance.createSubjectFilePath( this );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _subjectFile ) );
		startActivityForResult( i, REQ_CAPTURESUBJECTPHOTO );
	}

	private void onPictureTaken( ) {
		_subjectThumb = PhotoUtils.instance.createThumbnail( _subjectFile );
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