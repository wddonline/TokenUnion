package com.tokenunion.pro.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.anypocket.pro.R;

import static com.anypocket.pro.R.style.BottomDialog;

/**
 * Create by: xiaohansong
 * Time: 2019-09-24 17:20
 * -
 * Description: 钱包的"充币"，有memo的币种时的提示dlg
 */
public class DlgWalletMemoTips extends Dialog implements View.OnClickListener {

    private Context mContext;


    public DlgWalletMemoTips(@NonNull Context context) {
        super(context, BottomDialog);//风格主题
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_wallet_memo_tips_layout);//自定义布局
        // 按空白处不能取消
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView(){
        findViewById(R.id.dlg_wallet_memo_tips_layout_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dlg_wallet_memo_tips_layout_confirm:
                dismiss();
                break;

            default:
                break;
        }
    }
}
