package com.sosobro.enchroma;

import android.os.Bundle;
import android.content.Intent;

public class ExternalActivity extends StackActivity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setState( Common.STATE_EXTERNAL_START );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.println("EN XA activityResult rc="+resultCode);
		
		if (resultCode != RESULT_CANCELED) {
			setState( Common.STATE_EXTERNAL_RETURN );
		}
	}

	@Override
	protected void onResume( ) {
		super.onResume();

		int state = getState();
		System.out.println("EN XA resume state="+state);
		
		if (state == Common.STATE_EXTERNAL_START) {
			onExternalStart();
		}
		else if (state == Common.STATE_EXTERNAL_RETURN) {
			onExternalReturn();
			// If we ever go back..
			setState( Common.STATE_EXTERNAL_START );
		}
	}
	
	protected void onExternalStart( ) {
	}

	protected void onExternalReturn( ) {
	}
	
}