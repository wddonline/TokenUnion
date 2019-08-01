package com.iconiz.tokenunion.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.yidaichu.android.common.view.statusbar.StatusBarCompat;

public abstract class BaseActivity extends AppCompatActivity {

    public ActivityFragmentActive mActive = new ActivityFragmentActive(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarLightMode();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract void initData();

    protected abstract void initViews();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActive.destroy();
    }

    public final void setStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    public final void setTranslucent(boolean translucent) {
        StatusBarCompat.setTranslucent(getWindow(), translucent);
    }

    public final void setTransparent(boolean translucent) {
        StatusBarCompat.setTransparent(getWindow(), translucent);
    }

    public final int setStatusBarLightMode() {
        return StatusBarCompat.setStatusBarLightMode(this);
    }

    public void jumpToActivity(Class<? extends Activity> clazz) {
        jumpToActivity(clazz, null);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, View sharedView, String sharedElementName ) {
        jumpToActivity(clazz, null, sharedView, sharedElementName);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName ) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, sharedElementName).toBundle());
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        jumpToActivityForResult(clazz, null, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, View sharedView, String sharedElementName, int requestCode) {
        jumpToActivityForResult(clazz, null, sharedView, sharedElementName, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivityForResult(this, intent,  requestCode,ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, sharedElementName).toBundle());
    }
}