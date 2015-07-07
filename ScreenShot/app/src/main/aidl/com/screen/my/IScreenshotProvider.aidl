package com.screen.my;

import com.screen.my.IScreenshotCallback;

interface IScreenshotProvider {
	
	void registRemoteCallback(IScreenshotCallback callback);
	
	void unRegistRemoteCallback(IScreenshotCallback callback);
	
	void root();
}