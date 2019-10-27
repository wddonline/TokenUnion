package com.tokenunion.pro.ui.invite.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.invite.adapter.InviteRecordAdapter;
import com.tokenunion.pro.ui.invite.model.InviteRecord;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.DistirbutionVosBean;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class InviteRecordActivity extends BaseActivity {
    private static final String TAG = InviteRecordActivity.class.getSimpleName();
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

    /**
     * 团队规模（USDT）
     */
    private String mTeamBal;
    private TextView mTextViewTeamBal;
    /**
     * 团队人数
     */
    private int mTeamNum;
    private TextView mTextViewTeamNum;

    private Handler mHandler = new VosTypeMessage();
    private class VosTypeMessage extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    displayTimeVos();
                    break;

                case 2:
                    // 规模分布
                    displaySizeVos();
                    break;

                case 3:
                    // 星级分布
                    displayLevelVos();
                    break;

                    default:
                        break;

            }
            mTextViewTeamBal.setText(mTeamBal);
            mTextViewTeamNum.setText(mTeamNum+ "");
        }
    }

    /**
     * 展示 时间分布
     */
    private void displayTimeVos() {
        mTimeHeaders = new int[]{R.string.username, R.string.header_register_time};
        mTimeData = new ArrayList<>();
        for(int i=0; i<mTimeVosBeanList.size(); i++){
            List<InviteRecord> records = new ArrayList<>();
            records.add(new InviteRecord(mTimeVosBeanList.get(i).getUserName()));// //"123@2156.216"));
            records.add(new InviteRecord(mTimeVosBeanList.get(i).getSignTime()));//"2019-06-26  15:25:14"));
            mTimeData.add(records);
        }
        setTableData(mTimeHeaders, mTimeData);
    }

    /**
     * 展示 规模分布
     */
    private void displaySizeVos() {
        mSizeHeaders = new int[]{R.string.username, R.string.header_size, R.string.header_ratio};
        mSizeData = new ArrayList<>();
        for(int i=0; i<mScaleVosBeanList.size(); i++){
            List<InviteRecord> records = new ArrayList<>();
            records.add(new InviteRecord(mScaleVosBeanList.get(i).getUserName()));
            records.add(new InviteRecord(mScaleVosBeanList.get(i).getTeamBal()));
            records.add(new InviteRecord(mScaleVosBeanList.get(i).getTeamRatio()+ " %"));
            mSizeData.add(records);
        }
    }

    /**
     * 展示 星级分布
     */
    private void displayLevelVos() {
        mLevelHeaders = new int[]{R.string.header_star_level, R.string.header_people, R.string.header_team, R.string.header_ratio};
        mLevelData = new ArrayList<>();
        for(int i=0; i<mLevelVosBeanList.size(); i++){
            int level = BusinessUtils.getLevelId(mLevelVosBeanList.get(i).getLevelCode());
            if(level<0){
                break;
            }
            List<InviteRecord> records = new ArrayList<>();
            records.add(new InviteRecord(level+1, ""));//InviteRecord.TYPE_STAR, ""));
            records.add(new InviteRecord(mLevelVosBeanList.get(i).getLevelNum()+ "")); // 人数
            records.add(new InviteRecord(mLevelVosBeanList.get(i).getTeamNum()+ "")); // 团队分布
            records.add(new InviteRecord(mLevelVosBeanList.get(i).getLevelRatio()+ " %")); // 占比
            mLevelData.add(records);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_record);
        initData();
        initViews();

        requestList();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initViews() {
        mTextViewTeamBal = findViewById(R.id.activity_invite_record_team_bal);
        mTextViewTeamNum = findViewById(R.id.activity_invite_record_team_num);

        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.invite_friend);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mFilterView = findViewById(R.id.activity_invite_record_filter_name);
        mListView = findViewById(R.id.activity_invite_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this);
        final int MARGIN = DensityUtils.dip2px(this, 20);
        decoration.setLeftOffset(MARGIN);
        decoration.setRightOffset(MARGIN);
        mListView.addItemDecoration(decoration);

        headerView = findViewById(R.id.activity_invite_record_header);
    }

    private void setTableData(int[] headers, List<List<InviteRecord>> records) {
        if(null == headers || null == records){
            return;
        }
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

    /**
     * 时间分布
     */
    private List<DistirbutionVosBean.TimeVosBean> mTimeVosBeanList;
    /**
     * 规模分布
     */
    private List<DistirbutionVosBean.ScaleVosBean> mScaleVosBeanList;
    /**
     * 星级分布
     */
    private List<DistirbutionVosBean.LevelVosBean> mLevelVosBeanList;

    private void requestList(){
        MineApi.getTeamInvitedDistirbutionList(1, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                DistirbutionVosBean distirbutionVosBean = (DistirbutionVosBean)object;
                if(null != distirbutionVosBean) {
                    mTimeVosBeanList = distirbutionVosBean.getTimeVos();
                    mTeamBal = distirbutionVosBean.getTeamBal();
                    mTeamNum = distirbutionVosBean.getTeamNum();
                    mHandler.sendEmptyMessage(1);
                }else {
                    LogUtil.e(TAG, "distirbutionVosBean is null.");
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InviteRecordActivity.this, errMessage);
                    }
                });
            }
        });

        MineApi.getTeamInvitedDistirbutionList(2, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                DistirbutionVosBean distirbutionVosBean = (DistirbutionVosBean)object;
                if(null != distirbutionVosBean) {
                    mScaleVosBeanList = distirbutionVosBean.getScaleVos();
                    mTeamBal = distirbutionVosBean.getTeamBal();
                    mTeamNum = distirbutionVosBean.getTeamNum();
                    mHandler.sendEmptyMessage(2);
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InviteRecordActivity.this, errMessage);
                    }
                });
            }
        });

        MineApi.getTeamInvitedDistirbutionList(3, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                DistirbutionVosBean distirbutionVosBean = (DistirbutionVosBean)object;
                if(null != distirbutionVosBean) {
                    mLevelVosBeanList = distirbutionVosBean.getLevelVos();
                    mTeamBal = distirbutionVosBean.getTeamBal();
                    mTeamNum = distirbutionVosBean.getTeamNum();
                    mHandler.sendEmptyMessage(3);
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InviteRecordActivity.this, errMessage);
                    }
                });

            }
        });
    }
}
