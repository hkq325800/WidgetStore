package shellljx.gmail.com.app.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import shellljx.gmail.com.app.http.HttpRequestManager;
import shellljx.gmail.com.app.model.Channel;
import shellljx.gmail.com.app.model.request.Request;
import shellljx.gmail.com.app.model.Response.Response;
import shellljx.gmail.com.app.model.request.ChannelReq;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ChannelService extends BaseService {

    public ChannelService(Context context) {
        super(context);
    }

    public void queryChannelList(Request<ChannelReq> request,HttpRequestManager.OnResponseListener<Response> listener){
        Log.e("RequestParams:",request.getParams().toString());
        httpRequestManager.jsonPostRequest(getBASE_URL()+QUERY_CHANNEL_LIST,new TypeToken<Response<List<Channel>>>()
        {}.getType(),request.getParams(),listener);
    }
}
