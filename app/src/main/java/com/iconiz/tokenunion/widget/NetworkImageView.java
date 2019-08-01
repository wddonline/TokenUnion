package com.iconiz.tokenunion.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.iconiz.tokenunion.R;

import java.io.File;

public class NetworkImageView extends AppCompatImageView {

    private int mPlaceholderImg = R.drawable.default_loading_img_gray;
    private int mErrorImg = R.drawable.default_loading_img_gray;

    public NetworkImageView(Context context) {
        super(context);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageUri(String uri) {
        if (TextUtils.isEmpty(uri)) {
            setImageDrawable(ContextCompat.getDrawable(getContext(), mPlaceholderImg));
            return;
        }
        if (uri.startsWith("http://") || uri.startsWith("https://")) {
            Glide.with(getContext())
                    .load(uri)
                    .placeholder(mPlaceholderImg)
                    .error(mErrorImg)
                    .into(this);
        } else {
            File file = new File(uri);
            Glide.with(getContext())
                    .load(file)
                    .placeholder(mPlaceholderImg)
                    .error(mErrorImg)
                    .into(this);
        }

    }

    public int getPlaceholderImg() {
        return mPlaceholderImg;
    }

    public void setPlaceholderImg(int placeholderImg) {
        this.mPlaceholderImg = placeholderImg;
    }

    public int getErrorImg() {
        return mErrorImg;
    }

    public void setErrorImg(int errorImg) {
        this.mErrorImg = errorImg;
    }
}
