package shellljx.gmail.com.app.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/16.
 */
public class Channel implements Serializable {

    public Channel(String logoUrl,String name,String createTime,int id){
        this.logourl = logoUrl;
        this.name = name;
        this.create_at = createTime;
        this.channelId = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logoUrl) {
        this.logourl = logoUrl;
    }

    @Override
    public String toString() {
        return "Channel{"+
                "channelId="+channelId+
                ", name='"+name+'\''+
                ", logourl='"+logourl+'\''+
                ", createTime='"+create_at+'\''+
                '}';
    }

    int channelId;
    String name;
    String logourl;
    String create_at;
}
