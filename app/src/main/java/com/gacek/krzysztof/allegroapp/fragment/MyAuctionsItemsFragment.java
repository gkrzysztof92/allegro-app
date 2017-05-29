package com.gacek.krzysztof.allegroapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.adapter.SellItemAdapter;
import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.dto.SellItem;

import java.util.ArrayList;
import java.util.List;


public class MyAuctionsItemsFragment extends Fragment {

    public static final String TAG = "MyAuctionsItemsFragment";

    private PreferencesManager preferencesManager;

    private List<SellItem> sellItems;
    private RecyclerView recyclerView;
    private SellItemAdapter sellItemAdapter;


    public MyAuctionsItemsFragment() {
    }

    public static MyAuctionsItemsFragment newInstance() {
        MyAuctionsItemsFragment fragment = new MyAuctionsItemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferencesManager.initializeInstance(getContext());
        preferencesManager = PreferencesManager.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_auctions_items, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById
                (R.id.fragment_my_auctions_auctionList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sellItemAdapter = new SellItemAdapter(this.sellItems);
        recyclerView.setAdapter(sellItemAdapter);

        return view;
    }

    public void setSellItems(List<SellItem> sellItems) {
        this.sellItems = sellItems;
        if (sellItemAdapter != null) {
            this.sellItemAdapter.setSellItems(sellItems);
            this.sellItemAdapter.notifyDataSetChanged();
        }
    }
}
