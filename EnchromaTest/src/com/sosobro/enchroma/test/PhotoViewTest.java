package com.sosobro.enchroma.test;

import java.io.File;

import com.sosobro.enchroma.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.provider.MediaStore;
import android.test.ActivityUnitTestCase;

public class PhotoViewTest extends ActivityUnitTestCase<MainActivity> {
	
	private PhotoView _photoView;
	
	public PhotoViewTest( ) {
		super( MainActivity.class );
	}

	private class TestPhotoUtils extends PhotoUtils {
		
		public static final String SubjectFilePath = "/storage/sdcard0/Pictures/Enchroma/subject.jpg";
		
		@Override
		public File createSubjectFilePath( Activity a ) {
			return new File( SubjectFilePath );
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
		MainActivity a = (MainActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
		_photoView = (PhotoView) a.findViewById( R.id.subjectImg );
	}

	public void testOnClick( ) {
		_photoView.onClick( _photoView );
		
		// Did we start the camera activity specifying the file path?
		Intent i = getStartedActivityIntent();
		assertEquals( MediaStore.ACTION_IMAGE_CAPTURE, i.getAction() );
		assertEquals( MainActivity.REQ_CAPTURESUBJECTPHOTO, getStartedActivityRequest() );
		assertEquals( "file:///storage/sdcard0/Pictures/Enchroma/subject.jpg",
			i.getExtras().get( MediaStore.EXTRA_OUTPUT ).toString() );

		// Did we save the state?
		PhotoView.SavedState ss = (PhotoView.SavedState) _photoView.onSaveInstanceState();
		assertEquals( TestPhotoUtils.SubjectFilePath, ss.photoFile.toString() );
		
		// TODO: Test if path was created.
	}
	
	public void testSaveRestoreState( ) {
		Parcel p = Parcel.obtain();
		
		// Save
		PhotoView.SavedState ss = new PhotoView.SavedState();
		ss.photoFile = new File( TestPhotoUtils.SubjectFilePath );
		ss.writeToParcel( p, 0 );

		// Restore
		p.setDataPosition( 0 );
		ss = new PhotoView.SavedState( p );
		assertEquals( TestPhotoUtils.SubjectFilePath, ss.photoFile.toString() );
		
		p.recycle();
	}
	
	public void testSaveRestoreEmptyState( ) {
		Parcel p = Parcel.obtain();
		
		// Save
		PhotoView.SavedState ss = new PhotoView.SavedState();
		ss.writeToParcel( p, 0 );

		// Restore
		p.setDataPosition( 0 );
		ss = new PhotoView.SavedState( p );
		assertNull( ss.photoFile );
		
		p.recycle();
	}
}