package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class SplashActivityTest extends ActivityUnitTestCase<SplashActivity> {
	public SplashActivityTest( ) {
		super( SplashActivity.class );
	}

	/// Tests when the user clicks the Begin button.
	public void testBeginApp( ) {
		SplashActivity a = (SplashActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), SplashActivity.class ),
			null, null );

		a.beginBtn_onClick( null );

		assertEquals( "com.sosobro.enchroma.MainActivity",
			getStartedActivityIntent().getComponent().getClassName() );
		// We won't go back to the splash screen.
		assertTrue( isFinishCalled() );
	}
}