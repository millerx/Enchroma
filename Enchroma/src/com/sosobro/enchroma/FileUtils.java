package com.sosobro.enchroma;

import java.io.File;

import android.app.Activity;
import android.os.Environment;

public class FileUtils {

	public File createSubjectFilePath( Activity a ) {
		// /storage/sdcard0/Pictures/Enchroma/subject.jpg
		
		File f = new File(
			Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
			a.getResources().getString( R.string.app_name ) );
		f.mkdir();
		
		return new File(
			f.toString(),
			"subject.jpg" );
	}
	
	public static FileUtils instance = new FileUtils();
	
}
