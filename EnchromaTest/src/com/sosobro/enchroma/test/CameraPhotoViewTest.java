package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.provider.MediaStore;
import android.test.ActivityUnitTestCase;

public class CameraPhotoViewTest extends ActivityUnitTestCase<MainActivity> {
	
	private CameraPhotoView _cameraView;
	
	public CameraPhotoViewTest( ) {
		super( MainActivity.class );
	}

	protected void setUp( ) throws Exception {
		super.setUp();

		FileUtils.instance = new Shims.FileUtilsShim();
		ThumbnailBuilder.instance = new Shims.ThumbnailBuilderShim();
		
		MainActivity a = (MainActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
		
		_cameraView = (CameraPhotoView) a.findViewById( R.id.subjectImg );
	}

	public void testOnClick( ) {
		_cameraView.onClick( _cameraView );
		
		// Did we start the camera activity specifying the file path?
		Intent i = getStartedActivityIntent();
		assertEquals( MediaStore.ACTION_IMAGE_CAPTURE, i.getAction() );
		assertEquals( MainActivity.REQ_CAPTURESUBJECTPHOTO, getStartedActivityRequest() );
		assertEquals( "file:///storage/sdcard0/Pictures/Enchroma/subject.jpg",
			i.getExtras().get( MediaStore.EXTRA_OUTPUT ).toString() );

		// Did we save the state?
		PhotoView.SavedState ss = (PhotoView.SavedState) _cameraView.onSaveInstanceState();
		assertEquals( Shims.FileUtilsShim.SubjectFilePath, ss.photoFile.toString() );
		
		// TODO: Test if path was created.
	}

}