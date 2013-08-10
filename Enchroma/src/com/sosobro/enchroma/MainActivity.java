package com.sosobro.enchroma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

// TODO: Optimize layout
//       http://stackoverflow.com/questions/5254562/is-there-a-simpler-better-way-to-put-a-border-outline-around-my-textview

public class MainActivity extends Activity {

	public static final int REQ_CAPTURESUBJECTPHOTO = 1;
	public static final int REQ_SELECTBACKGROUNDPHOTO = 2;

	private PhotoView m_subjectPV;
	private PhotoView m_backgroundPV;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		m_subjectPV = (PhotoView) findViewById( R.id.subjectImg );
		m_subjectPV.init( this, REQ_CAPTURESUBJECTPHOTO );

		m_backgroundPV = (PhotoView) findViewById( R.id.backgroundImg );
		m_backgroundPV.init( this, REQ_SELECTBACKGROUNDPHOTO );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	@Override
	protected void onResume( ) {
		super.onResume();
		System.out.println("MainActivity resume");
		m_subjectPV.onActivityResume();
		m_backgroundPV.onActivityResume();
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.printf("onActivityResult req=%d res=%d\n", requestCode, resultCode);

		switch (requestCode)
		{
		case REQ_CAPTURESUBJECTPHOTO:
			m_subjectPV.onActivityResult( resultCode, data );
			break;
		case REQ_SELECTBACKGROUNDPHOTO:
			m_backgroundPV.onActivityResult( resultCode, data );
			break;
		}
	}

	public void engageBtn_onClick( View v ) {
		// TODO: Enage
	}
}