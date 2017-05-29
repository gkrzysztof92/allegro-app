package com.gacek.krzysztof.allegroapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.dto.SellItem;

import java.util.List;



public class SellItemAdapter extends RecyclerView.Adapter<SellItemAdapter.ViewHolder> {

    private static final String TAG = "SELL_ITEM_ADAPTER";

    List<SellItem> sellItems;

    public SellItemAdapter(List<SellItem> sellItems) {
        this.sellItems = sellItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sell_item_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SellItem sellItem = sellItems.get(position);
        holder.bindView(sellItem);
    }

    @Override
    public int getItemCount() {
        if (sellItems == null) {
            return 0;
        } else {
            return sellItems.size();
        }
    }

    public void setSellItems(List<SellItem> sellItems) {
        this.sellItems = sellItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTextView;
        private TextView itemPriceTextView;
        private TextView itemsLeftTextView;
        private TextView timeLeftTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View view) {
            itemNameTextView = (TextView) itemView.findViewById(R.id.sell_item_list_row_itemName);
            itemPriceTextView = (TextView) itemView.findViewById(R.id.sell_item_list_row_itemPrice);
            itemsLeftTextView = (TextView) itemView.findViewById(R.id.sell_item_list_row_itemsLeft);
            timeLeftTextView = (TextView) itemView.findViewById(R.id.sell_item_list_row_timeLeft);

        }

        public void bindView(SellItem sellItem) {
            itemNameTextView.setText(sellItem.getItemTitle());
            itemPriceTextView.setText("Cena: " + sellItem.getItemPrice().get(0).getPriceValue() + " zl");
            itemsLeftTextView.setText("Ilosc: " + String.valueOf(sellItem.getItemStartQuantity() - sellItem.getPrivaitemSoldQuantity()));
            timeLeftTextView.setText(sellItem.getItemEndTimeLeft());
        }

    }

}
