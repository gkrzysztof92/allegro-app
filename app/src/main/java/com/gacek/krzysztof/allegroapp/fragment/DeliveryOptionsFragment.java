package com.gacek.krzysztof.allegroapp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.adapter.TabsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOptionsFragment extends Fragment implements TabHost.OnTabChangeListener {

    public static String TAG = "DeliveryOptionsFragment";

    private List<Fragment> fragmentList;
    private List<String> titleList;

    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;
    private TabLayout tabLayout;

    public DeliveryOptionsFragment() {
    }

    public static DeliveryOptionsFragment newInstance() {
        return new DeliveryOptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Create View");
        View view = inflater.inflate(R.layout.fragment_delivery_options, container, false);
        fragmentList = new ArrayList<>();
        fragmentList.add(InPostDeliveryOptionsFragment.newInstance());
        titleList = new ArrayList<>();
        titleList.add("In Post");
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabsAdapter = new TabsAdapter(getFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(tabsAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

    }
}
