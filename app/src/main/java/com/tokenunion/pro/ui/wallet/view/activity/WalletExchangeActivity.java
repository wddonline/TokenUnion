package com.tokenunion.pro.ui.wallet.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.model.ExchangeConfigBean;
import com.tokenunion.pro.ui.wallet.model.PaymentCard;
import com.tokenunion.pro.ui.wallet.model.TradeBalanceBean;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.GoogleCodeVerifyDlg;
import com.tokenunion.pro.widget.LoadView;
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

public class WalletExchangeActivity extends BaseActivity {

    private static final String TAG = WalletExchangeActivity.class.getSimpleName();
    private TextView mCardVew;
    private TagFlowLayout mCardFlowLayout;
    private EditText mAmountView;
    private TextView mAmountHintView;
    private TextView mDstCardView;
    private TagFlowLayout mDstCardFlowLayout;
    private EditText mDstAmountView;
    private EditText mWealthPwdView;
    private TextView mExchangeHintView;
    private TextView mPwdHintView;
    private String mGoogleCode;
    private LoadView mLoadingView;

    private List<PaymentCard> mAllCards;
    // 用户的可兑换资产数量
    private String mSymbalAmount;
    // 最小兑换数
    private String mMinExchangeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_exchange);
        initData();
        initViews();
        getTradeBalance();
    }

    @Override
    protected void initData() {
        requestExchangeConfig();
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.flash_exchange);

        mLoadingView = findViewById(R.id.data_loading);
        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wallet_exchange_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));

        mCardVew = findViewById(R.id.activity_wallet_exchange_card);
        mAmountView = findViewById(R.id.activity_wallet_exchange_amount);
        mAmountHintView = findViewById(R.id.activity_wallet_exchange_amount_hint);

        mAllCards = new ArrayList<>();
        final String[] names = {"ANY"};
        PaymentCard paymentCard;
        for (int i = 0; i < names.length; i++) {
            paymentCard = new PaymentCard();
            paymentCard.setId(i);
            paymentCard.setName(names[i]);
            paymentCard.setBalance("7895269.5487");
            paymentCard.setFee("100");
            mAllCards.add(paymentCard);
        }

        mCardFlowLayout = findViewById(R.id.activity_wallet_exchange_tabs);
//        mCardFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                PaymentCard card = (PaymentCard) mCardFlowLayout.getAdapter().getItem(position);
//                onCardSelected(card);
//                return true;
//            }
//        });

        mDstCardView = findViewById(R.id.activity_wallet_exchange_dst_card);
        mDstAmountView = findViewById(R.id.activity_wallet_exchange_dst_amount);

        mDstCardFlowLayout = findViewById(R.id.activity_wallet_exchange_dst_tabs);
        mDstCardFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                PaymentCard card = (PaymentCard) mDstCardFlowLayout.getAdapter().getItem(position);
