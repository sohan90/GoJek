package com.gojek.application.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gojek.application.R;
import com.gojek.application.databinding.ItemTrendingBinding;
import com.gojek.application.model.TrendingResponse;

import java.util.List;

import javax.inject.Inject;

public final class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    private List<TrendingResponse> list;

    @Inject
    public TrendingAdapter(List<TrendingResponse> list) {
        this.list = list;
    }

    public void setData(List<TrendingResponse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingBinding binding = DataBindingUtil.inflate(LayoutInflater
                        .from(parent.getContext()), R.layout.item_trending,
                parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingResponse trendingResponse = list.get(position);
        holder.bindData(trendingResponse);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendingBinding itemTrendingBinding;

        private ViewHolder(@NonNull ItemTrendingBinding itemView) {
            super(itemView.getRoot());

            this.itemTrendingBinding = itemView;

            itemView.getRoot().setOnClickListener((v) -> {

                TrendingResponse trendingResponse = list.get(getAdapterPosition());
                boolean expanded = trendingResponse.isExpand();
                trendingResponse.setExpand(!expanded);
                notifyItemChanged(getAdapterPosition());

            });

        }

        private void bindData(TrendingResponse trendingResponse) {

            itemTrendingBinding.setViewModel(trendingResponse);

            boolean isExpanded = trendingResponse.isExpand();
            // Set the visibility based on state
            itemTrendingBinding.itemExpandLyt.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }
}
