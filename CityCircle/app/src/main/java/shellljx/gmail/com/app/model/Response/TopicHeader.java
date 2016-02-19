package shellljx.gmail.com.app.model.Response;

import java.util.List;

/**
 * Created by shell on 15-10-18.
 */
public class TopicHeader {

    private List<String> mImages;

    public TopicHeader(List<String> list){
        this.mImages = list;
    }

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }
}