//                mDstCardView.setText(card.getName());
//                mDstCardView.setTag(card);
//                final String format = getString(R.string.available_balance_format_1);
//                mDstAmountView.setHint(String.format(format, card.getBalance(), card.getName()));
                return true;
            }
        });

        List<PaymentCard> cards = new ArrayList<>();
        PaymentCard card = new PaymentCard();
        card.setId(0);
        card.setName("ANY");
        card.setBalance("7895269.5487");
        card.setFee("100");
        cards.add(card);

        CardTagAdapter tagAdapter = new CardTagAdapter(cards);
        mCardFlowLayout.setAdapter(tagAdapter);

        List<PaymentCard> cardsTo = new ArrayList<>();
        PaymentCard cardTo = new PaymentCard();
        cardTo.setId(0);
        cardTo.setName("ETH");
        cardTo.setBalance("7895269.5487");
        cardTo.setFee("100");
        cardsTo.add(cardTo);
        CardTagAdapter tagAdapterTo = new CardTagAdapter(cardsTo);
        mDstCardFlowLayout.setAdapter(tagAdapterTo);

        mWealthPwdView = findViewById(R.id.activity_wallet_exchange_pwd);

        mPwdHintView = findViewById(R.id.activity_wallet_exchange_pwd_hint);
        mPwdHintView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = mPwdHintView.getText().toString();
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
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0080FF"));
        span.setSpan(colorSpan, index, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mPwdHintView.setText(span);

        mExchangeHintView = findViewById(R.id.activity_wallet_exchange_hint);
    }

    private void onCardSelected(PaymentCard card) {
        mCardVew.setText(card.getName());
        mCardVew.setTag(card);
        final String format = getString(R.string.available_balance_format_1);
        mAmountView.setHint(String.format(format, card.getBalance(), card.getName()));

        String text = getString(R.string.exchange_fee_format);
        String item = card.getFee() + " " + card.getName();
        text = String.format(text, item);
        SpannableString span = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        int index = text.indexOf(item);
        span.setSpan(colorSpan, index, index + item.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mExchangeHintView.setText(span);
        mExchangeHintView.setVisibility(View.VISIBLE);
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onExchangeClicked(View view) {

        if(!verifyInput()){
            return;
        }
        showGoogleCodeDlg();

//        ToastUtils.showToast(this, R.string.exchange_success);
//        finish();
    }

    private void showGoogleCodeDlg(){
        final GoogleCodeVerifyDlg dlg = new GoogleCodeVerifyDlg(WalletExchangeActivity.this);
        dlg.show();

        dlg.setOnDialogListener(new GoogleCodeVerifyDlg.OnDialogListener() {
            @Override
            public void onCloseClick() {
                dlg.dismiss();
            }

            @Override
            public void onInputComplete() {
                mGoogleCode = dlg.getGoogleCode();
                dlg.dismiss();

                doCommit();
            }
        });
    }

    public void onCardClicked(final View view) {
//        PaymentCardChoiceWindow choiceWindow = new PaymentCardChoiceWindow(this, mAllCards, view.getWidth());
//        choiceWindow.setOnMenuSelectedListener(new PaymentCardChoiceWindow.OnMenuSelectedListener() {
//            @Override
//            public void onMenuSelected(PaymentCard card) {
//                final String format = getString(R.string.available_balance_format_1);
//                switch (view.getId()) {
//                    case R.id.activity_wallet_exchange_card:
//                        onCardSelected(card);
//                        break;
//                    case R.id.activity_wallet_exchange_dst_card:
//                        mDstCardView.setText(card.getName());
//                        mDstCardView.setTag(card);
//                        mDstAmountView.setHint(String.format(format, card.getBalance(), card.getName()));
//                        break;
//                }
//
//            }
//        });
//        choiceWindow.showAsDropDown(view);
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

    private class CardTagAdapter extends TagAdapter<PaymentCard> {

        public CardTagAdapter(List<PaymentCard> cards) {
            super(cards);
        }

        @Override
        public View getView(FlowLayout parent, int position, PaymentCard tag) {
            TextView tagView = new TextView(getBaseContext());
            tagView.setBackgroundResource(R.drawable.bg_payment_card);
            tagView.setTextColor(Color.parseColor("#CAAC89"));
            tagView.setGravity(Gravity.CENTER_VERTICAL);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtils.sp2px(getBaseContext(), 12));
            tagView.setText(tag.getName());
            tagView.setTag(tag);
            return tagView;
        }
    }

    private void updateAmountHint(String symbolName){
        if(null != mMinExchangeAmount && null != mSymbalAmount) {
            final String format = getString(R.string.available_balance_format_2);
            mAmountView.setHint(String.format(format, mSymbalAmount, symbolName, mMinExchangeAmount, symbolName));
        }
    }

    private void requestExchangeConfig(){
        WalletApi.getExchangeConfig(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                List<ExchangeConfigBean> list = (List<ExchangeConfigBean>) object;
                if(null == list || list.isEmpty()){
                    LogUtil.e(TAG, "数据为空");
                    return;
                }
                ExchangeConfigBean bean = list.get(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 可用和最小
                        mMinExchangeAmount = bean.getMinAmount()+ "";
                        updateAmountHint(bean.getFromSymbol());

                        // 手续费率
                        String text = getString(R.string.exchange_fee_format);
                        String item = bean.getFeeRate() * 100+ " %";
                        text = String.format(text, item);
                        SpannableString span = new SpannableString(text);
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
                        int index = text.indexOf(item);
                        span.setSpan(colorSpan, index, index + item.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        mExchangeHintView.setText(span);
                        mExchangeHintView.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(WalletExchangeActivity.this, errMessage);
                    }
                });

            }
        });
    }

    private boolean verifyInput(){
        if(TextUtils.isEmpty(mAmountView.getText())){
            mAmountView.requestFocus();
            mAmountHintView.setVisibility(View.VISIBLE);
            return false;
        }else {
            mAmountHintView.setVisibility(View.GONE);
        }

        if(TextUtils.isEmpty(mWealthPwdView.getText())){
            mWealthPwdView.requestFocus();
            mPwdHintView.setVisibility(View.VISIBLE);
            return false;
        }else {
            mPwdHintView.setVisibility(View.GONE);
        }

        return true;
    }

    private void doCommit(){
        mLoadingView.setVisibility(View.VISIBLE);
        WalletApi.doExchange(mAmountView.getText().toString(), mCardVew.getText().toString(),
                mDstCardView.getText().toString(), mWealthPwdView.getText().toString(),
                mGoogleCode, mActive, new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingView.setVisibility(View.GONE);
                                ToastUtils.showToast(WalletExchangeActivity.this, R.string.exchange_success);
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailed(String errCode, String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingView.setVisibility(View.GONE);
                                ToastUtils.showToast(WalletExchangeActivity.this, errMessage);
                            }
                        });

                    }
                });
    }

    /**
     * 获取币种的可用余额
     */
    private void getTradeBalance(){
        String symbol = mCardVew.getText().toString();
        WalletApi.getTradeBalance(symbol,  mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final TradeBalanceBean tradeBalanceBean = (TradeBalanceBean) object;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!tradeBalanceBean.getSymbolBals().isEmpty()) {
                            mSymbalAmount = tradeBalanceBean.getSymbolBals().get(0).getAmount();
                            updateAmountHint(symbol);
                        }
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(WalletExchangeActivity.this, errMessage);
                    }
                });

            }
        });
    }
}
