package shellljx.gmail.com.app.util;

import android.app.Activity;
import android.os.Build;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public class CompatibilityUtil {

    public static List<String> listSdk;
    private static Method overridePenddingTransition;

    static {
        listSdk = new ArrayList<>(10);
        listSdk.add("1.5");
        listSdk.add("3");
        try {
            overridePenddingTransition = Activity.class.getMethod(
                    "overridePendingTransition",new Class[]{Integer.TYPE,Integer.TYPE}
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasImageCaptureBug(){
        boolean hasBug = false;
        String sdk = Build.VERSION.SDK;
        for(String tempSdk : listSdk){
            if(sdk.indexOf(tempSdk)!=-1){
                hasBug = true;
                break;
            }
        }
        if("me600".equals(getModel().toLowerCase())){
            hasBug = false;
        }
        return hasBug;
    }


    public static String getModel(){
        return Build.MODEL;
    }
}
