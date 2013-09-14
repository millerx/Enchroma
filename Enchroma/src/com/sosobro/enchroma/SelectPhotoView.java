package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
			_ss.photoFile = new File( PickUtils.instance.queryFilename( _activity, data.getData() ) );
		}
		
		super.onActivityResult( resultCode, data );
	}
}
