package com.sosobro.enchroma;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

// TODO: http://developer.android.com/tools/testing/contentprovider_testing.html

public class PickUtils {

	public String queryFilename(Activity a, android.net.Uri uri) {
        String[] columns = { MediaStore.Images.Media.DATA };
    	
        Cursor cursor = a.getContentResolver().query( uri, columns, null, null, null );
        cursor.moveToFirst();

        String filename = cursor.getString( cursor.getColumnIndex( columns[0] ) );
        cursor.close();
        
        return filename;
	}
	
	public static PickUtils instance = new PickUtils();
	
}