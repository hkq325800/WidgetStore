package shellljx.gmail.com.app.support;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import shellljx.gmail.com.APPAplication;

/**
 * Created by Administrator on 2015/6/10.
 */
public class Utility {

    public static boolean isIntentsafe(Activity activity,Intent intent){
        PackageManager manager = activity.getPackageManager();
        List<ResolveInfo> activities = manager.queryIntentActivities(intent,0);
        return activities.size()>0;
    }

    public static int dip2px(int dipValue) {
        float reSize = APPAplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public static int px2dip(int pxValue) {
        float reSize = APPAplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    public static int getScreenWidth() {
        Activity activity = APPAplication.getInstance().getActivity();
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.widthPixels;
        }

        return 480;
    }

    public static boolean isKK() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static int getScreenHeight() {
        Activity activity = APPAplication.getInstance().getActivity();
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.heightPixels;
        }
        return 800;
    }

    public static boolean isIntentsafe(Activity activity,Uri uri){
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        PackageManager manager = activity.getPackageManager();
        List<ResolveInfo> activities = manager.queryIntentActivities(intent,0);
        return activities.size()>0;
    }

    public static void copyFile(InputStream in,File destFile) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] bytes = new byte[1024];
        int len;
        while ((len=bufferedInputStream.read(bytes))!=-1){
           bufferedOutputStream.write(bytes,0,len);
        }
        closeSilently(bufferedInputStream);
        closeSilently(bufferedOutputStream);
    }

    public static void closeSilently(Closeable c){
        if(c!=null){
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
