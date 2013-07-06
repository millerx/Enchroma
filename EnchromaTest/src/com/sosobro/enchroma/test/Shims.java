package com.sosobro.enchroma.test;

import java.io.File;

import com.sosobro.enchroma.*;

import android.app.Activity;
import android.graphics.Bitmap;

public class Shims {
	
	public static class FileUtilsShim extends FileUtils {
		
		public static final String SubjectFilePath = "/storage/sdcard0/Pictures/Enchroma/subject.jpg";
		
		@Override
		public File createSubjectFilePath( Activity a ) {
			return new File( SubjectFilePath );
		}
	
	}
	
	public static class ThumbnailBuilderShim extends ThumbnailBuilder {
		
		@Override
		public Bitmap createThumbnail( File f ) {
			return Bitmap.createBitmap( 8, 8, Bitmap.Config.RGB_565 );
		}
		
	}

}
