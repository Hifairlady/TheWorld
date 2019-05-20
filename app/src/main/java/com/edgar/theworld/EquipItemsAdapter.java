package com.edgar.theworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EquipItemsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<EquipItem> equipItems = new ArrayList<>();

    public EquipItemsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_equip_item_style,
                parent, false);
        return new NormalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewHolder = (NormalViewHolder) holder;
        viewHolder.tvNameChs.setText(equipItems.get(viewHolder.getAdapterPosition()).getNameChs());
        viewHolder.tvNameEng.setText(equipItems.get(viewHolder.getAdapterPosition()).getNameEng());
        viewHolder.tvLevel.setText(equipItems.get(viewHolder.getAdapterPosition()).getItemLevel());
        viewHolder.tvQuality.setText(equipItems.get(viewHolder.getAdapterPosition()).getItemQuality());
    }

    @Override
    public int getItemCount() {
        return (equipItems == null ? 0 : equipItems.size());
    }

    public void setAllEquips(List<EquipItem> equips) {
        equipItems = new ArrayList<>();
        equipItems.addAll(equips);
        notifyDataSetChanged();
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvNameChs;
        private TextView tvNameEng;
        private TextView tvLevel;
        private TextView tvQuality;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_item_icon);
            tvNameChs = itemView.findViewById(R.id.tv_item_name_chs);
            tvNameEng = itemView.findViewById(R.id.tv_item_name_eng);
            tvLevel = itemView.findViewById(R.id.tv_item_level);
            tvQuality = itemView.findViewById(R.id.tv_item_quality);
        }
    }

}
