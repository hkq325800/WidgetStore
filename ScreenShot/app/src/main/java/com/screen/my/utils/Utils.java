package com.screen.my.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class Utils {
	
	public static InputStream getAssertFile(Context context, String fileName) throws IOException {
		return context.getAssets().open(fileName);
	}
	
	public static boolean copy(InputStream inputStream, String outPath) {
		File file = new File(outPath);
		if(!file.exists()) {
			FileOutputStream fileOutputStream = null;
			try {
				file.createNewFile();
				fileOutputStream = new FileOutputStream(file);
				byte []b = new byte[2048];
				int len = 0;
				while((len = inputStream.read(b, 0, b.length)) != -1) {
					fileOutputStream.write(b, 0, len);
				}
				fileOutputStream.flush();
			} catch (Exception e) {
				e.printStackTrace();
				file.delete();
				return false;
			} finally {
				if(null != fileOutputStream) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(null != inputStream) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	
	public static boolean copy(InputStream inputStream, OutputStream outputStream) {
		try {
			byte []b = new byte[2048];
			int len = 0;
			while((len = inputStream.read(b, 0, b.length)) != -1) {
				outputStream.write(b, 0, len);
			}
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	
	public static String getSavePicPath(String path) {
		return path + "screen" + System.currentTimeMillis() + ".jpg";
	}
	
}
