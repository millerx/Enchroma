package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class SplashActivityTest extends ActivityUnitTestCase<SplashActivity> {
	public SplashActivityTest( ) {
		super( SplashActivity.class );
	}

	protected void setUp( ) throws Exception {
		super.setUp();
		startActivity(
			new Intent( getInstrumentation().getTargetContext(), SplashActivity.class ),
			null, null );
	}

	/// Tests when the user clicks the Begin button.
	public void testBeginApp( ) {
		getActivity().beginBtn_onClick( null );

		assertEquals( "com.sosobro.enchroma.MainActivity",
			getStartedActivityIntent().getComponent().getClassName() );
		// We won't go back to the splash screen.
		assertTrue( isFinishCalled() );
	}
}