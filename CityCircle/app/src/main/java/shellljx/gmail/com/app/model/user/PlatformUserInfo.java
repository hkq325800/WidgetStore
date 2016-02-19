package shellljx.gmail.com.app.model.user;

/**
 * Created by Administrator on 2015/6/4.
 */
public abstract class PlatformUserInfo extends UserInfo{

    public static enum Platform{
        QQ,WEIBO,APP
    }

    public abstract Platform getPlatform();


}
