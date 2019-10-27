package com.tokenunion.pro.ui.login.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.adapter.LanguageChoiceAdapter;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.utils.language.LanguageType;
import com.tokenunion.pro.utils.language.LanguageUtil;
import com.tokenunion.pro.utils.language.SpUtil;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.yidaichu.android.common.utils.DensityUtils;

public class LanguageChoiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choice);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.lan_choose);

        RecyclerView listView = findViewById(R.id.activity_language_choice_list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration dividerDecoration = new LineDividerDecoration(this);
        int offset = DensityUtils.dip2px(this, 22);
        dividerDecoration.setLeftOffset(offset);
        dividerDecoration.setRightOffset(offset);
        listView.addItemDecoration(dividerDecoration);

        String[] data = getResources().getStringArray(R.array.support_lans);
        LanguageChoiceAdapter adapter = new LanguageChoiceAdapter(this, data);
        adapter.setOnItemClickedListener(new LanguageChoiceAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                changeLanguage(position);
                finish();
            }
        });
        listView.setAdapter(adapter);
    }

    public void onBackClicked(View view) {
        finish();
    }


    /**
     *
     * 切换语言
     * @param position
     */
    private void changeLanguage(int position){
//     <item>简体中文</item>
//        <item>English</item>
//        <item>한국어</item>
//        <item>Tiếng Việt</item>
//        <item>日本语</item>
        String language = null;
        int newPosition = position+1; // 去掉了中文
        switch (newPosition) {
            case 0:
                //切换为简体中文
                language = LanguageType.CHINESE.getLanguage();
                break;
            case 1:
                //切换为英语
                language = LanguageType.ENGLISH.getLanguage();
                break;
            case 2:
                //切换为韩语
                language = LanguageType.KO_RKR.getLanguage();
                break;
            default:
                language = LanguageType.ENGLISH.getLanguage();
                break;

        }
        // 记录设置的值
        SpUtil.getInstance(this).putInt(SpUtil.LANGUAGE_ID, position);
        Configs.setCurrentLanguage(LanguageType.getLanguageName(language));
        changeLanguage(language);
    }

    /**
     * 如果是7.0以下，我们需要调用changeAppLanguage方法，
     * 如果是7.0及以上系统，直接把我们想要切换的语言类型保存在SharedPreferences中即可
     * 然后重新启动MainActivity
     * @param language
     */
    private void changeLanguage(String language) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(TUApplication.INSTANCE, language);
        }
        SpUtil.getInstance(this).putString(SpUtil.LANGUAGE, language);
        Intent intent;
        if(UserAccount.getInstance().isLogin()) {
            intent = new Intent(this, MainActivity.class);

        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
