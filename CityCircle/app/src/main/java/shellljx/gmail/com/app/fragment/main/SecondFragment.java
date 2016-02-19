package shellljx.gmail.com.app.fragment.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationViewHolder;
import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.app.http.HttpRequestManager;
import shellljx.gmail.com.app.http.ImageRequestManager;
import shellljx.gmail.com.app.model.Channel;
import shellljx.gmail.com.app.model.Pager;
import shellljx.gmail.com.app.model.Response.PagerRes;
import shellljx.gmail.com.app.model.Response.Response;
import shellljx.gmail.com.app.model.request.ChannelReq;
import shellljx.gmail.com.app.service.ChannelService;
import shellljx.gmail.com.app.service.ServiceManager;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationRecyclerAdapter;
import shellljx.gmail.com.widget.CommonToolbar.CommonToolbar;
import shellljx.gmail.com.widget.R;
import shellljx.gmail.com.widget.ViewHolder.LoadingFirstViewHolder;
import shellljx.gmail.com.widget.ViewHolder.LoadingViewHolder;
import shellljx.gmail.com.widget.ViewHolder.OffsetViewHolder;
import shellljx.gmail.com.widget.model.ItemData;
import shellljx.gmail.com.widget.model.RecyclerViewItemArray;

/**
 * Created by Administrator on 2015/5/8.
 */
public class SecondFragment extends BaseFragment implements CommonToolbar.OnToolbarClickListener,View.OnClickListener{

    private static final String TAG = SecondFragment.class.getName();
    private CommonToolbar toolbar;
    private RecyclerView channellist;
    private RecyclerViewItemArray mItemArray;
    private RelativeLayout headerPanel;
    private TextView paneltext;

    private ChannelService mChannelService;

    private static final int TYPE_CHANNEL = 1;
    private static final int TYPE_LOADING = 2;
    private static final int TYPE_FIRST_LOADING = 3;
    private static final int TYPE_OFFSET = 4;

    private boolean isLoading = false;
    private boolean isEnd = false;
    //头部解释栏的状态
    private boolean isAnimating = false;
    private boolean isPannelHidden = false;
    private AnimatorSet panelShowAnimatorSet;
    private AnimatorSet panelHidenAnimatorSet;

    public SecondFragment(){

    }

