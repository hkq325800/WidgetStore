package shellljx.gmail.com.app.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import shellljx.gmail.com.APPAplication;
import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/10.
 */
public class FileManager {

    private static final String PICTURE_CACHE="picture_cache";
    private static final String TXT2PIC = "txt2pic";

    private static volatile boolean cantReadBecauseOfAndroidBugPermissionProblem = false;


    public static boolean isExternalStorageMounted(){
        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageDirectory().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY
        );
        boolean unMounted = Environment.getExternalStorageDirectory().equals(
                Environment.MEDIA_UNMOUNTED
        );
        return !(!canRead||onlyRead||unMounted);
    }

    public static String getConvertPicTempFile(){
        if(!isExternalStorageMounted()){
            return "";
        }else{
            return getSdCardPath()+File.separator+"convert"+
                    System.currentTimeMillis()+".jpg";
        }
    }

    public static String  getSdCardPath(){
        if(isExternalStorageMounted()){
            File path = APPAplication.getInstance().getExternalCacheDir();
            if(path!=null){
                return path.getAbsolutePath();
            }else{
                if(!cantReadBecauseOfAndroidBugPermissionProblem){
                    cantReadBecauseOfAndroidBugPermissionProblem=true;
                    final Activity activity = APPAplication.getInstance().getActivity();
                    if(activity==null||activity.isFinishing()){
                        APPAplication.getInstance().getUIHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(APPAplication.getInstance(),R.string.delete_cache_dir,Toast.LENGTH_LONG).show();
                            }
                        });

                        return "";
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(activity)
                                    .setTitle(R.string.warning)
                                    .setMessage(R.string.delete_cache_dir)
                                    .setPositiveButton(R.string.confim, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    });
                    return "";
                }
            }
        }else {
            return "";
        }
        return "";
    }

    public static File createNewFileInSDCard(String absolutePath) {
        if (!isExternalStorageMounted()) {
            Log.e("FileManager","sdcard unavailiable");
            return null;
        }

        if (TextUtils.isEmpty(absolutePath)) {
            return null;
        }

        File file = new File(absolutePath);
        if (file.exists()) {
            return file;
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                Log.d("FileManager", e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static String getTxt2picPath() {
        if (!isExternalStorageMounted()) {
            return "";
        }

        String path = getSdCardPath() + File.separator + TXT2PIC;
        File file = new File(path);
        if (file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
