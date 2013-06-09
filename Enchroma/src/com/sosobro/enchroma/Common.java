package com.sosobro.enchroma;

// TODO: Clean me up

public class Common {
	// Activity state bundle keys.
	public static final String BUNDLE_STATE = "state";
	public static final String EXTRA_SUBJECT_FN = "enchroma.subject_fn";
	public static final String EXTRA_BACKGROUND_FN = "enchroma.background_fn";
	
	// State constants
	public static final int STATE_OK = 0;
	public static final int STATE_FINISH = 1;
	public static final int STATE_EXTERNAL_START = 2;
	public static final int STATE_EXTERNAL_RETURN = 3;
	
	// Intent requests
	public static final int REQ_EXTERNAL = 0;
	public static final int REQ_NEXTACTIVITY = 1;
	public static final int REQ_CAPTURESUBJECTPHOTO = 2;
	public static final int REQ_SELECTBACKGROUND = 3;
}