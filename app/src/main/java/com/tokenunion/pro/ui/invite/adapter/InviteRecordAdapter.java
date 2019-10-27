package com.tokenunion.pro.ui.invite.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.ui.invite.model.InviteRecord;

import java.util.ArrayList;
import java.util.List;

public class InviteRecordAdapter extends RecyclerView.Adapter<InviteRecordAdapter.RecordViewHolder> {

    private Context mContext;
    private List<List<InviteRecord>> mData;
    private LayoutInflater mInflater;

    public InviteRecordAdapter(Context context, List<List<InviteRecord>> data) {
        this.mContext = context;
        mData = new ArrayList<>();
        mData.addAll(data);
        mInflater = LayoutInflater.from(context);
    }

    public void refreshData(List<List<InviteRecord>> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_invite_record, parent, false);
        RecordViewHolder viewHolder = new RecordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {

        TextView[] textViews;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViews = new TextView[4];
            for (int i = 0; i < 4; i++) {
                int resId = mContext.getResources().getIdentifier("item_invite_record_value" + i, "id", mContext.getPackageName());
                textViews[i] = itemView.findViewById(resId);
            }
        }

        public void bindData(List<InviteRecord> items) {
            int strId;
            int drawableId;
            Drawable drawable;
            InviteRecord item;
            for (int i = 0; i < textViews.length; i++) {
                if (i < items.size()) {
                    item = items.get(i);
                    if (i == 0) {
                        ((View)textViews[i].getParent()).setVisibility(View.VISIBLE);
                    } else {
                        textViews[i].setVisibility(View.VISIBLE);
                    }
                    if (item.getType() == InviteRecord.TYPE_NORMAL) {
                        textViews[i].setText(item.getValue());
                        textViews[i].setCompoundDrawables(null, null, null, null);
                    } else {
                        switch (item.getType()) {
                            case InviteRecord.TYPE_STAR:
                                strId = R.string.level_star;
                                drawableId = R.mipmap.star_black;
                                break;
                            case InviteRecord.TYPE_JADE:
                                strId = R.string.level_jade;
                                drawableId = R.mipmap.star_black;
                                break;
                            case InviteRecord.TYPE_GOLD:
                                strId = R.string.level_gold;
                                drawableId = R.mipmap.star_red;
                                break;
                            case InviteRecord.TYPE_TITANIUM:
                                strId = R.string.level_titanium;
                                drawableId = R.mipmap.star_red;
                                break;
                            case InviteRecord.TYPE_PLATINUM:
                                strId = R.string.level_platinum;
                                drawableId = R.mipmap.star_gold;
                                break;
                            default:
                                strId = R.string.level_diamond;
                                drawableId = R.mipmap.star_gold;
                                break;
                        }
                        textViews[i].setText(strId);
                        drawable = mContext.getResources().getDrawable(drawableId);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        textViews[i].setCompoundDrawables(drawable, null, null, null);
                    }

                } else {
                    if (i == 0) {
                        ((View)textViews[i].getParent()).setVisibility(View.GONE);
                    } else {
                        textViews[i].setVisibility(View.GONE);
                    }
                }
            }
        }
    }
}
