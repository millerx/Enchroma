package com.sosobro.enchroma;

import java.io.File;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;

public class CropActivity extends StackActivity {

	// TODO: Crop
	
	@Override
	protected void onCreate( Bundle state ) {
		super.onCreate( state );
		setContentView( R.layout.activity_crop );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.crop, menu );
		return true;
	}

	@Override
	protected void onStackResume( ) {
		super.onStackResume();

		System.out.println("EN CpA");

		String subjectFn = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			subjectFn = extras.getString( Common.EXTRA_SUBJECT_FN );
			
			// Fake work
			if (subjectFn != null) {
				File f = new File(subjectFn);
				System.out.println("EN CpA fileSize: " + f.length());
			}
		}

		Intent i = new Intent( this, SelectBgActivity.class );
		i.putExtra( Common.EXTRA_SUBJECT_FN, subjectFn );
		startActivityForResult( i, Common.REQ_NEXTACTIVITY );
	}
}