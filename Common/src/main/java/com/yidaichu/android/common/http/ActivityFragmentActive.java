package com.yidaichu.android.common.http;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.fragment.app.Fragment;

import java.lang.ref.SoftReference;

public class ActivityFragmentActive {

    private SoftReference<Activity> mActivity;
    private SoftReference<Fragment> mFragment;

    public ActivityFragmentActive(Activity activity) {
        mActivity = new SoftReference<>(activity);
    }

    public ActivityFragmentActive(Fragment fragment) {
        this.mFragment = new SoftReference<>(fragment);
    }

    public void destroy() {
        if (mActivity != null) {
            mActivity.clear();
            mActivity = null;
        }
        if (mFragment != null) {
            mFragment.clear();
            mFragment = null;
        }
    }

    public boolean isActive() {
        if (mActivity != null) {
            Activity activity = mActivity.get();
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (activity.isDestroyed() || activity.isFinishing()){
                        return false;
                    }
                } else {
                    if (activity.isFinishing()) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (mFragment != null) {
            Fragment fragment = mFragment.get();
            if (fragment != null) {
                Activity activity = fragment.getActivity();
                if (activity == null) return false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (activity.isDestroyed() || activity.isFinishing()) {
                        return false;
                    }
                } else {
                    if (activity.isFinishing()) {
                        return false;
                    }
                }
                if (fragment.isDetached()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Context getContext() {
        if (mActivity != null) {
            Activity activity = mActivity.get();
            if (activity != null)
            return activity;
        }
        if (mFragment != null) {
            Fragment fragment = mFragment.get();
            if (fragment != null)
            return fragment.getContext();
        }
        return null;
    }
}
