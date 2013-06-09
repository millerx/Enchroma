package com.sosobro.enchroma.test;

import com.sosobro.enchroma.*;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	public MainActivityTest( ) {
		super( MainActivity.class );
	}

	public void testSomke( ) {
		MainActivity a = (MainActivity) startActivity(
			new Intent( getInstrumentation().getTargetContext(), MainActivity.class ),
			null, null );
	}
}