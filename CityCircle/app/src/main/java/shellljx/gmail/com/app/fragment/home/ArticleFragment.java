package shellljx.gmail.com.app.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationViewHolder;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.CommonAdapter;
import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.app.model.Response.Article;
import shellljx.gmail.com.app.util.Constants;
import shellljx.gmail.com.widget.R;

/**
 * Created by lijinxiang on 10/11/15.
 */
public class ArticleFragment extends BaseFragment {

    @Bind(R.id.post_list)
    RecyclerView post_list;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    private CommonAdapter mAdapter;
    private List<Article> mData;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mData = new ArrayList<>();
        post_list.setHasFixedSize(true);
        post_list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        setAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //   remove progress item
            mData.remove(mData.size() - 1);
            mAdapter.notifyItemRemoved(mData.size());
            //add items one by one
            int start = mData.size();
            int end = start + 1;

            for (int i = start; i < end; i++) {
                Article article = new Article();
                article.setContent("这是加贴 = " + i);
                mData.add(article);
                mAdapter.notifyItemInserted(mData.size());
            }
            mAdapter.setLoaded();
        }
    };

    private void setAdapter() {
        //假数据
        for (int i = 0; i < 4; i++) {
            Article article = new Article();
            article.setContent("这是消息 = " + i);
            mData.add(article);
        }
        post_list.setAdapter(mAdapter = new CommonAdapter<Article>(getActivity(), post_list, mData, R.layout.item_article) {
            @Override
            public void convert(AnimationViewHolder helper, Article item) {
                android.util.Log.d("ljx", item.getContent());
                ((TextView) helper.getView(R.id.content_text)).setText(item.getContent());
            }

            @Override
            public int getViewType(Article item, int position) {
                if (null == item){
                    return Constants.EXTRA.LIST_LOADING;
                }
                return Constants.EXTRA.LIST_ITEM;
            }
        });
        mAdapter.setOnLoadMoreListener(new CommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                mData.add(null);
                mAdapter.notifyItemInserted(mData.size());
                handler.postDelayed(runnable, 5000);
            }
        });
    }
}
