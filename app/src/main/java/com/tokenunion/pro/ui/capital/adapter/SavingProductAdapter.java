package com.tokenunion.pro.ui.capital.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseViewHolder;
import com.tokenunion.pro.ui.capital.model.SavingProduct;
import com.tokenunion.pro.utils.StringUtils;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingProductAdapter extends RecyclerView.Adapter<SavingProductAdapter.ProductViewHolder> {

    private Context mContext;
    private List<SavingProduct> mData;
    private OnItemClickedListener mOnItemClickedListener;

    public SavingProductAdapter(Context context, List<SavingProduct> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public void refreshData(List<SavingProduct> products) {
        mData.clear();
        if (products != null) {
            mData.addAll(products);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<SavingProduct> products) {
        if (products == null) return;
        int start = mData.size();
        mData.addAll(products);
        notifyItemRangeInserted(start, products.size());
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_saving_product, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SavingProduct product = mData.get(position);
        holder.bindData(product, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.mOnItemClickedListener = onItemClickedListener;
    }

    class ProductViewHolder extends BaseViewHolder<SavingProduct> {

        @BindView(R.id.item_saving_product_click)
        View clickView;
        @BindView(R.id.item_saving_product_name)
        TextView nameView;
        @BindView(R.id.item_saving_product_tag)
        TextView tagView;
        @BindView(R.id.item_saving_product_expected_return)
        TextView expectedReturnView;
        @BindView(R.id.item_saving_product_expected_return_lab)
        TextView expectedReturnLabelView;
        @BindView(R.id.item_saving_product_start_balance)
        TextView startBalanceView;
        @BindView(R.id.item_saving_product_product_duration)
        TextView durationView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindData(SavingProduct item, int position) {
            nameView.setText(item.getSymbol());

            SpannableString span;
            AbsoluteSizeSpan sizeSpan;
            String value;
            String duration;
            if (item.getIsTimeDeposit() == 1) {//定期
                expectedReturnLabelView.setVisibility(View.VISIBLE);
                expectedReturnView.setVisibility(View.VISIBLE);
                value = StringUtils.getAbsoluteNum(item.getProfitRate(), 2);
                value = String.format(mContext.getString(R.string.expected_return_format), value, "%");
                span = new SpannableString(value);
                sizeSpan = new AbsoluteSizeSpan(DensityUtils.sp2px(mContext, 12));
                span.setSpan(sizeSpan, value.lastIndexOf('%'), value.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                expectedReturnView.setText(span);

                duration = String.format("%1$d %2$s", item.getDeadline(), item.getDeadlineUnit());
                span = new SpannableString(duration);
                sizeSpan = new AbsoluteSizeSpan(DensityUtils.sp2px(mContext, 12));
                span.setSpan(sizeSpan, duration.indexOf(' '), duration.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                durationView.setText(duration);
            } else {
                expectedReturnLabelView.setVisibility(View.GONE);
                expectedReturnView.setVisibility(View.GONE);

                duration = mContext.getString(R.string.is_demand);
                durationView.setText(duration);
            }

            String startBalance = String.format("%1$s %2$s", StringUtils.getAbsoluteNum(item.getMinDepositAmount(), 2), item.getSymbol());
            span = new SpannableString(startBalance);
            sizeSpan = new AbsoluteSizeSpan(DensityUtils.sp2px(mContext, 12));
            span.setSpan(sizeSpan, startBalance.indexOf(' '), startBalance.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            startBalanceView.setText(span);

            if (item.getDigest() != null && item.getDigest().length > 0) {
                tagView.setVisibility(View.VISIBLE);
                tagView.setText(item.getDigest()[0]);
                tagView.setSelected(item.getIsTimeDeposit() == 1);
            } else {
                tagView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface OnItemClickedListener {

        void onItemClicked(SavingProduct product, int position);
    }

}