package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

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
		
		MainActivity a = (MainActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
		
		_selectView = (SelectPhotoView) a.findViewById( R.id.backgroundImg );
	}

	public void testSmoke( ) {
		assertNotNull( _selectView );
	}
	
}