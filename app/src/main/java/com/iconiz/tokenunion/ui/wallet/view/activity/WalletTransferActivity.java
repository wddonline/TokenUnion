package com.iconiz.tokenunion.ui.wallet.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.iconiz.tokenunion.ui.wallet.model.PaymentCard;
import com.iconiz.tokenunion.ui.wallet.widget.PaymentCardChoiceWindow;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.flow.FlowLayout;
import com.yidaichu.android.common.view.flow.TagAdapter;
import com.yidaichu.android.common.view.flow.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class WalletTransferActivity extends BaseActivity {

    private TextView mCardVew;
    private TagFlowLayout mFlowLayout;
    private EditText mWealthPwdView;
    private EditText mAmountView;

    private List<PaymentCard> mAllCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transfer);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.transfer);

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wallet_transfer_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));

        mCardVew = findViewById(R.id.activity_wallet_transfer_card);
        mAmountView = findViewById(R.id.activity_wallet_transfer_amount);
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

        mFlowLayout = findViewById(R.id.activity_wallet_transfer_tabs);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                PaymentCard card = (PaymentCard)mFlowLayout.getAdapter().getItem(position);
                mCardVew.setText(card.getName());
                mCardVew.setTag(card);
                final String format = getString(R.string.available_balance_format_1);
                mAmountView.setHint(String.format(format, card.getBalance(), card.getName()));
                return true;
            }
        });

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

        mWealthPwdView = findViewById(R.id.activity_wallet_transfer_pwd);

        TextView pwdHintView = findViewById(R.id.activity_wallet_transfer_pwd_hint);
        pwdHintView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = pwdHintView.getText().toString();
        SpannableString span = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                jumpToActivity(ChangeCapitalPwdActivity.class);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        int index = text.indexOf('，') + 1;
        span.setSpan(clickableSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE524F"));
        span.setSpan(colorSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        pwdHintView.setText(span);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onWithdrawClicked(View view) {
        ToastUtils.showToast(this, R.string.transfer_success);
        finish();
    }

    public void onCardClicked(View view) {
        PaymentCardChoiceWindow choiceWindow = new PaymentCardChoiceWindow(this, mAllCards, view.getWidth());
        choiceWindow.setOnMenuSelectedListener(new PaymentCardChoiceWindow.OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(PaymentCard card) {
                mCardVew.setText(card.getName());
                mCardVew.setTag(card);
                final String format = getString(R.string.available_balance_format_1);
                mAmountView.setHint(String.format(format, card.getBalance(), card.getName()));
            }
        });
        choiceWindow.showAsDropDown(view);
    }

    public void onHidePwdClicked(View view) {
        view.setSelected(!view.isSelected());
        int inputType;
        if (view.isSelected()) {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
        } else {
            inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        mWealthPwdView.setInputType(inputType);
        mWealthPwdView.setSelection(mWealthPwdView.getText().length());
    }

    public void onCameraClicked(View view) {

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
