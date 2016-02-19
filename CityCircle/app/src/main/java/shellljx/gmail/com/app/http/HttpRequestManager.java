package shellljx.gmail.com.app.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

import shellljx.gmail.com.app.util.GsonUtil;
import shellljx.gmail.com.app.util.NotificationHelper;

/**
 * Created by Administrator on 2015/5/18.
 */
public class HttpRequestManager {

    public static final String TAG = HttpRequestManager.class.getName();
    private static HttpRequestManager mHttpRequestManager;
    private RequestQueue mQueue;
    private Gson gson;

    public static synchronized HttpRequestManager getInstance(Context context){
        if(mHttpRequestManager==null){
            Log.e("Context",":"+context);
            mHttpRequestManager = new HttpRequestManager(context.getApplicationContext());
        }
        return mHttpRequestManager;
    }

    private HttpRequestManager(Context context){
        mQueue = Volley.newRequestQueue(context);
        gson = GsonUtil.getInstance().getGson();
    }

    public <T> void jsonGetRequest(String url,Class clazz,Map<String,String> params, final OnResponseListener<T> listener){
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET,url,clazz,new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                listener.onSuccess(t);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(gsonRequest);
    }

    public <T> void jsonGetRequest(String url,Type type,Map<String,String> params, final OnResponseListener<T> listener){
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET,url,type,new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                listener.onSuccess(t);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(gsonRequest);
    }

    public <T> void jsonPostRequest(String url,Type type, final Map<String,String> params, final OnResponseListener<T> listener){
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET,url,type,new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                listener.onSuccess(t);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mQueue.add(gsonRequest);

    }

    public <T> void jsonPostRequest(String url,Class clazz, final Map<String,String> params, final OnResponseListener<T> listener){
        GsonRequest<T> gsonRequest = new GsonRequest<T>(Request.Method.GET,url,clazz,new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                listener.onSuccess(t);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mQueue.add(gsonRequest);
    }

    public void request(Request request){
        mQueue.add(request);
    }

    public static abstract class OnResponseListener<T>{
        private Context context;

        public OnResponseListener(Context context){
            this.context = context;
        }
        public void onSuccess(T object){};

        public void onError(VolleyError error){
            if(error instanceof TimeoutError){
                NotificationHelper.toast(context,"服务器超时");
            }else if(error instanceof NoConnectionError){
                NotificationHelper.toast(context,"无网络连接");
            }else{
                NotificationHelper.toast(context,"服务器错误");
            }
        }

    }



}
