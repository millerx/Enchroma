package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;

public class SelectPhotoView extends PhotoView {

	public SelectPhotoView( Context context, AttributeSet attrs ) {
		super( context, attrs );
	}

	@Override
	public void onClick( View v ) {
		Intent i = new Intent( Intent.ACTION_PICK );
		i.setType( "image/*" );
		_activity.startActivityForResult( i, _reqId );
	}
	
	@Override
	public void onActivityResult( int resultCode, Intent data ) {
		if (resultCode == Activity.RESULT_OK) {
			// Set the filename before the base class creates the thumbnail.
			// TODO: Should PhotoView work in URIs instead of Files?
			_ss.photoFile = new File( queryFilename( data.getData() ) );
		}
		
		super.onActivityResult( resultCode, data );
	}

	private String queryFilename( android.net.Uri uri) {
        String[] columns = { MediaStore.Images.Media.DATA };
    	
        Cursor cursor = _activity.getContentResolver().query( uri, columns, null, null, null );
        cursor.moveToFirst();

        String filename = cursor.getString( cursor.getColumnIndex( columns[0] ) );
        cursor.close();
        
        return filename;
	}
}
