package com.kerchin.yellownote.utilities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * com.kerchin.yellownote.utilities
 * Created by hzhuangkeqing on 2015/9/23 0023.
 */
public class NormalUtils {
    public static void goToActivity(Activity context, Class activity) {
        Intent intent = new Intent();
        intent.setClass(context, activity);
        context.startActivity(intent);//首页面转换
        /*context.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);*/
    }

    public static String md5(String val) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = val.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateString(Date date) {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss", Locale.CHINA);/* HH:mm:ss*/
        if (current.getDay() - date.getDay() == 0) {
            return "今天" + formatter.format(date);
        } else if (current.getDay() - date.getDay() == 1) {
            return "昨天" + formatter.format(date);
        } else if (current.getDay() - date.getDay() == 2) {
            return "前天" + formatter.format(date);
        }
        formatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);/* HH:mm:ss*/
        return formatter.format(date);
    }

    /**
     * Map a value within a given range to another range.
     *
     * @param value    the value to map
     * @param fromLow  the low end of the range the value is within
     * @param fromHigh the high end of the range the value is within
     * @param toLow    the low end of the range to map to
     * @param toHigh   the high end of the range to map to
     * @return the mapped value
     */
    public static double mapValueFromRangeToRange(
            double value,
            double fromLow,
            double fromHigh,
            double toLow,
            double toHigh) {
        double fromRangeSize = fromHigh - fromLow;//250
        double toRangeSize = toHigh - toLow;//1
        double valueScale = (value - fromLow) / fromRangeSize;//0.02
        return toLow + (valueScale * toRangeSize);
    }

    /**
     * convert drawable to bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static String getStringPreview(String str) {
        String preview;
        if (str.length() < 29) {
            preview = str.replace("\n", " ");
        } else {
            preview = str.replace("\n", " ").substring(0, 29) + "...";
        }
        return preview;
    }

    //用于检测该意图能否可以使用
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public static String SYS_PROP_MOD_VERSION = "ro.modversion";

    /**
     * 获取ROM版本
     */
    public static String getModVersion() {
        String modVersion = getSystemProperty();
        if (modVersion == null || modVersion.length() == 0) {
            modVersion = getCustomROM();
        }
        return (modVersion == null || modVersion.length() == 0 ? "Unknown" : modVersion);
    }

    public static String getSystemProperty() {
        String line = null;
        BufferedReader reader = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "UNKNOWN";
    }
    /**
     * 获取系统属性
     */
    /*public static String getSystemProperty(String prop) {
        String line;
        BufferedReader input = null;
        try {
            Process process = Runtime.getRuntime().exec("getprop " + prop);
            input = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return line;
    }*/

    /**
     * 获取自定义机型的ROM
     */
    public static String getCustomROM() {
        String line;
        BufferedReader input = null;
        StringBuilder sb = new StringBuilder();
        String version = Build.VERSION.RELEASE;
        try {
            Process process = Runtime.getRuntime().exec("getprop ro.build.description");
            input = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"), 1024);
            line = input.readLine();
            if (line == null || line.length() == 0) {
                return null;
            }
            String[] s = line.split("\\s");
            for (int i = 0; i < s.length; i++) {
                if (!s[i].equals(version) && !s[i].contains("key")) {
                    sb.append(s[i] + " ");
                }
            }
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void openMiuiPermissionActivity(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        String rom = getModVersion();

        if ("V5".equals(rom)) {
            /*PackageInfo pInfo = null;
            try {
                pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            intent.setClassName(SETTINGS_PACKAGE_NAME, "com.miui.securitycenter.permission.AppPermissionsEditor");
            assert pInfo != null;
            intent.putExtra("extra_package_uid", pInfo.applicationInfo.uid);*/

        } else if ("V6".equals(rom)) {
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
        }

        if (isIntentAvailable(context, intent)) {
            if (context instanceof Activity) {
                Activity a = (Activity) context;
                a.startActivityForResult(intent, 2);
            }
        } else {
            Log.e("openMiuiPermission", "Intent is not available!");
            Toast.makeText(context, "当前miui版本不支持直接打开设置中心", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 判断MIUI的悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isMiuiFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        int OP_SYSTEM_ALERT_WINDOW = 24;
        if (version >= 19) {
            checkOp(context, OP_SYSTEM_ALERT_WINDOW);  //自己写就是24 为什么是24?看AppOpsManager
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method checkOp = manager.getClass().getMethod("checkOp", Integer.class, Integer.class, String.class);
                Integer flag = (Integer) checkOp.invoke(manager, op
                        , Binder.getCallingUid(), context.getPackageName());
                if (AppOpsManager.MODE_ALLOWED == flag/*(Integer)ReflectUtils.invokeMethod(manager, "checkOp", op
                        , Binder.getCallingUid(), context.getPackageName())*/) {  //这儿反射就自己写吧
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("checkOp", "Below API 19 cannot invoke!");
        }
        return false;
    }
}
