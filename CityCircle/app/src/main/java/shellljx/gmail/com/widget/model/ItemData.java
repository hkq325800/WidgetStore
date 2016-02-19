package shellljx.gmail.com.widget.model;

/**
 * Created by Administrator on 2015/5/15.
 */
public class ItemData<T> {
    private int mDataType;
    private T mData;

    public ItemData(int mDataType,T data){
        this.mDataType = mDataType;
        this.mData = data;
    }

    public ItemData(){}

    public int getDataType(){
        return mDataType;
    }

    public void setDataType(int dataType){
        this.mDataType = dataType;
    }

    public T getData(){
        return mData;
    }

    public void setData(T data){
        this.mData = data;
    }
}
