package main.java.com.screen.aidl;

import com.screen.aidl.IScreenshotCallback;

interface IScreenshotProvider {
	
	void registRemoteCallback(IScreenshotCallback callback);
	
	void unRegistRemoteCallback(IScreenshotCallback callback);
	
	void root();
}