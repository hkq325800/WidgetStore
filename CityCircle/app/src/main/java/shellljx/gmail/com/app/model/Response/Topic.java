package shellljx.gmail.com.app.model.Response;

/**
 * Created by shell on 15-10-18.
 */
public class Topic {

    private String title;
    private boolean isExpend = false;

    public boolean isExpend() {
        return isExpend;
    }

    public void setIsExpend(boolean isExpend) {
        this.isExpend = isExpend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
