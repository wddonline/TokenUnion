package com.iconiz.tokenunion.ui.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.login.adapter.LanguageChoiceAdapter;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;

public class LanguageChoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choice);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.lan_choose);

        RecyclerView listView = findViewById(R.id.activity_language_choice_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration dividerDecoration = new LineDividerDecoration(this);
        int offset = DensityUtils.dip2px(this, 22);
        dividerDecoration.setLeftOffset(offset);
        dividerDecoration.setRightOffset(offset);
        listView.addItemDecoration(dividerDecoration);

        String[] data = getResources().getStringArray(R.array.support_lans);
        LanguageChoiceAdapter adapter = new LanguageChoiceAdapter(this, data);
        adapter.setOnItemClickedListener(new LanguageChoiceAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                finish();
            }
        });
        listView.setAdapter(adapter);
    }

    public void onBackClicked(View view) {
        finish();
    }
}
