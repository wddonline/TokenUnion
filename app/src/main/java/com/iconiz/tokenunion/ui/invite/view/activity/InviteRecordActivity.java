package com.iconiz.tokenunion.ui.invite.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.invite.adapter.InviteRecordAdapter;
import com.iconiz.tokenunion.ui.invite.model.InviteRecord;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class InviteRecordActivity extends BaseActivity {

    private RecyclerView mListView;
    private ViewGroup headerView;
    private TextView mFilterView;

    private InviteRecordAdapter mAdapter;
    private int[] mTimeHeaders;
    private int[] mSizeHeaders;
    private int[] mLevelHeaders;
    private List<List<InviteRecord>> mTimeData;
    private List<List<InviteRecord>> mSizeData;
    private List<List<InviteRecord>> mLevelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_record);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mTimeHeaders = new int[]{R.string.username, R.string.header_register_time};
        mTimeData = new ArrayList<>();
        List<InviteRecord> records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("2019-06-26  15:25:14"));
        mTimeData.add(records);

        mSizeHeaders = new int[]{R.string.username, R.string.header_size, R.string.header_ratio};
        mSizeData = new ArrayList<>();
        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord("123@2156.216"));
        records.add(new InviteRecord("21236989.3256"));
        records.add(new InviteRecord("51.0%"));
        mSizeData.add(records);

        mLevelHeaders = new int[]{R.string.header_star_level, R.string.header_people, R.string.header_team, R.string.header_ratio};
        mLevelData = new ArrayList<>();
        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_STAR, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_JADE, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_GOLD, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_TITANIUM, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_PLATINUM, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);

        records = new ArrayList<>();
        records.add(new InviteRecord(InviteRecord.TYPE_DIAMOND, ""));
        records.add(new InviteRecord("123"));
        records.add(new InviteRecord("12"));
        records.add(new InviteRecord("51.0%"));
        mLevelData.add(records);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.invite_friend);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mFilterView = findViewById(R.id.activity_invite_record_filter_name);
        mListView = findViewById(R.id.activity_invite_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this, LinearLayoutManager.VERTICAL, Color.parseColor("#EDEDED"));
        mListView.addItemDecoration(decoration);

        headerView = findViewById(R.id.activity_invite_record_header);

        setTableData(mTimeHeaders, mTimeData);
    }

    private void setTableData(int[] headers, List<List<InviteRecord>> records) {
        TextView textView;
        for (int i = 0; i < headerView.getChildCount(); i++) {
            textView = (TextView) headerView.getChildAt(i);
            if (i < headers.length) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(headers[i]);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
        if (mAdapter == null) {
            mAdapter = new InviteRecordAdapter(getBaseContext(), records);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.refreshData(records);
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onTypeClicked(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_invite_record_cate);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int[] headers;
                List<List<InviteRecord>> records;
                switch (item.getItemId()) {
                    case R.id.menu_invite_record_cate_time:
                        headers = mTimeHeaders;
                        records = mTimeData;
                        mFilterView.setText(R.string.time_distribution);
                        break;
                    case R.id.menu_invite_record_cate_size:
                        headers = mSizeHeaders;
                        records = mSizeData;
                        mFilterView.setText(R.string.size_distribution);
                        break;
                    default:
                        headers = mLevelHeaders;
                        records = mLevelData;
                        mFilterView.setText(R.string.star_distribution);
                        break;
                }
                setTableData(headers, records);
                return true;
            }
        });
        popupMenu.show();
    }

    public void onInviteClicked(View view) {
        jumpToActivity(InviteFriendActivity.class);
    }
}
