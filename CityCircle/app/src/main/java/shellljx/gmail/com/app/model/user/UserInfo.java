package shellljx.gmail.com.app.model.user;

/**
 * Created by Administrator on 2015/6/4.
 */
public abstract class UserInfo {

    protected String userNumber;

    public abstract String getScreenName();
    public abstract String getProfileImageUrl();

    public String getUserNumber(){
        return userNumber;
    }

    public void setUserNumber(String userNumber){
        this.userNumber = userNumber;
    }
}
