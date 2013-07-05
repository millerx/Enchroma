package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	
	public MainActivityTest( ) {
		super( MainActivity.class );
	}

	protected void setUp( ) throws Exception {
		super.setUp();
		
		startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
	}
	
	public void testSmoke( )
	{
		assertNotNull( getActivity() );
	}
}