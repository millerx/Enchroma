package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class SelectPhotoViewTest extends ActivityUnitTestCase<MainActivity> {
	
	private SelectPhotoView _selectView;
	
	public SelectPhotoViewTest( ) {
		super( MainActivity.class );
	}

	protected void setUp( ) throws Exception {
		super.setUp();

		FileUtils.instance = new Shims.FileUtilsShim();
		ThumbnailBuilder.instance = new Shims.ThumbnailBuilderShim();
		PickUtils.instance = new Shims.PickUtilsShim();
		
		MainActivity a = (MainActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
		
		_selectView = (SelectPhotoView) a.findViewById( R.id.backgroundImg );
	}

	public void testOnClick( ) {
		_selectView.onClick( _selectView );

		// Did we start an image picker activity?
		Intent i = getStartedActivityIntent();
		assertEquals( Intent.ACTION_PICK, i.getAction() );
		assertEquals( "image/*", i.getType() );
		assertEquals( MainActivity.REQ_SELECTBACKGROUNDPHOTO, getStartedActivityRequest() );
		
		PhotoView.SavedState ss = (PhotoView.SavedState) _selectView.onSaveInstanceState();
		// ss.photoFile should not be set until activity returns.
		assertNull( ss.photoFile );
		// [Background picture chosen]
		
		_selectView.onRestoreInstanceState( ss );
		Intent ri = new Intent( "action", android.net.Uri.parse("data://picker") );
		_selectView.onActivityResult( Activity.RESULT_OK, ri );
		
		// Did we save the state?
		assertEquals( Shims.PickUtilsShim.PickedPhotoPath, ss.photoFile.toString() );
		
		_selectView.onActivityResume();
	}

	public void testCameraActivityCancelled( ) {
		_selectView.onClick( _selectView );
		
		PhotoView.SavedState ss = (PhotoView.SavedState) _selectView.onSaveInstanceState();
		// ss.photoFile should not be set until activity returns.
		assertNull( ss.photoFile );
		// [Background picture chosen]
		
		_selectView.onRestoreInstanceState( ss );
		_selectView.onActivityResult( Activity.RESULT_CANCELED, null );
		
		// photoFile should still be null because activity result was not OK.
		assertNull( ss.photoFile );
		
		_selectView.onActivityResume();
	}
	
}