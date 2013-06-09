package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class SplashActivityTest extends ActivityUnitTestCase<SplashActivity> {
	public SplashActivityTest( ) {
		super( SplashActivity.class );
	}

	public void testSomke( ) {
		SplashActivity a = (SplashActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), SplashActivity.class ),
			null, null );
	}
}