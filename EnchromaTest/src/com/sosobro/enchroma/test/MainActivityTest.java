package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;
import com.sosobro.enchroma.test.Shims.FileUtilsShim;
import com.sosobro.enchroma.test.Shims.PickUtilsShim;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

	private CameraPhotoView _subjectView;
	private SelectPhotoView _backgroundView;
	
	public MainActivityTest( ) {
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
		
		_subjectView = (CameraPhotoView) a.findViewById( R.id.subjectImg );
		_backgroundView = (SelectPhotoView) a.findViewById( R.id.backgroundImg );
	}
	
	public void testEngageClick( )
	{
		// We need to set both photos to enable the Engage button.
		setSubjectPhoto();
		setBackgroundPhoto();
		
		MainActivity a = (MainActivity) getActivity();
		a.engageBtn_onClick( null );
		
		Intent i = getStartedActivityIntent();
		assertEquals( "com.sosobro.enchroma.ChromakeyActivity",
			i.getComponent().getClassName() );
		assertEquals( FileUtilsShim.SubjectFilePath, i.getExtras().get( Common.EXTRA_SUBJECT_FN ).toString() );
		assertEquals( PickUtilsShim.PickedPhotoPath, i.getExtras().get( Common.EXTRA_BACKGROUND_FN ).toString() );
	}

	// Tests that the Engage button is disabled if no photos have been selected.
	public void testEngageDisabledIfNoPhotos( ) {
		//setSubjectPhoto();
		//setBackgroundPhoto();
		
		MainActivity a = (MainActivity) getActivity();
		a.engageBtn_onClick( null );
		
		assertTrue( MainActivity.REQ_CHROMAKEY != this.getStartedActivityRequest() );
	}

	// Tests that the Engage button is disabled if only the subject photo has been taken.
	public void testEngageDisabledIfSubjectOnly( ) {
		setSubjectPhoto();
		//setBackgroundPhoto();
		
		MainActivity a = (MainActivity) getActivity();
		a.engageBtn_onClick( null );
		
		assertTrue( MainActivity.REQ_CHROMAKEY != this.getStartedActivityRequest() );
	}
	
	// Tests that the Engage button is disabled if only the background photo has been selected.
	public void testEngageDisabledIfBackgroundOnly( ) {
		//setSubjectPhoto();
		setBackgroundPhoto();
		
		MainActivity a = (MainActivity) getActivity();
		a.engageBtn_onClick( null );
		
		assertTrue( MainActivity.REQ_CHROMAKEY != this.getStartedActivityRequest() );
	}

	private void setSubjectPhoto( ) {
		_subjectView.onClick( _subjectView );
		_subjectView.onActivityResult( Activity.RESULT_OK, null );
	}

	private void setBackgroundPhoto( ) {
		// TODO: Better sim once SelectPhotoView tests are written.
//		PhotoView.SavedState ss = (PhotoView.SavedState) _backgroundView.onSaveInstanceState();
//		ss.photoFile = new File( "/storage/sdcard0/Download/PlanetFlaire.jpg" );
//		_backgroundView.onRestoreInstanceState( ss );
		_backgroundView.onClick( _subjectView );
		Intent ri = new Intent( "action", android.net.Uri.parse("data://picker") );
		_backgroundView.onActivityResult( Activity.RESULT_OK, ri );
	}
	
}