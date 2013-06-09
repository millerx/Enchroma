package com.sosobro.enchroma;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;

public class SelectBgActivity extends ExternalActivity {

	Uri _uri;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_select_bg );
	}

	@Override
	protected void onExternalStart( ) {
		super.onExternalStart();
		System.out.println("EN SBA externalStart");
		
		Intent i = new Intent( Intent.ACTION_PICK );
		i.setType("image/*");
	    startActivityForResult( i, Common.REQ_EXTERNAL );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.println("EN SBA activityResult rc="+resultCode);
		
		if (resultCode != RESULT_OK)
			return;
		
		_uri = data.getData();
		System.out.println("EN SBA uri=" + _uri);
	}
	
	@Override
	protected void onExternalReturn( ) {
		super.onExternalReturn();
		System.out.println("EN SBA externalResume");

		String backgroundFn = "";
        if (_uri != null) {
	        String[] columns = {MediaStore.Images.Media.DATA};
	
	        Cursor cursor = getContentResolver().query(_uri, columns, null, null, null);
	        cursor.moveToFirst();
	
	        backgroundFn = cursor.getString(cursor.getColumnIndex(columns[0]));
	        cursor.close();
        }
		
		String subjectFn = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			subjectFn = extras.getString( Common.EXTRA_SUBJECT_FN );
		}

		Intent i = new Intent( this, ChromakeyActivity.class );
		i.putExtra( Common.EXTRA_SUBJECT_FN, subjectFn );
		i.putExtra( Common.EXTRA_BACKGROUND_FN, backgroundFn );
		startActivityForResult( i, Common.REQ_NEXTACTIVITY );
	}
}