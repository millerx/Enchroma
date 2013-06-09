package com.sosobro.enchroma;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

// Events come in this order:
// onCreate
// [onRestoreInstanceState]
// [onActivityResult]
// onResume
// [onSaveInstanceState]

public class StackActivity extends Activity {

	private int _state = Common.STATE_OK;
	protected void setState( int s ) { _state = s; }
	protected int getState( ) { return _state; }
	
	@Override
	protected void onRestoreInstanceState( Bundle state ) {
		super.onRestoreInstanceState( state );
		
		_state = state.getInt( Common.BUNDLE_STATE );
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		System.out.println( String.format("EN SA activityResult req=%d res=%d", requestCode, resultCode) );

		if (resultCode == RESULT_CANCELED) {
			_state = Common.STATE_FINISH;
		}
		else if (requestCode == Common.REQ_NEXTACTIVITY && resultCode == RESULT_OK) {
			_state = Common.STATE_FINISH;
		}
	}

	@Override
	protected void onResume( ) {
		super.onResume();

		System.out.println("EN SA resume state="+_state);
		
		if (_state == Common.STATE_FINISH) {
			finish();
		}
		else {
			onStackResume();
		}
	}
	
	protected void onStackResume( ) {
	}
	
	@Override
	protected void onSaveInstanceState( Bundle state ) {
		super.onSaveInstanceState( state );
		
		state.putInt( Common.BUNDLE_STATE, _state );
	}
	
}