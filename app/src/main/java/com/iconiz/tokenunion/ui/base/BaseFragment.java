package com.iconiz.tokenunion.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public final String TAG = this.getClass().getName();

    public ActivityFragmentActive mActive = new ActivityFragmentActive(this);
    protected View mRootView;

    private boolean dataLoaded = false;
    private boolean viewInited = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getRootLayoutId(), container, false);
            initData(savedInstanceState);
            initViews();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        viewInited = true;
        if (dataLoaded) return mRootView;
        if (!getUserVisibleHint()) return mRootView;
        lazyLoad();
        dataLoaded = true;
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) return;
        if (!viewInited) return;
        if (dataLoaded) return;
        lazyLoad();
        dataLoaded = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActive.destroy();
    }

    public void jumpToActivity(Class<? extends Activity> clazz) {
        jumpToActivity(clazz, null);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, View sharedView, String sharedElementName ) {
        jumpToActivity(clazz, null, sharedView, sharedElementName);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName ) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivity(getContext(), intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), sharedView, sharedElementName).toBundle());
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        jumpToActivityForResult(clazz, null, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, View sharedView, String sharedElementName, int requestCode) {
        jumpToActivityForResult(clazz, null, sharedView, sharedElementName, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivityForResult(getActivity(), intent,  requestCode,ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), sharedView, sharedElementName).toBundle());
    }

    protected abstract int getRootLayoutId();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initViews();

    protected abstract void lazyLoad();
}
