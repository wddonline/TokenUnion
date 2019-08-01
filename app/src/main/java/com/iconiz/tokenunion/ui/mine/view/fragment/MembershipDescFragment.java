package com.iconiz.tokenunion.ui.mine.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MembershipDescFragment extends BaseFragment {

    private String mCondition;
    private String mStableValue;
    private int mLevel;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_membership_desc;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mLevel = getArguments().getInt("level");

        String[] conditions = getResources().getStringArray(R.array.membership_conditions);
        switch (mLevel) {
            case User.LEVEL_YOUNG:
                mCondition = conditions[0];
                mStableValue = "0 ($)";
                break;
            case User.LEVEL_PREFERRED:
                mCondition = conditions[1];
                mStableValue = "1,000 ($)";
                break;
            case User.LEVEL_ELITE:
                mCondition = conditions[2];
                mStableValue = "2,000 ($)";
                break;
            case User.LEVEL_RESERVE:
                mCondition = conditions[3];
                mStableValue = "3,000 ($)";
                break;
            case User.LEVEL_WORLD:
                mCondition = conditions[4];
                mStableValue = "5,000 ($)";
                break;
            default:
                mCondition = conditions[5];
                mStableValue = "10,000 ($)";
                break;
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void lazyLoad() {
        TextView conditionView = mRootView.findViewById(R.id.fragment_membership_desc_condition);
        conditionView.setText(mCondition);

        TextView stableLevelView = mRootView.findViewById(R.id.fragment_membership_desc_stable_level);
        String levelStr = getString(R.string.membership_wealth_limit_solo);
        levelStr = String.format(levelStr, mStableValue);
        stableLevelView.setText(levelStr);

        initRightIcons();

        TextView utcView = mRootView.findViewById(R.id.fragment_membership_desc_utc_time);
        //（UTC Z HH:mm）
        SimpleDateFormat dateFormat = new SimpleDateFormat("Z hh:mm a", Locale.ENGLISH);
        String dateStr = dateFormat.format(new Date());
        utcView.setText(String.format(getString(R.string.system_time_format), dateStr));
    }

    private void initRightIcons() {
        String[] descs = getResources().getStringArray(R.array.membership_right_descs);
        int[] icons;
        String type;
        switch (mLevel) {
            case User.LEVEL_YOUNG:
                icons = new int[3];
                type = "black";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                break;
            case User.LEVEL_PREFERRED:
                icons = new int[4];
                type = "black";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "6%");
                break;
            case User.LEVEL_ELITE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "9%");
                descs[4] = String.format(descs[4], "$7,000");
                break;
            case User.LEVEL_RESERVE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "12%");
                descs[4] = String.format(descs[4], "$15,000");
                break;
            case User.LEVEL_WORLD:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "15%");
                descs[4] = String.format(descs[4], "$100,000");
                descs[5] = descs[5];
                break;
            default:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "15%+");
                descs[4] = String.format(descs[4], "$250,000");
                descs[5] = descs[5];
                break;
        }
        for (int i = 0; i < icons.length; i++) {
            icons[i] = getResources().getIdentifier("icon_member_right_" + type + "_" + i, "mipmap", getContext().getPackageName());
        }
        String[] rights = getResources().getStringArray(R.array.membership_rights);
        ViewGroup rightView;
        String id;
        for (int i = 0; i < 6; i++) {
            id = "fragment_membership_desc_right" + i;
            rightView = mRootView.findViewById(getResources().getIdentifier(id, "id", getContext().getPackageName()));
            if (i < icons.length) {
                rightView.setVisibility(View.VISIBLE);
                ((ImageView)rightView.getChildAt(0)).setImageResource(icons[i]);
                ((TextView)rightView.getChildAt(1)).setText(rights[i]);
                ((TextView)rightView.getChildAt(2)).setText(descs[i]);
            } else {
                rightView.setVisibility(View.INVISIBLE);
            }
        }
    }
}
