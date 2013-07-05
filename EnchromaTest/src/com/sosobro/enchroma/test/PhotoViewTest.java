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

	private class TestFileUtils extends FileUtils {
		
		public static final String SubjectFilePath = "/storage/sdcard0/Pictures/Enchroma/subject.jpg";
		
		@Override
		public File createSubjectFilePath( Activity a ) {
			return new File( SubjectFilePath );
		}

	}

	private class TestThumbnailBuilder extends ThumbnailBuilder {
		@Override
		public Bitmap createThumbnail( File f ) {
			return Bitmap.createBitmap( 8, 8, Bitmap.Config.RGB_565 );
		}
	}
	
	protected void setUp( ) throws Exception {
		super.setUp();
		
		FileUtils.instance = new TestFileUtils();
		ThumbnailBuilder.instance = new TestThumbnailBuilder();
		
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
		assertEquals( TestFileUtils.SubjectFilePath, ss.photoFile.toString() );
		
		// TODO: Test if path was created.
	}

	public void testCameraActivityReturns( ) {
		_photoView.onActivityResult( Activity.RESULT_OK, null );
		
		// Did we create the thumb and save it to the state?
		PhotoView.SavedState ss = (PhotoView.SavedState) _photoView.onSaveInstanceState();
		assertNotNull( ss.thumb );
		
		// When the state is restored we set the image.
		_photoView.onRestoreInstanceState( ss );
		assertEquals( 8, _photoView.getDrawable().getIntrinsicWidth() );
	}
	
	public void testSaveRestoreState( ) {
		Parcel p = Parcel.obtain();
		
		// Save
		PhotoView.SavedState ss = new PhotoView.SavedState();
		ss.photoFile = new File( TestFileUtils.SubjectFilePath );
		ss.thumb = ThumbnailBuilder.instance.createThumbnail( ss.photoFile );
		ss.writeToParcel( p, 0 );

		// Restore
		p.setDataPosition( 0 );
		ss = new PhotoView.SavedState( p );
		assertEquals( TestFileUtils.SubjectFilePath, ss.photoFile.toString() );
		assertNotNull( ss.thumb );
		
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
		assertNull( ss.thumb );
		
		p.recycle();
	}
}