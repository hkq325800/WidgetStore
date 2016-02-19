package shellljx.gmail.com.app.model.Response;

/**
 * Created by Administrator on 2015/5/20.
 */
public class PagerRes {

    public PagerRes(){

    }

    public PagerRes(int page,int totalPages){
        this.page = page;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

//    public int getEnd() {
//        return end;
//    }
//
//    public void setEnd(int end) {
//        this.end = end;
//    }
//
//    public int getStart() {
//        return start;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public void setSize(int size){this.size = size;}
    public int getSize(){return this.size;}
//    public int getCount(){return this.count;}
//    public void setCount(int count){this.count = count;}

    //private int count;//总数
    private int page;//页码
    private int size;//每页数量
    //private int end;//结束id
    //private int start;//起始id
    private int totalPages;//总页数
}
