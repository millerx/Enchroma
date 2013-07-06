package com.sosobro.enchroma;

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
		_ss.photoFile = FileUtils.instance.createSubjectFilePath( _activity );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _ss.photoFile ) );
		_activity.startActivityForResult( i, _reqId );
	}

}
