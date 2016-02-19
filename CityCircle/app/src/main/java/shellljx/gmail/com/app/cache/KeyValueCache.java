package shellljx.gmail.com.app.cache;

/**
 * Created by Administrator on 2015/6/4.
 */
public abstract class KeyValueCache<T> extends BaseCache {

    public abstract T getValue(String key);
    public abstract void putValue(String key,T object);
}
