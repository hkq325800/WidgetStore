package com.iue.pocketdoc.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 功能描述：单位转换工具类
 * 
 * @author syc
 * 
 */
public class DesityUtil {

	/** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  

	/** 
     * 根据手机的分辨率从 dp(像素) 的单位 转成为 px 
     */  
    public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) ((dipValue - 0.5f ) * scale);  
    }  

	public static float getDensity(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		return density;
	}

	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	/** 获取屏幕的高度 */
	public final static int getWindowsHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.heightPixels;
	}

	public static int sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
}
