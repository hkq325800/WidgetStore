package com.screen.my;
import android.os.Parcel;
import android.os.Parcelable;

interface IScreenshotCallback implements Parcelable  {

	void isRooted(boolean root);
	
	void isSeccuss(boolean seccussed);
}