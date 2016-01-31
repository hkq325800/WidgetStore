package me.yokeyword.itemtouchhelperdemo.demochannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.itemtouchhelperdemo.R;
import me.yokeyword.itemtouchhelperdemo.helper.ItemDragHelperCallback;

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mRecy = (RecyclerView) findViewById(R.id.recy);
        init();
    }

    private void init() {
        //数据源的准备
        final List<ChannelEntity> items = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道asasd中23sddfasdf" + i);
            items.add(entity);
        }
        final List<ChannelEntity> otherItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("其他asasdfdfasdf" + i);
            otherItems.add(entity);
        }

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        final ChannelAdapter adapter = new ChannelAdapter(this, helper, items, otherItems);
        //StaggeredGridLayoutManager sManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager manager = new GridLayoutManager(this, 6);
        //LinearLayoutManager lManager = new LinearLayoutManager(this);

        //为RecycleView设置类型管理器
        mRecy.setLayoutManager(manager);
        //将RecycleView和ItemTouchHelper相关联
        helper.attachToRecyclerView(mRecy);
        //为了用RecyclerView创建一个带header的grid 在header所处的位置返回和span count相等的span size
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                int temp = viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 2 : 6;
                return temp;
            }
        });
        mRecy.setAdapter(adapter);
        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position, int viewType) {
                switch (viewType) {
                    case ChannelAdapter.TYPE_MY:
                        Toast.makeText(ChannelActivity.this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
                        break;
                    case ChannelAdapter.TYPE_OTHER:
                        Toast.makeText(ChannelActivity.this, otherItems.get(position).getName(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
