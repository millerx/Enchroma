package com.sosobro.enchroma;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

// TODO: Center button better
// TODO: Handle horizontal layout
// TODO: Timer instead of button?  With button?
// TODO: Copyrighted image?

public class SplashActivity extends Activity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_splash );
	}

	public void beginBtn_onClick( View v ) {
		startActivity( new Intent( this, MainActivity.class ) );
		// Finish this activity so we won't go back to slash screen.
		finish();
	}	
}