package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
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

	public void testSmoke( ) {
		assertNotNull( _cameraView );
	}
	
}