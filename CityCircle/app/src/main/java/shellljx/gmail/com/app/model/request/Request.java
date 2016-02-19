package shellljx.gmail.com.app.model.request;

import java.util.HashMap;
import java.util.Map;

import shellljx.gmail.com.app.util.GsonUtil;

/**
 * Created by Administrator on 2015/5/18.
 */
public class Request<T> {

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getJessionId() {
        return jessionId;
    }

    public void setJessionId(String jessionId) {
        this.jessionId = jessionId;
    }

    public Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();

        params.put("userNumber",userNumber==null?"":userNumber);
        params.put("jessionId",jessionId==null?"":jessionId);
        params.put("data", GsonUtil.getInstance().getGson().toJson(data));

        return params;
    }

    private String userNumber;
    private String jessionId;
    private T data;
}
