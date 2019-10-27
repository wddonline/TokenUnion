package com.tokenunion.pro.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tokenunion.pro.ui.capital.model.BannerCommonBean;
import com.tokenunion.pro.ui.capital.view.activity.NewUserHoldingActivity;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.ui.mine.view.activity.MembershipCenterActivity;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.ui.web.activity.WebActivity;
import com.tokenunion.pro.utils.LogUtil;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Create by: xiaohansong
 * Time: 2019-09-06 14:29
 * -
 * Description: banner跳转处理类
 */
public class BannerDelegate implements BGABanner.Delegate {
    private static final String TAG = BannerDelegate.class.getSimpleName();
    private Context mContext;
    private List<BannerCommonBean> mBannerCommonBeanList;

    public BannerDelegate(Context context, List<BannerCommonBean> listBanners){
        this.mContext = context;
        this.mBannerCommonBeanList = listBanners;
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
        if(null == mBannerCommonBeanList || mBannerCommonBeanList.size()<position){
            return;
        }
        BannerCommonBean bean = mBannerCommonBeanList.get(position);
        if("1".equals(bean.getBannerType())){
            // 外部跳转
            Bundle bundle = new Bundle();
//                    bundle.putString("title", getString(R.string.user_licence_title));
            bundle.putString("url", bean.getBurl());
            jumpToActivity(mContext, WebActivity.class, bundle);
        }else{
//                    ToastUtils.showToast(getContext(), bean.getInterType());
            LogUtil.i(TAG, "InterType = "+ bean.getInterType());
            //  WEALTH：财富,WALLET：钱包 ,EXPLORE：发现 ,INVITE ：邀请,ROYALTY：会员中心,MESSAGE：消息
            switch (bean.getInterType()){
                case "WEALTH":
                    MainActivity.getInstance().swithToFragment(0);
                    break;
                case "WALLET":
                    MainActivity.getInstance().swithToFragment(1);
                    break;
                case "EXPLORE":
                    MainActivity.getInstance().swithToFragment(2);
                    break;
                case "INVITE":
                    MainActivity.getInstance().swithToFragment(3);
                    break;
                case "ROYALTY":
                    jumpToActivity(mContext, MembershipCenterActivity.class, null);
                    break;
                case "MESSAGE":
                    jumpToActivity(mContext, MessageActivity.class, null);
                    break;
                case "WELCOME":
                    jumpToActivity(mContext, NewUserHoldingActivity.class, null);
                    break;

                default:
//                    ToastUtils.showToast(mContext, bean.getInterType());
                    LogUtil.w(TAG, "unknown banner type: "+ bean.getInterType());
                    break;
            }
        }
    }

    public void jumpToActivity(Context context, Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(context, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
}
