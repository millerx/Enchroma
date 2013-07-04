package com.sosobro.enchroma;

// Next:
// - Create and serialize thumb.
// - Continue moving code over.
// - Pull out CameraPhotoView vs SelectPhotoView
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
	private SavedState _ss = new SavedState();

	static class SavedState extends BaseSavedState {
		public File photoFile;

		public SavedState( ) {
			super( new Bundle() );
		}
		
		public SavedState( Parcelable p, SavedState ss ) {
			super( p );
			photoFile = ss.photoFile;
		}
		
		private SavedState( Parcel in ) {
			super( in );
			
			{ // photoFile
				String s = in.readString();
				photoFile = (s == "") ? null : new File( s ); 
			}
			
			printState("Restored state:");
		}

		@Override
		public void writeToParcel( Parcel out, int flags ) {
			super.writeToParcel( out, flags );
			out.writeString( (photoFile == null) ? "" : photoFile.toString() );
			
			printState("Saved state:");
		}

		private void printState( String header ) {
			System.out.println( header );
			System.out.printf( "photoFile=%s\n", photoFile==null ? "null" : photoFile.toString() );
			//System.out.printf( "thumb=%d\n", thumb==null ? 0 : thumb.getByteCount() );
		}
		
		// required field that makes Parcelables from a Parcel
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel( Parcel in ) { return new SavedState( in ); }
			public SavedState[] newArray( int size ) { return new SavedState[size]; }
		};
	}
	
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
		return new SavedState( super.onSaveInstanceState(), _ss );
	}

	@Override
	protected void onRestoreInstanceState( Parcelable state ) {
		_ss = (SavedState) state;
		super.onRestoreInstanceState( _ss.getSuperState() );
	}

	@Override
	public void onClick( View v ) {
		// TODO: Refactor into this class.
		_ss.photoFile = PhotoUtils.instance.createSubjectFilePath( _activity );
		
		Intent i = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		i.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( _ss.photoFile ) );
		_activity.startActivityForResult( i, _reqId );
	}

	public void onActivityResult( int resultCode, Intent data ) {
		System.out.printf( "onActivityResult photoFile=%s\n",
			(_ss.photoFile == null) ? "null" : _ss.photoFile.toString() ); 
		// TODO: Move Main.onPictureTaken
	}
}
