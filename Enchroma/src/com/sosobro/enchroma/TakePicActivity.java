package com.sosobro.enchroma;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;

public class TakePicActivity extends ExternalActivity {

	private File _file;
	
	@Override
	protected void onCreate( Bundle state ) {
		super.onCreate( state );
		setContentView( R.layout.activity_take_pic );
		
		_file = createPhotoFile();

		System.out.println("EN TPA create file="+_file);
	}

	@Override
	protected void onExternalStart( ) {
		super.onExternalStart();
		System.out.println("EN TPA externalStart");
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(_file) );
		startActivityForResult( i, Common.REQ_EXTERNAL );
	}

	@Override
	protected void onExternalReturn( ) {
		super.onExternalReturn();
		System.out.println("EN TPA externalResume");

		Intent i = new Intent( this, CropActivity.class );
		i.putExtra( Common.EXTRA_SUBJECT_FN, _file.toString() );
		startActivityForResult( i, Common.REQ_NEXTACTIVITY );
	}

	private File createPhotoFile( ) {
		// /storage/sdcard0/Pictures/Enchroma/photo.jpg
		
		File f = new File(
			Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
			getResources().getString( R.string.app_name ) );
		f.mkdir();
		
		return new File(
			f.toString(),
			"photo.jpg" );
	}
	
}