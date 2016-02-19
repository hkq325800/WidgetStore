package shellljx.gmail.com.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2015/5/24.
 */
public class NetUtils {

    private static NetUtils instance;
    private static Context context;
    private static ConnectivityManager manager;

    public static NetUtils getInstance(Context context){
        if(instance==null)
            instance = new NetUtils(context);

        return instance;
    }

    public NetUtils(Context context){
        this.context = context;
        manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //判断网络连接状态
    public boolean isNetworkConnected(){
        if(context!=null){
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断是否在wifi状态下
    public boolean isWifiConnected(){
        if(context!=null){
            NetworkInfo wifiNetworkinfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiNetworkinfo!=null){
                return wifiNetworkinfo.isAvailable();
            }
        }
        return false;
    }

    //判断是否在移动网络状态下
    public boolean isMobileConnected(){
        if(context!=null){
            NetworkInfo mobileNetworkinfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mobileNetworkinfo!=null){
                return mobileNetworkinfo.isAvailable();
            }
        }
        return false;

    }

    //获取网络类型
    public int getConnectedType(){
        if(context!=null){
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){
                return networkInfo.getType();
            }
        }
        return -1;
    }
}
