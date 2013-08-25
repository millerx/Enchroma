package com.sosobro.enchroma.test;

import java.io.File;

import com.sosobro.enchroma.*;

import android.app.Activity;
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

	public void testCameraPhotoTaken( ) {
		_cameraView.onClick( _cameraView );
		
		// Did we start the camera activity specifying the file path?
		Intent i = getStartedActivityIntent();
		assertEquals( MediaStore.ACTION_IMAGE_CAPTURE, i.getAction() );
		assertEquals( MainActivity.REQ_CAPTURESUBJECTPHOTO, getStartedActivityRequest() );
		assertEquals( "file:///storage/sdcard0/Pictures/Enchroma/subject.jpg",
			i.getExtras().get( MediaStore.EXTRA_OUTPUT ).toString() );

		// TODO: Test if path was created.
		
		PhotoView.SavedState ss = (PhotoView.SavedState) _cameraView.onSaveInstanceState();
		// ss.photoFile should not be set until activity returns.
		assertNull( ss.photoFile );
		// [Photo taken]
		
		// We can't test starting camera activity and returning result on same test because we can't reset the View object.
		// But we can't put it in a separate test because creating a SavedState fails at runtime.
		_cameraView.onRestoreInstanceState( ss );
		_cameraView.onActivityResult( Activity.RESULT_OK, null );
		
		// Did we save the state?
		assertEquals( Shims.FileUtilsShim.SubjectFilePath, ss.photoFile.toString() );
		
		_cameraView.onActivityResume();
	}

	public void testCameraActivityCancelled( ) {
		_cameraView.onClick( _cameraView );
		
		PhotoView.SavedState ss = (PhotoView.SavedState) _cameraView.onSaveInstanceState();
		// ss.photoFile should not be set until activity returns.
		assertNull( ss.photoFile );
		// [Photo taken]
		
		_cameraView.onRestoreInstanceState( ss );
		_cameraView.onActivityResult( Activity.RESULT_CANCELED, null );
		
		// photoFile should still be null because activity result was not OK.
		assertNull( ss.photoFile );
		
		_cameraView.onActivityResume();
	}
	
}