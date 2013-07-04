package com.sosobro.enchroma;

// Next:
// - Wire up save/restore instance.
// - Continue moving code over.
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class PhotoView extends ImageView
	implements View.OnClickListener {

	private Activity _activity;
	private int _reqId;
	// TODO: Remove reference to subject.
	private File _subjectFile;
	private Bitmap _subjectThumb;
	
	public PhotoView( Context context, AttributeSet attrs ) {
		super( context, attrs );
		setOnClickListener( this );
	}

	public void init( Activity a, int reqId ) {
		_activity = a;
		_reqId = reqId;
	}
	
	// TODO: Print what was saved and loaded
	@Override
	protected Parcelable onSaveInstanceState( ) {
		Parcelable p = super.onSaveInstanceState();

		SavedState ss = new SavedState( p );
		ss.stateToSave = (_subjectFile == null) ? null : _subjectFile.toString();
		
		return ss;
	}

	@Override
	protected void onRestoreInstanceState( Parcelable state ) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState( state );
			return;
		}

		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState( ss.getSuperState() );
		
		_subjectFile = (ss.stateToSave == null) ? null : new File( ss.stateToSave );
	}

	@Override
	public void onClick( View v ) {
		// TODO: Refactor into this class.
		_subjectFile = PhotoUtils.instance.createSubjectFilePath( _activity );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _subjectFile ) );
		_activity.startActivityForResult( i, _reqId );
	}

	public void onActivityResult( int resultCode, Intent data ) {
		System.out.printf( "onActivityResult subjectFile=%s\n",
			(_subjectFile == null) ? "null" : _subjectFile.toString() );
		// TODO: Move Main.onPictureTaken
	}
	
	static class SavedState extends BaseSavedState {
		String stateToSave;

		SavedState( Parcelable p ) {
			super( p );
		}

		private SavedState( Parcel in ) {
			super( in );
			stateToSave = in.readString();
		}

		@Override
		public void writeToParcel( Parcel out, int flags ) {
			super.writeToParcel( out, flags );
			out.writeString( this.stateToSave );
		}

		// required field that makes Parcelables from a Parcel
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel( Parcel in ) {
				return new SavedState( in );
			}

			public SavedState[] newArray( int size ) {
				return new SavedState[size];
			}
		};
	}
}
