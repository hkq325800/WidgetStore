package shellljx.gmail.com.app.model.user;

/**
 * Created by Administrator on 2015/6/4.
 */
public class AppUserInfo extends PlatformUserInfo {

    public static class Properties{
        public final static String Phone="phone";
        public final static String ScreenName = "screen_name";
        public final static String Password="password";
    }

    private String mobile;
    private String nickname;
    private String password;

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getNickname(){
        return this.nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public Platform getPlatform() {
        return Platform.APP;
    }

    @Override
    public String getScreenName() {
        return nickname;
    }

    @Override
    public String getProfileImageUrl() {
        return null;
    }
}
