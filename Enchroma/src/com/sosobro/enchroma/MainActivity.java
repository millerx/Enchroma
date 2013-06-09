package com.sosobro.enchroma;

import java.io.File;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

// TODO: Optimize layout
// TODO: http://stackoverflow.com/questions/5254562/is-there-a-simpler-better-way-to-put-a-border-outline-around-my-textview

public class MainActivity extends Activity {

	private File _subjectFile;
	private File _backgroundFile;
	
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

		// TODO: Load state
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.printf("onActivityResult req=%d res=%d\n", requestCode, resultCode);
		
		if (requestCode == Common.REQ_CAPTURESUBJECTPHOTO && resultCode == RESULT_OK) {
			onPictureTaken();
		}
		else if (requestCode == Common.REQ_SELECTBACKGROUND && resultCode == RESULT_OK) {
			onBackgroundSelected();
		}
	}
	
	public void subjectImg_onClick( View v ) {
		// TODO: Take picture
	}
	
	private void onPictureTaken( ) {
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