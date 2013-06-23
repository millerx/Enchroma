package com.sosobro.enchroma.test;

import java.io.File;

import com.sosobro.enchroma.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.test.ActivityUnitTestCase;
import android.widget.ImageView;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	
	public MainActivityTest( ) {
		super( MainActivity.class );
	}

	private class TestPhotoUtils extends PhotoUtils {
		
		@Override
		public File createSubjectFilePath( Activity a ) {
			return new File( "/storage/sdcard0/Pictures/Enchroma/subject.jpg" );
		}

		@Override
		public Bitmap createThumbnail( Bitmap b ) {
			return b;
		}
		
		@Override
		public Bitmap createThumbnail( File f ) {
			return Bitmap.createBitmap( 8, 8, Bitmap.Config.RGB_565 );
		}
	}
	
	protected void setUp( ) throws Exception {
		super.setUp();
		
		PhotoUtils.instance = new TestPhotoUtils();
		
		startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
	}

	// TODO: Test load and save state with an empty bundle.
	
	public void testSubjectImg_Clicked( ) {
		MainActivity a = getActivity();
		
		a.subjectImg_onClick( null );

		// Did we start the camera activity specifying the file path?
		Intent i = getStartedActivityIntent();
		assertEquals( MediaStore.ACTION_IMAGE_CAPTURE,
			getStartedActivityIntent().getAction() );
		assertEquals( MainActivity.REQ_CAPTURESUBJECTPHOTO, getStartedActivityRequest() );
		assertEquals( "file:///storage/sdcard0/Pictures/Enchroma/subject.jpg",
			i.getExtras().get( MediaStore.EXTRA_OUTPUT ).toString() );

		// When the onClick method completes the activity is paused and the camera activity starts.
		// We should have saved our current state.
		Bundle outState = new Bundle();
		getInstrumentation().callActivityOnSaveInstanceState( a, outState );
		assertEquals( "/storage/sdcard0/Pictures/Enchroma/subject.jpg",
			outState.getString( "subjectFile" ) );
		
		// TODO: Test if path was created.
	}

	public void testCameraActivityReturns( ) {
		MainActivity a = getActivity();
		
		// TODO: Combine both subject tests and use a monitor.
		//       http://stackoverflow.com/questions/13042015/testing-onactivityresult
		
		// Restore state from when subjectImg was clicked.
		Bundle inState = new Bundle();
		inState.putString( MainActivity.BN_SUBJECTFILE, "/storage/sdcard0/Pictures/Enchroma/subject.jpg" );
		getInstrumentation().callActivityOnRestoreInstanceState( a, inState );

		// Intent from camera activity with a filepath is null.
		a.onActivityResult( MainActivity.REQ_CAPTURESUBJECTPHOTO, Activity.RESULT_OK, null );
		
		getInstrumentation().callActivityOnResume( a );

		// For our tests the thumb is an 8x8 bitmap.
		ImageView iv = (ImageView) a.findViewById( com.sosobro.enchroma.R.id.subjectImg );
		assertEquals( 8, iv.getDrawable().getIntrinsicWidth() );
	}
}