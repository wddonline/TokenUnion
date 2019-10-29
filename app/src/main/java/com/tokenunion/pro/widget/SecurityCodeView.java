package com.tokenunion.pro.widget;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.common.SimpleTextWatcher;

public class SecurityCodeView extends RelativeLayout {

    private EditText mEditText;
    private TextView[] mTextViews;

    private StringBuffer mStringBuffer = new StringBuffer();
    private InputCompletedListener mInputCompletedListener;

    private int mCount = 6;
    private String mInputCode;

    public SecurityCodeView(Context context) {
        this(context, null);
    }

    public SecurityCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextViews = new TextView[6];
        View.inflate(context, R.layout.view_security_code, this);

        mEditText = (EditText) findViewById(R.id.view_security_code_input);
        mTextViews[0] = (TextView) findViewById(R.id.view_security_code_code1);
        mTextViews[1] = (TextView) findViewById(R.id.view_security_code_code2);
        mTextViews[2] = (TextView) findViewById(R.id.view_security_code_code3);
        mTextViews[3] = (TextView) findViewById(R.id.view_security_code_code4);
        mTextViews[4] = (TextView) findViewById(R.id.view_security_code_code5);
        mTextViews[5] = (TextView) findViewById(R.id.view_security_code_code6);

        mEditText.setCursorVisible(false);//将光标隐藏

        mEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //如果字符不为""时才进行操作
                if (!editable.toString().equals("")) {
                    if (mStringBuffer.length() > 5) {
                        //当文本长度大于3位时editText置空
                        mEditText.setText("");
                        return;
                    } else {
                        //将文字添加到StringBuffer中
                        mStringBuffer.append(editable);
                        mEditText.setText("");//添加后将EditText置空
                        mCount = mStringBuffer.length();
                        mInputCode = mStringBuffer.toString();
                        if (mStringBuffer.length() == 6) {
                            //文字长度位4  则调用完成输入的监听
                            if (mInputCompletedListener != null) {
                                mInputCompletedListener.inputComplete();
                            }
                        }
                    }

                    for (int i = 0; i < mStringBuffer.length(); i++) {
                        mTextViews[i].setText(String.valueOf(mInputCode.charAt(i)));
                        mTextViews[i].setBackgroundResource(R.drawable.bg_verify_code_active);
                    }

                }
            }
        });

        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int space = dip2px(getContext(), 9 * (mTextViews.length - 1));
        int width = (int) (1f * (getMeasuredWidth() - space - getPaddingLeft() - getPaddingRight()) / mTextViews.length);
        LinearLayout.LayoutParams lp;
        for (int i = 0; i < mTextViews.length; i++) {
            lp = (LinearLayout.LayoutParams) mTextViews[i].getLayoutParams();
            lp.width = width;
            lp.height = width;
            mTextViews[i].setLayoutParams(lp);
        }
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public boolean onKeyDelete() {
        if (mCount == 0) {
            mCount = 6;
            return true;
        }
        if (mStringBuffer.length() > 0) {
            //删除相应位置的字符
            mStringBuffer.delete((mCount - 1), mCount);
            mCount--;
            mInputCode = mStringBuffer.toString();
            mTextViews[mStringBuffer.length()].setText("");
            mTextViews[mStringBuffer.length()].setBackgroundResource(R.drawable.bg_verify_code_default);
            if (mInputCompletedListener != null)
                mInputCompletedListener.deleteContent(true);//有删除就通知manger

        }
        return false;
    }

    /**
     * 清空输入内容
     */
    public void clearEditText() {
        mStringBuffer.delete(0, mStringBuffer.length());
        mInputCode = mStringBuffer.toString();
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText("");
            mTextViews[i].setBackgroundResource(R.drawable.bg_verify_code_default);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void setInputCompletedListener(InputCompletedListener inputCompletedListener) {
        this.mInputCompletedListener = inputCompletedListener;
    }
    /**
     * 获取输入文本
     *
     * @return
     */
    public String getInputCode() {
        return mInputCode;
    }

    public interface InputCompletedListener {

        void inputComplete();
        void deleteContent(boolean isDelete);

    }


}