package com.tokenunion.pro.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.adapter.MessageAdapter;
import com.tokenunion.pro.ui.mine.model.MessageBean;
import com.tokenunion.pro.ui.mine.widget.MessageFilterWindow;
import com.tokenunion.pro.R;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.SharedPreferenceUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tokenunion.pro.config.SharedPrefKeys.HAS_NEW_MESSAGE_KEY;
import static com.tokenunion.pro.config.SharedPrefKeys.LATEST_MESSAGE_ID_KEY;
import static com.tokenunion.pro.utils.EventBusUtils.EVENT_NO_NEW_MESSAGE;

public class MessageActivity extends BaseActivity {

    private static final String TAG = MessageActivity.class.getSimpleName();

    private RecyclerView mListView;
    private MessageFilterWindow mFilterWindow;

    private MessageAdapter mAdapter;
    private MessageBean mMessageBean;
    private List<MessageBean.MessageListBean> mMessageList = new ArrayList<>();
    private SmartRefreshLayout mSmartRefreshLayout;
    private int mMaxlastmessageid = -1;
    // 上一次的最旧的messageId
    private int mMinLastMessageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNewMessageData();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.msg_center);
//        ImageView menuView = findViewById(R.id.layout_common_actionbar_right);
//        menuView.setImageResource(R.drawable.ic_actionbar_more);
//        menuView.setVisibility(View.VISIBLE);
//        menuView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFilterWindow.showAtLocation(findViewById(android.R.id.content), Gravity.RIGHT, 0, 0);
//            }
//        });

        mListView = findViewById(R.id.activity_message_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this, LinearLayoutManager.VERTICAL, Color.TRANSPARENT);
        decoration.setItemSize(DensityUtils.dip2px(this, 12));
        mListView.addItemDecoration(decoration);

        mSmartRefreshLayout = findViewById(R.id.activity_message_refresh);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestNewMessageData();
                mSmartRefreshLayout.finishRefresh();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestHistoryMessageData();
                mSmartRefreshLayout.finishLoadMore();
            }
        });

        mFilterWindow = new MessageFilterWindow(this);
        mFilterWindow.setOnMenuSelectedListener(new MessageFilterWindow.OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int type) {
                switch (type) {
                    case MessageFilterWindow.TYPE_ALL:
                        break;
                    case MessageFilterWindow.TYPE_BILL_RECORD:
                        break;
                    case MessageFilterWindow.TYPE_INVITE_RECORD:
                        break;
                    case MessageFilterWindow.TYPE_MEM_LEVEL:
                        break;
                    case MessageFilterWindow.TYPE_ACCOUNT_SAFETY:
                        break;
                    case MessageFilterWindow.TYPE_NOTICE:
                        break;
                }
            }
        });

        refreshMessageData(false);
    }

    /**
     * 更新数据显示
     * @param isAddNewMessage 标识是否是增加新消息。如果是新增新消息，则倒序添加到已有数据顶部；否则，顺序添加到现有数据尾部
     */
    private void refreshMessageData(boolean isAddNewMessage){
        if(null == mMessageBean || null == mMessageBean.getMessageList() || mMessageBean.getMessageList().isEmpty()){
            LogUtil.e(TAG, "message data is empty.");
            return;
        }

        if(mMessageList.isEmpty()) {
            // 首次
            mMessageList = mMessageBean.getMessageList();
            mAdapter = new MessageAdapter(this, mMessageList);
            mListView.setAdapter(mAdapter);
        }else{
            if(isAddNewMessage) {
                // 上拉刷新-新消息
                int count = mMessageBean.getMessageList().size() - 1;
                // 倒序添加到数据顶部
                for (int i = count; i >= 0; i--) {
                    MessageBean.MessageListBean bean = mMessageBean.getMessageList().get(i);
                    if (!mMessageList.contains(bean)) {
                        mMessageList.add(0, bean);
                    }
                }
            }else{
                // loadMore数据，顺序添加到数据尾部
                for(MessageBean.MessageListBean bean: mMessageBean.getMessageList()){
                    if (!mMessageList.contains(bean)) {
                        // 如果mMessageList不包含新的项，则增加到mMessageList中
                        mMessageList.add(bean);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        }

        mAdapter.setOnItemClickedListener(new MessageAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(MessageBean.MessageListBean msg) {
//                switch (msg.getType()) {
//                    case Message.TYPE_MONEY:
//                        jumpToActivity(BillRecordActivity.class);
//                        break;
//                    case Message.TYPE_INVITE:
//                        jumpToActivity(InviteFriendActivity.class);
//                        break;
//                    case Message.TYPE_UPGRADE:
//                        jumpToActivity(MembershipCenterActivity.class);
//                        break;
//                }
            }
        });
    }

    public void onBackClicked(View view) {
        finish();
    }

    private void requestNewMessageData(){
        String mid = (-1 == mMaxlastmessageid ? "" : mMaxlastmessageid + "");
        MineApi.getMessageCenterListData(mid, "", MESSAGE_PAGE_SIZE, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                SharedPreferenceUtils.saveBoolean(MessageActivity.this, HAS_NEW_MESSAGE_KEY, false);
                EventBusUtils.post(new EventBusUtils.CommonEvent(EVENT_NO_NEW_MESSAGE));

                mMessageBean = (MessageBean) object;
                if(null == mMessageBean || null == mMessageBean.getMessageList() || mMessageBean.getMessageList().isEmpty()){
                    LogUtil.e(TAG, "message data is empty.");
                    return;
                }

                // 取最新的messageId
                mMaxlastmessageid = mMessageBean.getMessageList().get(0).getId();
                SharedPreferenceUtils.saveString(MessageActivity.this, LATEST_MESSAGE_ID_KEY, mMaxlastmessageid+ "");
                if(-1 == mMinLastMessageId) {
                    mMinLastMessageId = mMessageBean.getMessageList().get(mMessageBean.getTotalNum() - 1).getId();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshMessageData(true);
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(MessageActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 获取历史消息
     */
    private void requestHistoryMessageData(){

        String mid = mMinLastMessageId + "";
        MineApi.getMessageCenterListHistoryData(mid, "", MESSAGE_PAGE_SIZE, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mMessageBean = (MessageBean) object;

                if(null == mMessageBean || null == mMessageBean.getMessageList() || mMessageBean.getMessageList().isEmpty()){
                    LogUtil.e(TAG, "message data is empty.");
                    return;
                }

                // 取最后的messageId
                mMinLastMessageId = mMessageBean.getMessageList().get(mMessageBean.getTotalNum()-1).getId();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshMessageData(false);
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(MessageActivity.this, errMessage);
                    }
                });
            }
        });
    }

}
