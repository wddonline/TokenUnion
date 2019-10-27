package com.tokenunion.pro.ui.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.ui.login.adapter.CountryChoiceAdapter;
import com.tokenunion.pro.ui.login.model.CountryBean;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class CountryChoiceActivity extends BaseActivity {

    public static final String EXTRA_KEY_COUNTRY = "country";
    public static final String EXTRA_KEY_COUNTRY_ABB = "countryAbb";
    public static final String EXTRA_KEY_PHONE_CODE = "phoneCode";
    private List<CountryBean> mListCounty = new ArrayList<>();
    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_choice);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        UserApi.getCountryList(Configs.getCurrentLanguage(), new ActivityFragmentActive(this), new ApiRequestCallback() {
            @Override
            public void onSuccess(final Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListCounty = (List<CountryBean>)object;
                        refreshListViewData(mListCounty);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(CountryChoiceActivity.this, errMessage);
                    }
                });
            }
        });

    }

    private void refreshListViewData(List<CountryBean> list){
        if(null == mListView){
            return;
        }
        CountryChoiceAdapter adapter = new CountryChoiceAdapter(this, list);
        adapter.setOnItemClickedListener(new CountryChoiceAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent();
                CountryBean countryBean = mListCounty.get(position);
                intent.putExtra(EXTRA_KEY_COUNTRY, countryBean.getCountryName());
                intent.putExtra(EXTRA_KEY_PHONE_CODE, countryBean.getPhoneCode());
                intent.putExtra(EXTRA_KEY_COUNTRY_ABB, countryBean.getCountryAbb());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mListView.setAdapter(adapter);
//        mListView.notifyAll();
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.country_region);

        mListView = findViewById(R.id.activity_country_choice_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration dividerDecoration = new LineDividerDecoration(this);
        int offset = DensityUtils.dip2px(this, 22);
        dividerDecoration.setLeftOffset(offset);
        dividerDecoration.setRightOffset(offset);
        mListView.addItemDecoration(dividerDecoration);

//        final String[] data = getResources().getStringArray(R.array.country_codes);
//        CountryChoiceAdapter adapter = new CountryChoiceAdapter(this, data);
//        adapter.setOnItemClickedListener(new CountryChoiceAdapter.OnItemClickedListener() {
//            @Override
//            public void onItemClicked(int position) {
//                Intent intent = new Intent();
//                intent.putExtra("country", data[position].split("-")[1]);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//        mListView.setAdapter(adapter);
    }

    public void onBackClicked(View view) {
        finish();
    }

}
