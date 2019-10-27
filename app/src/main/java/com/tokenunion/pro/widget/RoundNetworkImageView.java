package com.tokenunion.pro.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.anypocket.pro.R;

/**
 * Created by richard on 9/20/16.
 */

public class RoundNetworkImageView extends NetworkImageView {

    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_BORDER = 0;
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private int mLeftTopRadius;
    private int mLeftBottomRadius;
    private int mRightTopRadius;
    private int mRightBottomRadius;
    private int mBorderWidth;
    private int mBorderColor;
    private boolean isRound;

    private Drawable mDrawable;
    private Drawable mBackgroundDrawable;

    private ScaleType mScaleType;

    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    public RoundNetworkImageView(Context context) {
        super(context);
        mBorderWidth = DEFAULT_BORDER;
        mBorderColor = DEFAULT_BORDER_COLOR;
    }


    public RoundNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundNetworkImageView, defStyle, 0);

        int index = a.getInt(R.styleable.RoundNetworkImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(sScaleTypeArray[index]);
        }

        int radius = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_cornerRadius, DEFAULT_RADIUS);
        if (radius > 0) {
            mLeftTopRadius = radius;
            mLeftBottomRadius = radius;
            mRightTopRadius = radius;
            mRightBottomRadius = radius;
        }

        mLeftTopRadius = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_leftTopRadius, radius);
        mLeftBottomRadius = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_leftBottomRadius, radius);
        mRightTopRadius = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_rightTopRadius, radius);
        mRightBottomRadius = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_rightBottomRadius, radius);

        mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundNetworkImageView_borderSize, DEFAULT_BORDER);
        mBorderColor = a.getColor(R.styleable.RoundNetworkImageView_borderColor, DEFAULT_BORDER_COLOR);

        a.recycle();

        isRound = mLeftTopRadius + mLeftBottomRadius + mRightTopRadius + mRightBottomRadius > 0;
    }

    /**
     * Controls how the image should be resized or moved to match the size
     * of this ImageView.
     *
     * @param scaleType The desired scaling mode.
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch(scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            if (mDrawable instanceof RoundedDrawable
                    && ((RoundedDrawable) mDrawable).getScaleType() != scaleType) {
                ((RoundedDrawable) mDrawable).setScaleType(scaleType);
            }

            if (mBackgroundDrawable instanceof RoundedDrawable
                    && ((RoundedDrawable) mBackgroundDrawable).getScaleType() != scaleType) {
                ((RoundedDrawable) mBackgroundDrawable).setScaleType(scaleType);
            }
            setWillNotCacheDrawing(true);
            requestLayout();
            invalidate();
        }
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * @see ScaleType
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            mDrawable = RoundedDrawable.fromDrawable(drawable, mScaleType, mBorderWidth, mBorderColor,
                    mLeftTopRadius, mLeftBottomRadius, mRightTopRadius, mRightBottomRadius);
        } else {
            mDrawable = null;
        }
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm != null) {
            mDrawable = new RoundedDrawable(bm, mBorderWidth, mBorderColor, mLeftTopRadius,
                        mLeftBottomRadius, mRightTopRadius, mRightBottomRadius);

            if (mScaleType != null) {
                ((RoundedDrawable) mDrawable).setScaleType(mScaleType);
            }
        } else {
            mDrawable = null;
        }
        super.setImageDrawable(mDrawable);
    }

/*	@Override
	public void setBackground(Drawable background) {
		setBackgroundDrawable(background);
	}*/

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        if (isRound && background != null) {
            mBackgroundDrawable = RoundedDrawable.fromDrawable(background, mScaleType, mBorderWidth,
                    mBorderColor, mLeftTopRadius, mLeftBottomRadius, mRightTopRadius, mRightBottomRadius);
        } else {
            mBackgroundDrawable = background;
        }
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    public int getBorder() {
        return mBorderWidth;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setCornerRadius(int radius) {
        this.mLeftTopRadius = radius;
        this.mLeftBottomRadius = radius;
        this.mRightTopRadius = radius;
        this.mRightBottomRadius = radius;
        if (mDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mDrawable).setCornerRadius(radius);
        }
        if (isRound && mBackgroundDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mBackgroundDrawable).setCornerRadius(radius);
        }
    }

    public void setCornerRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius,
                                int rightBottomRadius) {
        this.mLeftTopRadius = leftTopRadius;
        this.mLeftBottomRadius = leftBottomRadius;
        this.mRightTopRadius = rightTopRadius;
        this.mRightBottomRadius = rightBottomRadius;
        if (mDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mDrawable).setLeftTopRadius(leftTopRadius);
            ((RoundedDrawable) mDrawable).setLeftBottomRadius(leftBottomRadius);
            ((RoundedDrawable) mDrawable).setRightTopRadius(rightTopRadius);
            ((RoundedDrawable) mDrawable).setRightBottomRadius(rightBottomRadius);
        }
        if (isRound && mBackgroundDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mBackgroundDrawable).setLeftTopRadius(leftTopRadius);
            ((RoundedDrawable) mBackgroundDrawable).setLeftBottomRadius(leftBottomRadius);
            ((RoundedDrawable) mBackgroundDrawable).setRightTopRadius(rightTopRadius);
            ((RoundedDrawable) mBackgroundDrawable).setRightBottomRadius(rightBottomRadius);
        }
    }

    public void setBorderWidth(int width) {
        if (mBorderWidth == width) { return; }

        this.mBorderWidth = width;
        if (mDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mDrawable).setBorderWidth(width);
        }
        if (isRound && mBackgroundDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mBackgroundDrawable).setBorderWidth(width);
        }
        invalidate();
    }

    public void setBorderColor(int color) {
        if (mBorderColor == color) { return; }

        this.mBorderColor = color;
        if (mDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mDrawable).setBorderColor(color);
        }
        if (isRound && mBackgroundDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mBackgroundDrawable).setBorderColor(color);
        }
        if (mBorderWidth > 0) { invalidate(); }
    }

    public boolean isRound() {
        return isRound;
    }

    public void setRound(boolean round) {
        if (this.isRound == round) { return; }

        this.isRound = round;
        if (round) {
            if (mBackgroundDrawable instanceof RoundedDrawable) {
                ((RoundedDrawable) mBackgroundDrawable).setScaleType(mScaleType);
                ((RoundedDrawable) mBackgroundDrawable).setBorderWidth(mBorderWidth);
                ((RoundedDrawable) mBackgroundDrawable).setBorderColor(mBorderColor);
                ((RoundedDrawable) mBackgroundDrawable).setLeftTopRadius(mLeftTopRadius);
                ((RoundedDrawable) mBackgroundDrawable).setLeftBottomRadius(mLeftBottomRadius);
                ((RoundedDrawable) mBackgroundDrawable).setRightTopRadius(mRightTopRadius);
                ((RoundedDrawable) mBackgroundDrawable).setRightBottomRadius(mRightBottomRadius);
            } else {
                setBackgroundDrawable(mBackgroundDrawable);
            }
        } else if (mBackgroundDrawable instanceof RoundedDrawable) {
            ((RoundedDrawable) mBackgroundDrawable).setBorderWidth(0);
            ((RoundedDrawable) mBackgroundDrawable).setCornerRadius(0);
        }

        invalidate();
    }
}