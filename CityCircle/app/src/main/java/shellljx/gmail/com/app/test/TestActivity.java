package shellljx.gmail.com.app.test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import shellljx.gmail.com.app.activity.BaseActivity;
import shellljx.gmail.com.widget.Dialog.PhoneVerDialog;
import shellljx.gmail.com.widget.R;
import shellljx.gmail.com.widget.SlideExpandableListView.ActionSlideExpandableListView;

/**
 * Created by shell on 15-9-12.
 */
public class TestActivity extends BaseActivity{

    private PhoneVerDialog dialog;

    @Bind(R.id.list)
    ActionSlideExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        listView.setAdapter(buildDummyData());
        listView.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {
            @Override
            public void onClick(View itemView, View clickedView, int position) {
            }
        },R.id.expandable_toggle_button,R.id.details);
    }

    public ListAdapter buildDummyData() {
        final int SIZE = 20;
        String[] values = new String[SIZE];
        for(int i=0;i<SIZE;i++) {
            values[i] = "Item "+i;
        }
        return new ArrayAdapter<String>(
                this,
                R.layout.item_test_list,
                R.id.text,
                values
        );
    }


}
