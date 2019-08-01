package com.iconiz.tokenunion.ui.wallet.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.wallet.model.PaymentCard;
import com.iconiz.tokenunion.ui.wallet.widget.PaymentCardChoiceWindow;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.iconiz.tokenunion.widget.NetworkImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.flow.FlowLayout;
import com.yidaichu.android.common.view.flow.TagAdapter;
import com.yidaichu.android.common.view.flow.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class WalletChargeActivity extends BaseActivity {

    private TextView mCardVew;
    private TagFlowLayout mFlowLayout;
    private NetworkImageView mQrView;
    private TextView mQrContentView;

    private List<PaymentCard> mAllCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_charge);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.rush_charge);

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wallet_charge_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));

        mCardVew = findViewById(R.id.activity_wallet_charge_card);

        mAllCards = new ArrayList<>();
        final String[] names = {"ANY", "BTC", "ETH", "EOS", "ADA", "TRX", "BCH"};
        PaymentCard paymentCard;
        for (int i = 0; i < names.length; i++) {
            paymentCard = new PaymentCard();
            paymentCard.setId(i);
            paymentCard.setName(names[i]);
            paymentCard.setBalance("7895269.5487");
            mAllCards.add(paymentCard);
        }

        mFlowLayout = findViewById(R.id.activity_wallet_charge_tabs);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                PaymentCard card = (PaymentCard)mFlowLayout.getAdapter().getItem(position);
                mCardVew.setText(card.getName());
                mCardVew.setTag(card);
                return true;
            }
        });
        mQrView = findViewById(R.id.activity_wallet_charge_qr);
        mQrView.setImageUri("http://img3.cache.netease.com/game/2013/11/26/20131126143638f53f4.png");
        mQrContentView = findViewById(R.id.activity_wallet_charge_qr_str);
        mQrContentView.setText("Ox1q1w2e3r4t5y6u7io93jfh48wjdsf9082hukjieg");

        List<PaymentCard> cards = new ArrayList<>();
        PaymentCard card = new PaymentCard();
        card.setId(0);
        card.setName("ANY");
        card.setBalance("7895269.5487");
        cards.add(card);

        card = new PaymentCard();
        card.setId(1);
        card.setName("BTC");
        card.setBalance("7895269.5487");
        cards.add(card);

        card = new PaymentCard();
        card.setId(2);
        card.setName("ETH");
        card.setBalance("7895269.5487");
        cards.add(card);

        card = new PaymentCard();
        card.setId(3);
        card.setName("EOS");
        card.setBalance("7895269.5487");
        cards.add(card);

        CardTagAdapter tagAdapter = new CardTagAdapter(cards);
        mFlowLayout.setAdapter(tagAdapter);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onBackWalletClicked(View view) {
        finish();
    }

    public void onCopyCodeClicked(View view) {
        AppUtils.copyText(this, mQrContentView.getText().toString());
        ToastUtils.showToast(this, R.string.copy_success);
    }

    public void onCardClicked(View view) {
        PaymentCardChoiceWindow choiceWindow = new PaymentCardChoiceWindow(this, mAllCards, view.getWidth());
        choiceWindow.setOnMenuSelectedListener(new PaymentCardChoiceWindow.OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(PaymentCard card) {
                mCardVew.setText(card.getName());
                mCardVew.setTag(card);
            }
        });
        choiceWindow.showAsDropDown(view);
    }

    private class CardTagAdapter extends TagAdapter<PaymentCard> {

        public CardTagAdapter(List<PaymentCard> cards) {
            super(cards);
        }

        @Override
        public View getView(FlowLayout parent, int position, PaymentCard tag) {
            TextView tagView = new TextView(getBaseContext());
            tagView.setBackgroundResource(R.drawable.bg_payment_card);
            tagView.setTextColor(Color.parseColor("#DE524F"));
            tagView.setGravity(Gravity.CENTER_VERTICAL);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtils.sp2px(getBaseContext(), 12));
            tagView.setText(tag.getName());
            tagView.setTag(tag);
            return tagView;
        }
    }
}
