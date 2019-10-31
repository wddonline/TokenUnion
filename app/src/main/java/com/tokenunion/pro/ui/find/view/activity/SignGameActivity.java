package com.tokenunion.pro.ui.find.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.yidaichu.android.common.utils.DensityUtils;

public class SignGameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_game);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.layout_common_actionbar_title)).setText(R.string.sign_challenge);

        FrameLayout timeLayout = findViewById(R.id.activity_sign_game_time);
        View[] dotViews = new View[5];
        for (int i = 0; i < dotViews.length; i++) {
            dotViews[i] = findViewById(getResources().getIdentifier("activity_sign_game_dot" + i, "id", getPackageName()));
        }
        View[] lineViews = new View[4];
        for (int i = 0; i < lineViews.length; i++) {
            dotViews[i] = findViewById(getResources().getIdentifier("activity_sign_game_line" + i, "id", getPackageName()));
        }
        dotViews[4].post(new Runnable() {
            @Override
            public void run() {
                final int offset = DensityUtils.dip2px(getBaseContext(), 5);
                int[] loc = new int[2];
                FrameLayout.LayoutParams lp;
                View childView;
                for (int i = 0; i < timeLayout.getChildCount(); i++) {
                    dotViews[i].getLocationInWindow(loc);
                    childView = timeLayout.getChildAt(i);
                    lp = (FrameLayout.LayoutParams)childView.getLayoutParams();
                    if (i < timeLayout.getChildCount() - 1) {
                        lp.leftMargin = (int) (loc[0] - offset - childView.getWidth() / 2f);
                    } else {
                        lp.leftMargin = (int) (loc[0] + offset - childView.getWidth() / 2f);
                    }
                    childView.setLayoutParams(lp);
                }
                timeLayout.requestLayout();
            }
        });
    }

    public void onBackClicked() {
        finish();
    }
}
