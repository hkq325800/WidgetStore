package shellljx.gmail.com.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import shellljx.gmail.com.app.util.NetUtils;

/**
 * Created by Administrator on 2015/5/24.
 */
public class NetReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            boolean isConnected = NetUtils.getInstance(context).isNetworkConnected();
            Toast.makeText(context,"网络状态:"+isConnected+"\nWifi状态:"+
            NetUtils.getInstance(context).isWifiConnected()+"\n移动网络:"+
            NetUtils.getInstance(context).isMobileConnected()
            +"网络连接类型:"+NetUtils.getInstance(context).getConnectedType(),Toast.LENGTH_SHORT).show();
        }
    }
}
