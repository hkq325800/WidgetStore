package main.java.com.screen.aidl;

interface IScreenshotCallback implements Parcelable {

	void isRooted(boolean root);
	
	void isSeccuss(boolean seccussed);
}