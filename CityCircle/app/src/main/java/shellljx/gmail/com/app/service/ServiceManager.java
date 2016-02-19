package shellljx.gmail.com.app.service;

import android.content.Context;

import java.util.HashMap;

import shellljx.gmail.com.app.model.request.Request;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ServiceManager {

    private static ServiceManager manager;

    private static Context context;
    private HashMap<String,BaseService> serviceMap;
    private static String userNumber;
    private static String jsessionId;

    public static final String CHANNEL_SERVICE = "channel_service";

    public static synchronized ServiceManager getInstance(Context context){
        if(manager==null){
            manager = new ServiceManager(context);
        }
        return manager;
    }

    public ServiceManager(Context context){
        this.context = context;
        this.serviceMap = new HashMap<String,BaseService>();
    }

    public BaseService getService(String serviceName){
        BaseService service = serviceMap.get(serviceName);
        if(service==null){
            if(CHANNEL_SERVICE.equalsIgnoreCase(serviceName)){

                service = new ChannelService(context);
                serviceMap.put(serviceName,service);
            }
        }
        return service;
    }

    public static <T> Request<T> obtainRequest(T data) {
        Request<T> request = new Request<>();
        request.setUserNumber("shell");
        request.setJessionId("123456");
        request.setData(data);

        return request;
    }
}
