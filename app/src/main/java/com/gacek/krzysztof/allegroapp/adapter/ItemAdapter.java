package com.gacek.krzysztof.allegroapp.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.model.ItemThumbnail;
import com.gacek.krzysztof.allegroapp.util.Utils;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<ItemThumbnail> itemList;

    Subject<Integer> itemClickSubject = PublishSubject.create();
    Subject<Integer> itemLongClickSubject = PublishSubject.create();

    public ItemAdapter(List<ItemThumbnail> itemList) {
        this.itemList = itemList;
    }

    public Subject<Integer> getItemClickSubject() {
        return itemClickSubject;
    }

    public Subject<Integer> getItemLongClickSubject() {
        return itemLongClickSubject;
    }

    public void setItemList(List<ItemThumbnail> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemThumbnail item = itemList.get(position);
        holder.itemNameTextView.setText(item.getItemName());
        if (item.getPhoto() != null) {
            holder.itemImageView.setImageBitmap(Utils.convertBlobToBitmap(item.getPhoto()));
        }
        if (item.isSelected()) {
            holder.setSelectedItemBackground();
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTextView;
        private ImageView itemImageView;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.item_list_row_itemName);
            this.itemImageView = (ImageView) itemView.findViewById(R.id.item_list_row_itemImage);

            itemView.setOnClickListener( l -> {
                itemClickSubject.onNext(itemList.get(getAdapterPosition()).getItemId());
            });

            itemView.setOnLongClickListener( l -> {
                ItemThumbnail item;
                int selectedItemId = 0;
                for (int i = 0; i < itemList.size(); i++) {
                    item = itemList.get(i);
                    if (i == getAdapterPosition()) {
                        if (item.isSelected()) {
                            item.setSelected(false);
                        } else {
                            item.setSelected(true);
                            selectedItemId = item.getItemId();
                        }
                    } else {
                        item.setSelected(false);
                    }
                }
                notifyDataSetChanged();
                itemLongClickSubject.onNext(selectedItemId);
                return true;
            });
        }

        public void setSelectedItemBackground() {
            this.itemView.setBackgroundColor(Color.GRAY);
        }

    }
}
