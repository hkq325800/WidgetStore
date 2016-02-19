package shellljx.gmail.com.widget.model;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/15.
 */
public class RecyclerViewItemArray extends LinkedList<ItemData> {

    public int findFirstTypePosition(int type){
        for (int i=0;i<size();i++){
            if(type==get(i).getDataType()){
                return i;
            }
        }
        return -1;
    }

    public int findLastTypePosition(int type){
        for(int i=size()-1;i>=0;i--){
            if(type==get(i).getDataType()){
                return i;
            }
        }
        return -1;
    }

    public int findFirstNotTypePosition(int type){
        for (int i=0;i<size();i++){
            if(type!=get(i).getDataType()){
                return i;
            }
        }
        return -1;
    }

    public int findLastNotTypePosition(int type){
        for (int i=size()-1;i>=0;i--){
            if(type!=get(i).getDataType()){
                return i;
            }
        }
        return -1;
    }

    public int addAfterLast(int type,ItemData data){
        int position = findLastTypePosition(type);
        add(position++,data);
        return position;
    }

    public int addBeforFirst(int type,ItemData data){
        int position = findFirstTypePosition(type);
        add(position,data);
        return position;
    }

    public ItemData removeFirstType(int type){
        int position = findFirstTypePosition(type);
        if(position!=-1){
            return remove(position);
        }
        return null;
    }

    /**
     * 判断链表中是否存在这个类型的数据项
     * @param type
     * @return
     */
    public boolean isEmptyOfType(int type){
        return findFirstTypePosition(type)==-1;
    }
}
