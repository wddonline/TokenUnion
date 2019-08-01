package com.iconiz.tokenunion.ui.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.login.adapter.CountryChoiceAdapter;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;

public class CountryChoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_choice);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.country_region);

        RecyclerView listView = findViewById(R.id.activity_country_choice_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration dividerDecoration = new LineDividerDecoration(this);
        int offset = DensityUtils.dip2px(this, 22);
        dividerDecoration.setLeftOffset(offset);
        dividerDecoration.setRightOffset(offset);
        listView.addItemDecoration(dividerDecoration);

        final String[] data = getResources().getStringArray(R.array.country_codes);
        CountryChoiceAdapter adapter = new CountryChoiceAdapter(this, data);
        adapter.setOnItemClickedListener(new CountryChoiceAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent();
                intent.putExtra("country", data[position].split("-")[1]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setAdapter(adapter);
    }

    public void onBackClicked(View view) {
        finish();
    }

}
