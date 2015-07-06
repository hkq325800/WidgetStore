package com.pps.screen.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * 进行截屏工具类
 * @author jiangqingqing
 * @time 2013/09/29
 */
public class ScreenShotUtils {	
	/**
	 * 进行截取屏幕  
	 * @param pActivity
	 * @return bitmap
	 */
	public static Bitmap takeScreenShot(Activity pActivity)
	{
		Bitmap bitmap=null;
		View view=pActivity.getWindow().getDecorView();
		// 设置是否可以进行绘图缓存
		view.setDrawingCacheEnabled(true);
		// 如果绘图缓存无法，强制构建绘图缓存
		view.buildDrawingCache();
	    // 返回这个缓存视图 
		bitmap=view.getDrawingCache();
		
		// 获取状态栏高度
		Rect frame=new Rect();
		// 测量屏幕宽和高
		view.getWindowVisibleDisplayFrame(frame);
		int stautsHeight=frame.top;
		Log.d("jiangqq", "状态栏的高度为:"+stautsHeight);
		
		int width=pActivity.getWindowManager().getDefaultDisplay().getWidth();
		int height=pActivity.getWindowManager().getDefaultDisplay().getHeight();
		// 根据坐标点和需要的宽和高创建bitmap
		bitmap=Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height-stautsHeight);
		return bitmap;
	}
	
	
	/**
	 * 保存图片到sdcard中
	 * @param pBitmap
	 */
	private static boolean savePic(Bitmap pBitmap,String strName)
	{
	  FileOutputStream fos=null;
	  try {
		fos=new FileOutputStream(strName);
		if(null!=fos)
		{
			pBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.flush();
			fos.close();
			return true;
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	  return false;
	} 
	/**
	 * 截图
	 * @param pActivity 
	 * @return 截图并且保存sdcard成功返回true，否则返回false
	 */
	public static boolean shotBitmap(Activity pActivity)
	{
		
		return  ScreenShotUtils.savePic(takeScreenShot(pActivity), "sdcard/"+System.currentTimeMillis()+".png");
	}
	
	
}