    public void initData(){
        mItemArray.add(new ItemData(TYPE_OFFSET,null));
        for(int i=0;i<30;i++){
            mItemArray.add(new ItemData(TYPE_CHANNEL,new Channel("http://img3.imgtn.bdimg.com/it/u=1790102556,3036052735&fm=21&gp=0.jpg",
                    "频道"+i,"2014年2月2日",i)));
        }
        mItemArray.add(new ItemData(TYPE_CHANNEL,new Channel("http://img3.imgtn.bdimg.com/it/u=1790102556,3036052735&fm=21&gp=0.jpg",
                "频道dfsdfsdsfsfdfsdfsdfsdfsdfsdfsddsgsfgsrwefwe","2014年2月2日",30)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,container,false);
        init(view);
        setupView(view);
        System.out.println(getActivity().getApplicationContext());
        return view;
    }



    public void init(View view){
        mItemArray = new RecyclerViewItemArray();
        mChannelService = (ChannelService) ServiceManager.getInstance(getActivity().getApplicationContext()).getService(ServiceManager.CHANNEL_SERVICE);
        initData();
    }

    public void setupView(View view){
        toolbar = (CommonToolbar)view.findViewById(R.id.toolbar);
        toolbar.setOnToolbarClickListener(this);
        channellist = (RecyclerView)view.findViewById(R.id.channel_list);
        headerPanel = (RelativeLayout)view.findViewById(R.id.header_panel);
        paneltext = (TextView)view.findViewById(R.id.header_panel_text);
        channellist.setHasFixedSize(true);
        channellist.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        channellist.setOnScrollListener(onScrollListener);
        channellist.setAdapter(new ChannelAdapter());
        getChannelList(1);
    }

    class ChannelAdapter extends AnimationRecyclerAdapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch(viewType){
                case TYPE_CHANNEL:{
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_item,parent,false);
                    return new ChannelViewHolder(view);
                }
                case TYPE_LOADING:{
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item,parent,false);
                    return new LoadingViewHolder(view);
                }
                case TYPE_FIRST_LOADING:{
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_first_time,parent,false);
                    return new LoadingFirstViewHolder(view);
                }
                case TYPE_OFFSET:{
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offset_layout,parent,false);
                    return new OffsetViewHolder(view);
                }
            }

            return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            switch(getItemViewType(position)){
                case TYPE_CHANNEL:{
                    ChannelViewHolder cHolder = (ChannelViewHolder)holder;
                    Channel channel = (Channel)mItemArray.get(position).getData();
                    cHolder.channelName.setText(channel.getName());
                    cHolder.createTime.setText(channel.getCreate_at());
                    cHolder.channelLogo.setImageUrl(channel.getLogourl(), ImageRequestManager.getInstance(getActivity()).getImageLoader());
                }
            }
            super.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemViewType(int position) {
            return mItemArray.get(position).getDataType();
        }

        @Override
        public int getItemCount() {
            return mItemArray.size();
        }

        class ChannelViewHolder extends AnimationViewHolder {

            private TextView channelName;
            private TextView createTime;
            private LinearLayout favorLogo;
            private NetworkImageView channelLogo;

            public ChannelViewHolder(View itemView) {
                super(itemView);
                channelName = (TextView)itemView.findViewById(R.id.channel_name);
                createTime = (TextView)itemView.findViewById(R.id.create_time);
                favorLogo = (LinearLayout)itemView.findViewById(R.id.favor_logo);
                channelLogo = (NetworkImageView)itemView.findViewById(R.id.channel_logo_view);
                channelLogo.setDefaultImageResId(R.drawable.ic_launcher);
                channelLogo.setErrorImageResId(R.drawable.ic_launcher);
                itemView.setOnClickListener(SecondFragment.this);
                favorLogo.setOnClickListener(SecondFragment.this);
                favorLogo.setTag(this);
            }
        }
    }


    @Override
    public void onLeftBtnClicked() {
        Toast.makeText(getActivity(),"点击了左边按钮",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightBtnClicked() {
        Toast.makeText(getActivity(),"点击了右边按钮",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.getActivity(),"dianji",Toast.LENGTH_SHORT).show();

    }

    //更新列表的方法
    public void getChannelList(int page){
        isLoading = true;
        ChannelReq channelReq = new ChannelReq();
        //获取channel列表的请求对象
        channelReq.setPager(new Pager(1,20));

        if(page==1){
            //第一页清空原来的数据
            mItemArray.clear();
            mItemArray.add(new ItemData(TYPE_OFFSET,null));
            mItemArray.add(new ItemData(TYPE_FIRST_LOADING,null));
            channellist.getAdapter().notifyDataSetChanged();
        }

        //obtainRequest()负责封装Request<T>对象（请求参数和用户名sessionid）
        mChannelService.queryChannelList(ServiceManager.obtainRequest(channelReq),mResponseListener);
    }
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(!isAnimating&&channellist.getScrollState()==RecyclerView.SCROLL_STATE_SETTLING){
                if(!isPannelHidden&&dy>0){
                    //在往上滑动的时候隐藏顶部解释栏
                    getPanelAnimatorSet(false).start();
                }else if(isPannelHidden&&dy<0){
                    //展示顶部栏
                    getPanelAnimatorSet(true).start();
                }
            }else if(isPannelHidden&&((LinearLayoutManager)channellist.getLayoutManager()).findFirstVisibleItemPosition()==0&&dy<=0&&channellist.getScrollState()==RecyclerView.SCROLL_STATE_DRAGGING){
                getPanelAnimatorSet(true).start();
            }

            //判断最后一个item，并加载更多
            if(isLoading||isEnd){
                return;
            }
            int lastPosition = ((LinearLayoutManager)channellist.getLayoutManager()).findLastVisibleItemPosition();
            ItemData itemData = mItemArray.get(lastPosition);
            if(itemData.getDataType()==TYPE_LOADING){

            }
        }
    };

    HttpRequestManager.OnResponseListener<Response> mResponseListener = new HttpRequestManager.OnResponseListener(getActivity()){
        @Override
        public void onSuccess(Object object) {
            //resolveResult((ArrayList<Channel>) object,new Pager(1,20));
            Response<ArrayList<Channel>> response = (Response<ArrayList<Channel>>)object;
            resolveResult(response.getData(),response.getPagerRes());
            paneltext.setText("页码"+response.getPagerRes().getPage()+"大小:"+response.getPagerRes().getSize()+"总页码:"+response.getPagerRes().getTotalPages());
        }
    };

    public void resolveResult(ArrayList<Channel> channels,PagerRes pager){
        if(channels!=null){
            mItemArray.removeFirstType(TYPE_FIRST_LOADING);
            for (Channel channel : channels) {
                mItemArray.add(new ItemData<>(TYPE_CHANNEL, channel));
            }
        }

        if(!mItemArray.isEmptyOfType(TYPE_CHANNEL)){
            mItemArray.add(new ItemData(TYPE_LOADING,pager));
        }
    }

    public AnimatorSet getPanelAnimatorSet(Boolean state){
        if(state){
            if(panelShowAnimatorSet==null){
                panelShowAnimatorSet = new AnimatorSet();
                int hight = headerPanel.getHeight();
                panelShowAnimatorSet.playTogether(
                        ObjectAnimator.ofFloat(headerPanel,"translationY",-hight,0)
                );
                panelShowAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isPannelHidden = false;
                        isAnimating = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        headerPanel.setVisibility(View.VISIBLE);
                        isAnimating = true;
                    }
                });
                panelShowAnimatorSet.setDuration(500);
            }
            return panelShowAnimatorSet;
        }else {
            if(panelHidenAnimatorSet==null){
                panelHidenAnimatorSet = new AnimatorSet();
                int hight = headerPanel.getHeight();
                panelHidenAnimatorSet.playTogether(
                        ObjectAnimator.ofFloat(headerPanel,"translationY",0,-hight)
                );
                panelHidenAnimatorSet.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        headerPanel.setVisibility(View.GONE);
                        isAnimating = false;
                        isPannelHidden = true;
                        //如果到达listview的顶部，重新展示出来顶部栏
                        if(((LinearLayoutManager)channellist.getLayoutManager()).findFirstVisibleItemPosition()==0){
                            getPanelAnimatorSet(true).start();
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAnimating = true;
                    }
                });
                panelHidenAnimatorSet.setDuration(500);
            }
            return panelHidenAnimatorSet;
        }
    }
}
