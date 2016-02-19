package shellljx.gmail.com.app.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import shellljx.gmail.com.app.util.GsonUtil;

/**
 * Created by Administrator on 2015/5/18.
 */
 public class GsonRequest<T> extends Request<T> {

    private final Gson gson = GsonUtil.getInstance().getGson();
    private Class<T> clazz;
    private Type mtype;
    private Response.Listener<T> listener;
    private static Map<String,String> mHeader = new HashMap<String,String>();


    public GsonRequest(int method, String url, Type type,Response.Listener listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        this.listener = listener;
        this.mtype = type;
    }

    public GsonRequest(int method,String url,Class clazz,Response.Listener listener ,Response.ErrorListener errorListener){
        super(method,url,errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }

    //设置访问服务器的时候必须传递的参数秘钥
    static{
        mHeader.put("APP-KEY","ABS-AAA");
        mHeader.put("APP-SCRET","helloworld");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeader;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try{
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T result = ClassOrType(json);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T t) {
        listener.onResponse(t);
    }

    private T ClassOrType(String json){
        if(clazz!=null){
            return gson.fromJson(json,clazz);
        }else if(mtype!=null){
            return gson.fromJson(json,mtype);
        }
        return null;
    }
}
