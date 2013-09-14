package com.sosobro.enchroma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

// TODO: Optimize layout
//       http://stackoverflow.com/questions/5254562/is-there-a-simpler-better-way-to-put-a-border-outline-around-my-textview

public class MainActivity extends Activity {

	public static final int REQ_CAPTURESUBJECTPHOTO = 1;
	public static final int REQ_SELECTBACKGROUNDPHOTO = 2;
	public static final int REQ_CHROMAKEY = 3;

	private PhotoView _subjectPV;
	private PhotoView _backgroundPV;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		_subjectPV = (PhotoView) findViewById( R.id.subjectImg );
		_subjectPV.init( this, REQ_CAPTURESUBJECTPHOTO );

		_backgroundPV = (PhotoView) findViewById( R.id.backgroundImg );
		_backgroundPV.init( this, REQ_SELECTBACKGROUNDPHOTO );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	@Override
	protected void onResume( ) {
		super.onResume();
		System.out.println("MainActivity resume");
		_subjectPV.onActivityResume();
		_backgroundPV.onActivityResume();
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.printf("onActivityResult req=%d res=%d\n", requestCode, resultCode);

		switch (requestCode)
		{
		case REQ_CAPTURESUBJECTPHOTO:
			_subjectPV.onActivityResult( resultCode, data );
			break;
		case REQ_SELECTBACKGROUNDPHOTO:
			_backgroundPV.onActivityResult( resultCode, data );
			break;
		}
	}

	public void engageBtn_onClick( View v ) {
		// Only chromakey if the subject and the background have been selected.
		String subjectFileName = _subjectPV.getPhotoFileName();
		String backgroundFileName = _backgroundPV.getPhotoFileName();
		if (subjectFileName != null && backgroundFileName != null)
		{
			Intent i = new Intent( this, ChromakeyActivity.class );
			i.putExtra( Common.EXTRA_SUBJECT_FN, subjectFileName );
			i.putExtra( Common.EXTRA_BACKGROUND_FN, backgroundFileName );
			startActivityForResult( i, REQ_CHROMAKEY );
		}
	}
}