package shellljx.gmail.com.app.service;

import android.content.Context;

import shellljx.gmail.com.app.http.HttpRequestManager;

/**
 * Created by Administrator on 2015/5/18.
 */
public class BaseService {

    protected HttpRequestManager httpRequestManager;

    private String HOST = "http://mc.linghun.me";
    private String BASE_PATH = "/app";

    protected String QUERY_CHANNEL_LIST = "/get_clist.php";

    private Context context;

    public BaseService(Context context){
        this.context = context;
        this.httpRequestManager = HttpRequestManager.getInstance(context);
    }

    public String getBASE_URL(){
        return HOST+BASE_PATH;
    }

}
