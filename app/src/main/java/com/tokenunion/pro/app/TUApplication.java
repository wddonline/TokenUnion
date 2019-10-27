package com.tokenunion.pro.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.language.LanguageUtil;
import com.tokenunion.pro.utils.language.SpUtil;
import com.tokenunion.pro.widget.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

public class TUApplication extends Application {

    public static TUApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        EventBusUtils.init();

        /**
         * 对于7.0以下，需要在Application创建的时候进行语言切换
         */
        String language = SpUtil.getInstance(this).getString(SpUtil.LANGUAGE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(this, language);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {

				WaterDropHeader header = new WaterDropHeader(context);

                return header;
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                BallPulseFooter footer = new BallPulseFooter(context);
                footer.setAnimatingColor(Color.parseColor("#F0CDA5"));
                return footer;
            }
        });
    }
}
