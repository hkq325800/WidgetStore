package com.iue.pocketdoc.utilities;

import com.iue.pocketdoc.global.SysConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author SYC
 *
 * @date 2015年4月21日
 */
public class PrefsAccessor {

	
	private static PrefsAccessor instance = null;
	private SharedPreferences mSharedPreferences;

	private PrefsAccessor(Context context) {
		mSharedPreferences = context.getSharedPreferences(SysConfig.SHARE_DATA_FILE_NAME,
				Context.MODE_PRIVATE);
	}

	public static PrefsAccessor getInstance() {
		if (instance == null) {
			throw new RuntimeException("please init first!");
		}
		return instance;
	}

	public synchronized static void init(Context context) {
		if (instance == null) {
			instance = new PrefsAccessor(context);
		}
	}

	public void saveString(String key, String value) {
		getEditor().putString(key, value).commit();
	}

	public void saveBoolean(String key, boolean value) {
		getEditor().putBoolean(key, value).commit();
	}

	public String getString(String key, String... defValue) {
		String defaultValue = "";
		if (null == defValue) {
			defaultValue = null;
		} else if (null != defValue && defValue.length > 0) {
			defaultValue = defValue[0];
		}
		return mSharedPreferences.getString(key, defaultValue);
	}

	public boolean getBoolean(String key, boolean... defValue) {
		boolean def = false;
		if (null != defValue && defValue.length > 0) {
			def = defValue[0];
		}
		return mSharedPreferences.getBoolean(key, def);
	}

	public void remove(String key) {
		if (mSharedPreferences.contains(key)) {
			getEditor().remove(key).commit();
		}
	}

	private Editor getEditor() {
		return mSharedPreferences.edit();
	}
}