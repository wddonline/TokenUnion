package com.iconiz.tokenunion.ui.web.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseFragment;

public class WebFragment extends BaseFragment {

    private View mRootView;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    private String mUrl;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mUrl = bundle.getString("url");
    }

    @Override
    protected void initViews() {
        mWebView = mRootView.findViewById(R.id.fragment_web_webview);
        mProgressBar = mRootView.findViewById(R.id.fragment_web_progressbar);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setSupportZoom(true); // 支持缩放
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void lazyLoad() {

    }
}
