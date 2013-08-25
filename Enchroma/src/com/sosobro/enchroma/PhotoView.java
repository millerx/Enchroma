package com.sosobro.enchroma;

// TODO: Unit test writes error to log.

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public abstract class PhotoView extends ImageView
	implements View.OnClickListener {

	protected Activity _activity;
	protected int _reqId;
	protected SavedState _ss = new SavedState();

	public static class SavedState extends BaseSavedState {
		public File photoFile;
		public Bitmap thumb;

		public SavedState( ) {
			super( new Bundle() );
		}
		
		public SavedState( Parcelable p, SavedState ss ) {
			super( p );
			photoFile = ss.photoFile;
			thumb = ss.thumb;
		}
		
		public SavedState( Parcel in ) {
			super( in );
			
			{ // photoFile
				String s = in.readString();
				photoFile = s.isEmpty() ? null : new File( s ); 
			}
			
			thumb = in.readParcelable( Bitmap.class.getClassLoader() );
			
			printState("Restored state:");
		}

		@Override
		public void writeToParcel( Parcel out, int flags ) {
			super.writeToParcel( out, flags );
			out.writeString( (photoFile == null) ? "" : photoFile.toString() );
			out.writeParcelable( thumb, 0 );
			
			printState("Saved state:");
		}

		private void printState( String header ) {
			System.out.println( header );
			System.out.printf( "photoFile=%s\n", photoFile==null ? "null" : photoFile.toString() );
			System.out.printf( "thumb=%d\n", thumb==null ? 0 : thumb.getByteCount() );
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
	
	@Override
	public Parcelable onSaveInstanceState( ) {
		return new SavedState( super.onSaveInstanceState(), _ss );
	}

	@Override
	public void onRestoreInstanceState( Parcelable state ) {
		_ss = (SavedState) state;
		super.onRestoreInstanceState( _ss.getSuperState() );
	}
	
	public void onActivityResume( ) {
		if (_ss.thumb != null) {
			setImageBitmap( _ss.thumb );
		}
	}
	
	public void onActivityResult( int resultCode, Intent data ) {
		// If OK was returned then _ss.photoFile should have been set.
		if (resultCode == Activity.RESULT_OK) {
			_ss.thumb = ThumbnailBuilder.instance.createThumbnail( _ss.photoFile );
		}
	}
}
