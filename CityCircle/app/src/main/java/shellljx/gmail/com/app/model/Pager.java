package shellljx.gmail.com.app.model;

/**
 * Created by Administrator on 2015/5/18.
 */
public class Pager {
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    int page = 1;
    int size = 20;

    public Pager(int page,int size){
        this.page = page;
        this.size = size;
    }

}
