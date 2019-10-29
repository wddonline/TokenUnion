package com.tokenunion.pro.ui.wallet.view.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.wallet.model.PaymentCard;
import com.tokenunion.pro.ui.wallet.model.RechargeListBean;
import com.tokenunion.pro.ui.wallet.widget.PaymentCardChoiceWindow;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.wallet.WalletApi;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.DlgWalletMemoTips;
import com.tokenunion.pro.widget.LoadView;
import com.tokenunion.pro.widget.NetworkImageView;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yidaichu.android.common.view.flow.FlowLayout;
import com.yidaichu.android.common.view.flow.TagAdapter;
import com.yidaichu.android.common.view.flow.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class WalletChargeActivity extends BaseActivity {
    private static final String TAG = WalletChargeActivity.class.getSimpleName();
    private TextView mCardVew;
    private TagFlowLayout mFlowLayout;
    private NetworkImageView mQrView;
    private TextView mQrContentView;
    private TextView mQrCopy;
    private LoadView mLoadingView;
    private View mViewContainerAddr;
    private View mViewContainerMemo;

    // 带memo的币种的币地址和memo值
    private TextView mTextViewAddrByMemo;
    private TextView mTextViewTagByMemo;
    private TextView mTextViewWarningByMemo;

    private List<PaymentCard> mAllCards;
    private List<RechargeListBean> mRechargeListBeans;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_charge);
        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doRequest();
            }
        },100);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.rush_charge);

        // "普通币"的显示viewgroup
        mViewContainerAddr = findViewById(R.id.activity_wallet_charge_addr_container);
        // "带memo的币"的显示Viewgroup
        mViewContainerMemo = findViewById(R.id.activity_wallet_charge_memo_container);

        mTextViewAddrByMemo = findViewById(R.id.layout_wallet_charge_memo_addr);
        mTextViewTagByMemo = findViewById(R.id.layout_wallet_charge_memo_tag);
        mTextViewWarningByMemo = findViewById(R.id.layout_wallet_charge_memo_warning);

        mCardVew = findViewById(R.id.activity_wallet_charge_card);
        mQrCopy = findViewById(R.id.activity_wallet_charge_qr_copy);
        mLoadingView = findViewById(R.id.data_loading);
    }

    private void updateData(){
        if(null == mRechargeListBeans || mRechargeListBeans.isEmpty()){
            return;
        }
        mAllCards = new ArrayList<>();
        for(int i=0; i< mRechargeListBeans.size(); i++){
            PaymentCard card = new PaymentCard();
            card.setId(i);
            card.setName(mRechargeListBeans.get(i).getSymbol());//"ANY");
            card.setAddress(mRechargeListBeans.get(i).getAddress());//"7895269.5487");
            mAllCards.add(card);
        }

        mFlowLayout = findViewById(R.id.activity_wallet_charge_tabs);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                PaymentCard card = (PaymentCard)mFlowLayout.getAdapter().getItem(position);
                displayCardInfo(card);
                return true;
            }
        });

        List<PaymentCard> cardList = new ArrayList<>();
        int allCount = mRechargeListBeans.size();
        for(int i=0; i< allCount; i++){
            if(Configs.SURPORT_SYMBOL_LIST.contains(mAllCards.get(i).getName())) {
                PaymentCard card = new PaymentCard();
                card.setId(i);
                card.setName(mRechargeListBeans.get(i).getSymbol());//"ANY");
                card.setAddress(mRechargeListBeans.get(i).getAddress());//"7895269.5487");
                card.setMemo(mRechargeListBeans.get(i).getMemo());
                cardList.add(card);
            }
        }

        CardTagAdapter tagAdapter = new CardTagAdapter(cardList);
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

    public void onCopyMemoClicked(View view){
        AppUtils.copyText(this, mTextViewTagByMemo.getText().toString());
        ToastUtils.showToast(this, R.string.copy_success);
    }

    public void onCardClicked(View view) {
        if(null == mAllCards|| null == mRechargeListBeans){
            return;
        }
        PaymentCardChoiceWindow choiceWindow = new PaymentCardChoiceWindow(this, mAllCards, view.getWidth());
        choiceWindow.setOnMenuSelectedListener(new PaymentCardChoiceWindow.OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(PaymentCard card) {
                displayCardInfo(card);
            }
        });
        choiceWindow.showAsDropDown(view);
    }

    /**
     * 显示一个币种的地址信息
     * @param card
     */
    private void displayCardInfo(PaymentCard card){
        mCardVew.setText(card.getName());
        mCardVew.setTag(card);

        // 地址
        mQrContentView = findViewById(R.id.activity_wallet_charge_qr_str);
        mQrContentView.setText(card.getAddress());

        // 二维码
        mQrView = findViewById(R.id.activity_wallet_charge_qr);
        Bitmap bitmap = BusinessUtils.createQRCode(card.getAddress());
        mQrView.setImageBitmap(bitmap);

        mQrCopy.setVisibility(View.VISIBLE);

        if(!"EOS".equals(card.getName().toUpperCase())){
//        if(!"TRE".equals(card.getName().toUpperCase())){
            mViewContainerAddr.setVisibility(View.VISIBLE);
            mViewContainerMemo.setVisibility(View.GONE);
        }else{
            mViewContainerAddr.setVisibility(View.GONE);
            mViewContainerMemo.setVisibility(View.VISIBLE);
            mTextViewAddrByMemo.setText(card.getAddress());
            mTextViewTagByMemo.setText(card.getMemo());

//            mTextViewAddrByMemo.setText("anydeposit"); // for test
//            mTextViewTagByMemo.setText("123456"); // for test

            String warningStr = getString(R.string.deposit_memo_warning);
            // warning 红色
            SpannableString span = new SpannableString(warningStr);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D14B64"));
            span.setSpan(colorSpan, 0, warningStr.indexOf("："), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            // 粗体
            span.setSpan(new StyleSpan(Typeface.BOLD),
                    0, warningStr.indexOf("："), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //设置斜体
            mTextViewWarningByMemo.setText(span);

            DlgWalletMemoTips dlg = new DlgWalletMemoTips(this);
            dlg.show();
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
            tagView.setTextColor(Color.parseColor("#CAAC89"));
            tagView.setGravity(Gravity.CENTER_VERTICAL);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtils.sp2px(getBaseContext(), 12));
            tagView.setText(tag.getName());
            tagView.setTag(tag);
            return tagView;
        }
    }

    private void doRequest(){
        mLoadingView.setVisibility(View.VISIBLE);
        WalletApi.getRechargeList(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mRechargeListBeans = (List<RechargeListBean>) object;

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
                        ToastUtils.showToast(WalletChargeActivity.this, errMessage);
                        mLoadingView.setVisibility(View.GONE);
                    }
                });

            }
        });
    }
}
