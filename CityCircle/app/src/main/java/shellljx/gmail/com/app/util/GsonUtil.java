package shellljx.gmail.com.app.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/5/18.
 */
public class GsonUtil {

    private static GsonUtil gsonUtil;
    private Gson gson;

    public static synchronized GsonUtil getInstance(){
        if(gsonUtil==null){
            gsonUtil = new GsonUtil();
        }
        return gsonUtil;
    }

    private GsonUtil(){
        gson = new Gson();
    }

    public Gson getGson(){
        return gson;
    }
}
