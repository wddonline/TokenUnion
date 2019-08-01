package com.iconiz.tokenunion.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.invite.view.activity.InviteFriendActivity;
import com.iconiz.tokenunion.ui.mine.adapter.MessageAdapter;
import com.iconiz.tokenunion.ui.mine.model.Message;
import com.iconiz.tokenunion.ui.mine.widget.MessageFilterWindow;
import com.iconiz.tokenunion.ui.wallet.view.activity.BillRecordActivity;
import com.iconiz.tokenunion.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    private RecyclerView mListView;
    private MessageFilterWindow mFilterWindow;

    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.msg_center);
        ImageView menuView = findViewById(R.id.layout_common_actionbar_right);
        menuView.setImageResource(R.drawable.ic_actionbar_more);
        menuView.setVisibility(View.VISIBLE);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterWindow.showAtLocation(findViewById(android.R.id.content), Gravity.RIGHT, 0, 0);
            }
        });

        mListView = findViewById(R.id.activity_message_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this, LinearLayoutManager.VERTICAL, Color.TRANSPARENT);
        decoration.setItemSize(DensityUtils.dip2px(this, 12));
        mListView.addItemDecoration(decoration);

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

        List<Message> data = new ArrayList<>();

        Message msg = new Message();
        msg.setType(Message.TYPE_MONEY);
        msg.setTitle("今日收益");
        msg.setCreateTime(System.currentTimeMillis());
        msg.setContent("您2019年6月26日综合收益65489.3215ANY已入账，请注意查收。");
        msg.setMoney(65489.3215f);
        msg.setCurrency("ANY");
        data.add(msg);

        msg = new Message();
        msg.setType(Message.TYPE_MONEY);
        msg.setTitle("提现成功");
        msg.setCreateTime(System.currentTimeMillis());
        msg.setContent("您2019年6月26日申请的6985.3215TRON已处理，请注意资金变化。");
        msg.setMoney(-6985.3215f);
        msg.setCurrency("TRON");
        data.add(msg);


        msg = new Message();
        msg.setType(Message.TYPE_UPGRADE);
        msg.setTitle("升级为玉星级");
        msg.setCreateTime(System.currentTimeMillis());
        msg.setContent("恭喜您成功升级为玉星级会员，赶快查看会员福利吧！");
        data.add(msg);

        msg = new Message();
        msg.setType(Message.TYPE_INVITE);
        msg.setTitle("邀请好友成功");
        msg.setCreateTime(System.currentTimeMillis());
        msg.setContent("您邀请的好友UJHYYT已于2019年6月26日12:25成功注册。");
        data.add(msg);

        mAdapter = new MessageAdapter(this, data);
        mAdapter.setOnItemClickedListener(new MessageAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(Message msg) {
                switch (msg.getType()) {
                    case Message.TYPE_MONEY:
                        jumpToActivity(BillRecordActivity.class);
                        break;
                    case Message.TYPE_INVITE:
                        jumpToActivity(InviteFriendActivity.class);
                        break;
                    case Message.TYPE_UPGRADE:
                        jumpToActivity(MembershipCenterActivity.class);
                        break;
                }
            }
        });
        mListView.setAdapter(mAdapter);

    }

    public void onBackClicked(View view) {
        finish();
    }
}
