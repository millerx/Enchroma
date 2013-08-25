package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.os.Environment;

public class FileUtils {

	public File createSubjectFilePath( Activity a ) {
		// /storage/sdcard0/Pictures/Enchroma/subject.jpg
		
		// TODO: Facebook put imgs under /DCIM/Facebook

		File picturesDir = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
		String appName   = a.getResources().getString( R.string.app_name );
		File f = new File( picturesDir, appName );
		f.mkdir();
		
		return new File( f.toString(), "subject.jpg" );
	}
	
	public static FileUtils instance = new FileUtils();
	
}
