package shellljx.gmail.com.app.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationViewHolder;
import shellljx.gmail.com.app.adapter.TopicAdapter;
import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.app.model.Response.Topic;
import shellljx.gmail.com.app.model.Response.TopicHeader;
import shellljx.gmail.com.app.util.Constants;
import shellljx.gmail.com.widget.R;

/**
 * Created by lijinxiang on 10/11/15.
 */
public class TopicFragment extends BaseFragment {

    @Bind(R.id.rl_content)
    RecyclerView rl_content;

    private TopicAdapter mAdapter;

    private List<Topic> mData;
    private TopicHeader header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = new ArrayList<>();
        android.util.Log.d("ljx", "哈哈哈哈");
        setupView();
        setAdapter();
    }

    @Override
    public void onResume() {
        mAdapter.setViewPagerAutoScroll(true);
        super.onResume();
    }

    @Override
    public void onPause() {
        mAdapter.setViewPagerAutoScroll(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupView() {
        rl_content.setHasFixedSize(true);
        rl_content.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    private void setAdapter() {
        //假数据
        for (int i = 0; i < 16; i++) {
            Topic topic = new Topic();
            topic.setTitle("这是话题的标题 = " + i);
            mData.add(topic);
        }
        List<String> list = new ArrayList<>();
        list.add("http://a.hiphotos.baidu.com/image/pic/item/8cb1cb13495409237d89a9779058d109b2de49d3.jpg");
        list.add("http://c.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc4c3dbc0113ddbb6fd5266331f.jpg");
        list.add("http://b.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01fdb719d2de4fe9925bd317df5.jpg");
        list.add("http://f.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbed860dc1ae7cf0f736aec31f8e.jpg");
        header = new TopicHeader(list);
        //第一个item为空是header
        mData.add(0, null);
        mData.add(null);
        rl_content.setAdapter(mAdapter = new TopicAdapter(getActivity(), rl_content, mData, header, R.layout.item_topic, R.layout.header_gallery));
    }
}
