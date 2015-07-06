package me.shyboy.swipelayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/*
 *使用示例,
 * 只需要自己定义内容区和操作区的布局。
 * 设置一个继承SwipeLayoutAdapter的适配器
 * 实现setContentView和setActionView方法
 */
/**
 *　　　　　　　　┏┓　　　┏┓+ +
 *　　　　　　　┏┛┻━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　　┃ 　
 *　　　　　　　┃　　　━　　　┃ ++ + + +
 *　　　　　　 ████━████ ┃+
 *　　　　　　　┃　　　　　　　┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　　┃ + +
 *　　　　　　　┗━┓　　　┏━┛
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　
 *　　　　　　　　　┃　　　┃ + + + +
 *　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　　　┃　　　┃ + 　　　
 *　　　　　　　　　┃　　　┃
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　
 *　　　　　　　　　┃　 　　┗━━━┓ + +
 *　　　　　　　　　┃ 　　　　　　　┣┓
 *　　　　　　　　　┃ 　　　　　　　┏┛
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　　　　　　　┃┫┫　┃┫┫
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
public class MainActivity extends Activity {

    private SwipeLayoutAdapter mAdapter;
    private ListView mListView;
    private List<String> mData;
    private static int mCurrentIndex;
    //private GridView mGridView;

    //插入数据
    public void insertData(int n)
    {
        for(int i = 0 ; i < n; i++)
        {
            mData.add("hello " + mCurrentIndex++);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<String>();
        insertData(30);
        mCurrentIndex = 0;
        mListView = (ListView)findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }
        });
        //mGridView = (GridView)findViewById(R.id.gridview);
        mAdapter = new MyAdapater(this,R.layout.item_content,R.layout.item_action,mData);
        //mGridView.setAdapter(mAdapter);
        //如果要使用GridView，只需要注释掉mListView.setAdapter(mAdapter);去掉上方的注释
        mListView.setAdapter(mAdapter);
    }

    //适配器
    class MyAdapater extends SwipeLayoutAdapter<String>
    {
        private List<String> _data;
        public MyAdapater(Activity context,int contentViewResourceId,int actionViewResourceId,List<String> objects)
        {
            super(context,contentViewResourceId,actionViewResourceId,objects);
            _data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, int position, HorizontalScrollView parent) {
            TextView tv = (TextView)contentView.findViewById(R.id.tv);
            tv.setText(_data.get(position));
        }

        //实现setActionView方法
        @Override
        public void setActionView(View actionView,final int position, final HorizontalScrollView parent) {

            actionView.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parent.scrollTo(0, 0);
                            _data.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    dialog.show();

                }
            });

            /*actionView.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"star item - " + position,Toast.LENGTH_SHORT).show();
                }
            });*/

        }
    }

}
