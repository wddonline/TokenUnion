package com.tokenunion.pro.ui.mine.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.ui.mine.model.MessageBean;
import com.tokenunion.pro.utils.DatetimeUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yidaichu.android.common.utils.DensityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.tokenunion.pro.config.Configs.SYSTEM_SERVER_TIME_ZONE;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = MessageAdapter.class.getSimpleName();
    private List<MessageBean.MessageListBean> mData;
    private LayoutInflater mInflater;
    private OnItemClickedListener mOnItemClickedListener;

    // 字体
    Typeface mTypeface;

    private int mMargin;

    public MessageAdapter(Context context, List<MessageBean.MessageListBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
        mMargin = DensityUtils.dip2px(context, 12);

//        //得到AssetManager
//        AssetManager mgr = context.getAssets();
//
//        //根据路径得到Typeface
//        mTypeface = Typeface.createFromAsset(mgr, "fonts/TimesNewRomanBoldItalic.ttf");
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_message, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bindData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView titleView;
        TextView dateView;
        TextView contentView;
        TextView moneyView;
        TextView clickView;
        ImageView imageView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.item_message_root);
            titleView = itemView.findViewById(R.id.item_message_title);
            dateView = itemView.findViewById(R.id.item_message_date);
            contentView = itemView.findViewById(R.id.item_message_content);
            moneyView = itemView.findViewById(R.id.item_message_money);
            clickView = itemView.findViewById(R.id.item_message_click);
            imageView = itemView.findViewById(R.id.item_message_image);

//            titleView.setTypeface(mTypeface);
        }

        public void bindData(final MessageBean.MessageListBean msg, int position) {
            ((LinearLayout.LayoutParams)rootView.getLayoutParams()).topMargin = position == 0 ? mMargin : 0;
            titleView.setText(msg.getTitle());//+ "_"+ msg.getType());
            String sDt = msg.getShowTime();//"2019-08-26 03:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone(SYSTEM_SERVER_TIME_ZONE));
            Date dateTime = null;
            try {
                dateTime = sdf.parse(sDt);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }

            //继续转换得到时间戳
            long timeStamp = dateTime.getTime();

            String displayTimeString = DatetimeUtils.getFuzzyTime(timeStamp);
            if("".equals(displayTimeString)) {
                sdf.setTimeZone(TimeZone.getDefault()); // 转换到当前时区
                displayTimeString = msg.getShowTime();
            }
            dateView.setText(displayTimeString);
            contentView.setText(msg.getContent());
            if(msg.getType() == 0){
                // 0为重点消息，红色显示
                int colorImportant = TUApplication.INSTANCE.getResources().getColor(R.color.message_text_color_important);
                contentView.setTextColor(colorImportant);
            }else{
                int colorImportant = TUApplication.INSTANCE.getResources().getColor(R.color.message_text_color_normal);
                contentView.setTextColor(colorImportant);
            }

            String imgUrl;
            if(null != msg.getPicUrl()) {
                imgUrl = msg.getPicUrl();
                // imageView.setImageUri(msg.getPicUrl());
                imageView.setVisibility(View.VISIBLE);
                loadIntoUseFitWidth(contentView.getContext(), imgUrl, R.drawable.default_loading_img_gray, imageView);
            }else {
//                imageView.setImageUri("https://upload.jianshu.io/users/upload_avatars/2055354/9827d4dc-44d1-4a35-b53e-f174255dae0f.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240");

                // imgUrl = "https://static.oschina.net/uploads/space/2018/0513/001735_h0LR_3577599.png";
                imageView.setVisibility(View.GONE);
            }

//            TextJustification.justify(contentView, msg.getContent().length());

//            switch (msg.getType()) { // 消息类型 预留
//                case Message.TYPE_MONEY:
//                    clickView.setText(R.string.view_money_record);
//                    moneyView.setVisibility(View.VISIBLE);
//                    String str = (msg.getMoney() > 0 ? "+ " : "- ") + msg.getMoney() + " " + msg.getCurrency();
//                    SpannableString span = new SpannableString(str);
//                    int color = msg.getMoney() > 0 ? Color.parseColor("#009688") : Color.parseColor("#E51C23");
//                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
//                    span.setSpan(colorSpan, 0, str.lastIndexOf(' '), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    moneyView.setText(span);
//                    break;
//                case Message.TYPE_INVITE:
//                    clickView.setText(R.string.invite_friend);
//                    moneyView.setVisibility(View.GONE);
//                    break;
//                case Message.TYPE_UPGRADE:
//                    clickView.setText(R.string.customer_center);
//                    moneyView.setVisibility(View.GONE);
//                    break;
//            }
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickedListener.onItemClicked(msg);
                }
            });
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(MessageBean.MessageListBean msg);

    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        LogUtil.d(TAG, "onResourceReady(), isFirstResource = "+ isFirstResource+ ", imageUrl ="+ imageUrl);
                        if (imageView == null) {
                            return false;
                        }

                        double oriWidth = resource.getIntrinsicWidth();
                        double oriHeight = resource.getIntrinsicHeight();
                        double rateHW = oriWidth / oriHeight;

                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        //float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                        int vh = (int) ((float) vw / (float) 1.78);
                        int vh = (int) ((float) vw / rateHW);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }
}
