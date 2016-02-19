package shellljx.gmail.com.app.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import shellljx.gmail.com.app.model.user.AppUserInfo;
import shellljx.gmail.com.app.model.user.PlatformUserInfo;
import shellljx.gmail.com.app.util.GsonUtil;

/**
 * Created by Administrator on 2015/6/4.
 */
public class PlatformUserCache extends KeyValueCache<PlatformUserInfo> {

    private static PlatformUserCache instance;

    private Context context;
    private Map<String,PlatformUserInfo> cache;
    private String mDefaultKey;

    public static class Key{
        public static final String QQ="qq_user_info";
        public static final String WEIBO="weibo_user_info";
        public static final String APP="app_user_info";
    }

    public static synchronized PlatformUserCache getInstance(Context context){
        if(instance==null){
            instance = new PlatformUserCache(context);
        }
        return instance;
    }

    private PlatformUserCache(Context context){
        this.context = context;
        load();
    }
    @Override
    public PlatformUserInfo getValue(String key) {
        return cache.get(key);
    }

    /**
     * @param key
     * @param object
     */
    @Override
    public void putValue(String key, PlatformUserInfo object) {
        cache.put(key,object);
    }

    /**
     * @param userInfo
     */
    public void putAppUserInfo(AppUserInfo userInfo){
        cache.put(Key.APP,userInfo);
    }

    public AppUserInfo getAppUserInfo(){
        return (AppUserInfo)cache.get(Key.APP);
    }

    public int getCacheSize(){
        return cache.size();
    }

    public String getmDefaultKey(){
        return mDefaultKey;
    }

    public void setmDefaultKey(String defaultKey){
        this.mDefaultKey = defaultKey;
    }

    public boolean isLogin(){
        return getValue(mDefaultKey) !=null;
    }

    @Override
    public void load() {
        cache = new HashMap<>();
        SharedPreferences preferences = context.getSharedPreferences("platform_user",Context.MODE_PRIVATE);
        String appJson = preferences.getString(Key.APP,null);
        if(appJson!=null){
            AppUserInfo appUserInfo = GsonUtil.getInstance().getGson().fromJson(appJson,AppUserInfo.class);
            cache.put(Key.APP,appUserInfo);
        }

        mDefaultKey = preferences.getString("default_key",null);
    }

    @Override
    public void save() {
        Set<String> keySet = cache.keySet();
        SharedPreferences preferences = context.getSharedPreferences("platform_user", Context.MODE_PRIVATE);
        Gson gson = GsonUtil.getInstance().getGson();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("default_key",mDefaultKey);
        for(String key:keySet){
            PlatformUserInfo userInfo = cache.get(key);
            if(userInfo!=null){
                editor.putString(key,gson.toJson(userInfo));
            }
        }
        editor.apply();
    }

    @Override
    public void clear() {
        SharedPreferences preferences = context.getSharedPreferences("platform_user",Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        load();
    }
}
