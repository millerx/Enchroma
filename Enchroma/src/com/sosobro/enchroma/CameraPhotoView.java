package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;

public class CameraPhotoView extends PhotoView {

	public CameraPhotoView( Context context, AttributeSet attrs ) {
		super( context, attrs );
	}

	@Override
	public void onClick( View v ) {
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		File photoFile = FileUtils.instance.createSubjectFilePath( _activity );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( photoFile ) );
		_activity.startActivityForResult( i, _reqId );
	}

	@Override
	public void onActivityResult( int resultCode, Intent data ) {
		if (resultCode == Activity.RESULT_OK) {
			// If resultCode=OK then we should set _ss.photoFile and only if resultCode=OK.
			// TODO: Should PhotoView work in URIs instead of Files?
			_ss.photoFile = FileUtils.instance.createSubjectFilePath( _activity );
		}
		
		super.onActivityResult( resultCode, data );
	}
	
}
