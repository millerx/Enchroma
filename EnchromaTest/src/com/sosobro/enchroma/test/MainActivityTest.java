package com.sosobro.enchroma.test;

import java.io.File;

import com.sosobro.enchroma.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.test.ActivityUnitTestCase;

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
		assertEquals( "android.media.action.IMAGE_CAPTURE",
			getStartedActivityIntent().getAction() );
		assertEquals( 1, getStartedActivityRequest() );
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
	
}