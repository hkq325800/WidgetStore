package shellljx.gmail.com.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/5/18.
 */
public class NotificationHelper {

    public static void toast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
