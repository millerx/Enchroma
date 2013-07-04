package com.sosobro.enchroma;

// Next:
// - Wire up save/restore instance.
// - Continue moving code over.
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class PhotoView extends ImageView
	implements View.OnClickListener {

	private Activity _activity;
	private int _reqId;
	// TODO: Remove reference to subject.
	private File _subjectFile;
	private Bitmap _subjectThumb;
	
	public PhotoView( Context context, AttributeSet attrs ) {
		super( context, attrs );
		setOnClickListener( this );
	}

	public void init( Activity a, int reqId ) {
		_activity = a;
		_reqId = reqId;
	}
	
	@Override
	public void onClick( View v ) {
		// TODO: Refactor into this class.
		_subjectFile = PhotoUtils.instance.createSubjectFilePath( _activity );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _subjectFile ) );
		_activity.startActivityForResult( i, _reqId );
	}

	public void onActivityResult( int resultCode, Intent data ) {
		// TODO: Move Main.onPictureTaken
	}
}
