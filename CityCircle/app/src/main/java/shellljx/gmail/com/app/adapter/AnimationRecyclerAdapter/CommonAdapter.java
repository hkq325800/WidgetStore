package shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationRecyclerAdapter;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationViewHolder;
import shellljx.gmail.com.app.util.Constants;
import shellljx.gmail.com.widget.R;

/**
 * Created by shell on 15-9-13.
 */
public abstract class CommonAdapter<T> extends AnimationRecyclerAdapter<RecyclerView.ViewHolder> {

    protected LayoutInflater mInflate;
    protected int mLayoutId;
    protected Context mContext;
    protected List<T> mDatas;
    private OnLoadMoreListener mLoadMoreListener;

    private int firstVisibleItem, totalItemCount, visibleItemCount;
    private boolean isLoading;

    public CommonAdapter(Context context, RecyclerView listview, List<T> data, int layoutId) {
        this.mContext = context;
        this.mDatas = data;
        this.mInflate = LayoutInflater.from(mContext);
        this.mLayoutId = layoutId;

        if (listview.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager manager = (LinearLayoutManager) listview.getLayoutManager();

            listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = manager.getItemCount();
                    firstVisibleItem = manager.findFirstVisibleItemPosition();
                    visibleItemCount = manager.getChildCount();
                    if (!isLoading && (firstVisibleItem + visibleItemCount) >= totalItemCount) {
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoad();
                        }
                        isLoading = true;
                    }
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constants.EXTRA.LIST_LOADING:
                View loading = mInflate.inflate(R.layout.loading_first_time, parent, false);
                return new AnimationViewHolder(loading);
            case Constants.EXTRA.LIST_ITEM:
                View item = mInflate.inflate(mLayoutId, parent, false);
                return new AnimationViewHolder(item);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //如果item==null就是加载更多的布局，不需要开始动画和绑定数据
        if (mDatas.get(position) != null) {
            super.onBindViewHolder(holder, position);
            convert((AnimationViewHolder) holder, mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(mDatas.get(position), position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setLoaded() {
        this.isLoading = false;
    }

    public abstract void convert(AnimationViewHolder helper, T item);

    public abstract int getViewType(T item, int position);

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoad();
    }
}
