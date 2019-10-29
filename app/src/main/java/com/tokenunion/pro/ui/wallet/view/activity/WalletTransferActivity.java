package com.tokenunion.pro.ui.wallet.view.activity;

import android.content.Intent;
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
import androidx.annotation.Nullable;

import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.mine.view.activity.ChangeCapitalPwdActivity;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.ui.wallet.model.PaymentCard;
import com.tokenunion.pro.ui.wallet.model.WithdrawListBean;
import com.tokenunion.pro.ui.wallet.widget.PaymentCardChoiceWindow;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.GoogleCodeVerifyDlg;
import com.tokenunion.pro.widget.LoadView;
import com.tokenunion.pro.zxing.android.CaptureActivity;
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

    private TextView mSymbolVew;
    private TextView mSymbolHintVew;
    private TagFlowLayout mFlowLayout;
    private EditText mWealthPwdView;
    private EditText mAmountView;
    private EditText mTranseUserName;
    private LoadView mLoadingView;

    private List<PaymentCard> mAllCards = new ArrayList<>();
    private List<WithdrawListBean> mWithdrawListBeans;
    private String mGoogleCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transfer);
        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doRequest();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.transfer);
        mSymbolVew = findViewById(R.id.activity_wallet_transfer_card);
        mSymbolHintVew = findViewById(R.id.activity_wallet_transfer_symbol_hint);
        mAmountView = findViewById(R.id.activity_wallet_transfer_amount);
        mTranseUserName = findViewById(R.id.activity_wallet_transfer_username);
        mWealthPwdView = findViewById(R.id.activity_wallet_transfer_pwd);
        mLoadingView = findViewById(R.id.data_loading);

        SmartRefreshLayout refreshLayout = findViewById(R.id.activity_wallet_transfer_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        refreshLayout.setRefreshFooter(new FalsifyFooter(this));
    }

    private void updateData(){
        if(null == mWithdrawListBeans){
            return;
        }
//        final String[] names = {"ANY", "BTC", "ETH", "EOS", "ADA", "TRX", "BCH"};
        PaymentCard paymentCard;
        for (int i = 0; i < mWithdrawListBeans.size(); i++) {
            paymentCard = new PaymentCard();
            paymentCard.setId(i);
            paymentCard.setName(mWithdrawListBeans.get(i).getSymbol());
            paymentCard.setBalance(mWithdrawListBeans.get(i).getAmount());//"7895269.5487");
            paymentCard.setFee(mWithdrawListBeans.get(i).getFeeAmount());//"100");
            paymentCard.setMinOut(mWithdrawListBeans.get(i).getMinAmount());//"1000");
            mAllCards.add(paymentCard);
        }

        mFlowLayout = findViewById(R.id.activity_wallet_transfer_tabs);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                PaymentCard card = (PaymentCard)mFlowLayout.getAdapter().getItem(position);
                onCardClicked(card);
                mSymbolHintVew.setVisibility(View.GONE);
                return true;
            }
        });

        List<PaymentCard> cards = new ArrayList<>();
        int allCount = mWithdrawListBeans.size();
        for( int i=0; i<allCount; i++){
            if(Configs.SURPORT_SYMBOL_LIST.contains(mAllCards.get(i).getName())) {
                PaymentCard card = mAllCards.get(i);
                cards.add(card);
            }
        }

        CardTagAdapter tagAdapter = new CardTagAdapter(cards);
        mFlowLayout.setAdapter(tagAdapter);

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
        if(!verifyInput()){
            return;
        }
        showGoogleCodeDlg();
    }

    public void onCardClicked(View view) {
        PaymentCardChoiceWindow choiceWindow = new PaymentCardChoiceWindow(this, mAllCards, view.getWidth());
        choiceWindow.setOnMenuSelectedListener(new PaymentCardChoiceWindow.OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(PaymentCard card) {
                onCardClicked(card);
            }
        });
        choiceWindow.showAsDropDown(view);
    }

    private void onCardClicked(PaymentCard card){
        mSymbolVew.setText(card.getName());
        mSymbolVew.setTag(card);
        final String format = getString(R.string.available_balance_format_1);
        mAmountView.setHint(String.format(format, card.getBalance(), card.getName()));
        mSymbolHintVew.setVisibility(View.GONE);
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
        checkCarmeraPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {

            case REQUEST_CODE_SCAN:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String content = data.getStringExtra(CaptureActivity.KEY_CODEDCONTENT);
                        mTranseUserName.setText(content);
                    }
                }
                break;

            default:
                break;
        }

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

    private void doRequest(){
        mLoadingView.setVisibility(View.VISIBLE);
        WalletApi.getWithdrawList(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mWithdrawListBeans = (List<WithdrawListBean>) object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateData();
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(WalletTransferActivity.this, errMessage);
                        mLoadingView.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    private boolean verifyInput(){
        if (mSymbolVew.getText().length() < 1) {
            mSymbolVew.requestFocus();
            mSymbolHintVew.setVisibility(View.VISIBLE);
            return false;
        } else {
            mSymbolHintVew.setVisibility(View.GONE);
        }

        if (mAmountView.getText().length() < 1) {
            mAmountView.requestFocus();
            findViewById(R.id.activity_wallet_transfer_amount_hint).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.activity_wallet_transfer_amount_hint).setVisibility(View.GONE);
        }

        if (mWealthPwdView.getText().length() < 1) {
            mWealthPwdView.requestFocus();
            findViewById(R.id.activity_wallet_transfer_pwd_hint).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.activity_wallet_transfer_pwd_hint).setVisibility(View.GONE);
        }
        return true;
    }

    private void transferCommit(){
        mLoadingView.setVisibility(View.VISIBLE);
        WalletApi.doTransfer(
                mSymbolVew.getText().toString(),
                mTranseUserName.getText().toString(),
                mAmountView.getText().toString(),
                mWealthPwdView.getText().toString(),
                mGoogleCode,
                mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(WalletTransferActivity.this, getString(R.string.transfer_success));
                                mLoadingView.setVisibility(View.GONE);
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(WalletTransferActivity.this, errMessage);
                                mLoadingView.setVisibility(View.GONE);
                            }
                        });
                    }
                }
        );
    }

    private void showGoogleCodeDlg(){
        final GoogleCodeVerifyDlg dlg = new GoogleCodeVerifyDlg(WalletTransferActivity.this);
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

                transferCommit();

            }
        });
    }
}
