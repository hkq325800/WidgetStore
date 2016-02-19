package shellljx.gmail.com.app.model.Response;

import shellljx.gmail.com.app.model.Pager;

/**
 * Created by Administrator on 2015/5/20.
 */
public class Response<T> {
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }

//    public String getJsessonId() {
//        return jsessonId;
//    }
//
//    public void setJsessonId(String jessionId) {
//        this.jsessonId = jessionId;
//    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PagerRes getPagerRes() {
        return pager;
    }

    public void setPagerRes(PagerRes pager) {
        this.pager = pager;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                //"success=" + success +
                //", info='" + info + '\'' +
                //", jsessonid='" + jsessonId + '\'' +
                ", data='" + data +'\'' +
                ", pager='" + pager +'\'' +
                '}';
    }

    //private boolean success;
    //private String info;
    //private String jsessonId;
    private T data;
    private PagerRes pager;
}
