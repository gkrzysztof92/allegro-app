package com.gacek.krzysztof.allegroapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.adapter.DeliveryExpandableDataAdapter;
import com.gacek.krzysztof.allegroapp.model.DeliveryOption;
import com.gacek.krzysztof.allegroapp.util.SellFormCfg;

import java.util.ArrayList;
import java.util.List;


public class InPostDeliveryOptionsFragment extends Fragment {

    private final static String TAG = "InPostDeliveryOptions";
    private final static String DELIVERY_TYPE = "INPOST";

    DeliveryExpandableDataAdapter deliveryExpandableDataAdapter;
    ExpandableListView expListView;
    List<DeliveryOption> deliveryOptions;

    public InPostDeliveryOptionsFragment() {
    }

    public static InPostDeliveryOptionsFragment newInstance() {
        Log.d(TAG, "Construct");
        return new InPostDeliveryOptionsFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Create View");
        View view = inflater.inflate(R.layout.fragment_in_post_delivery_options, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.expView);
        prepareData();
        deliveryExpandableDataAdapter = new DeliveryExpandableDataAdapter(deliveryOptions, getContext());
        expListView.setAdapter(deliveryExpandableDataAdapter);
        return view;
    }

    public void prepareData() {

        deliveryOptions = new ArrayList<>();

        DeliveryOption minipaczka = new DeliveryOption();
        minipaczka.setName("Allegro MiniPaczka InPost");
        minipaczka.setFirstItemPriceId(SellFormCfg.ALLEGRO_MINIPACZKA_INPOST_FIRST_FIELD.getFieldId());
        minipaczka.setNextItemPriceId(SellFormCfg.ALLEGRO_MINIPACZKA_INPOST_NEXT_FIELD.getFieldId());
        minipaczka.setQtyInPackageId(SellFormCfg.ALLEGRO_MINIPACZKA_INPOST_QTY_FIELD.getFieldId());
        minipaczka.setType(DELIVERY_TYPE);
        deliveryOptions.add(minipaczka);

        DeliveryOption paczkomat = new DeliveryOption();
        paczkomat.setName("Allegro Paczkomaty InPost");
        paczkomat.setFirstItemPriceId(SellFormCfg.ALLEGRO_PACZKOMATY_INPOST_FIRST_FIELD.getFieldId());
        paczkomat.setNextItemPriceId(SellFormCfg.ALLEGRO_PACZKOMATY_INPOST_NEXT_FIELD.getFieldId());
        paczkomat.setQtyInPackageId(SellFormCfg.ALLEGRO_PACZKOMATY_INPOST_QTY_FIELD.getFieldId());
        paczkomat.setType(DELIVERY_TYPE);
        deliveryOptions.add(paczkomat);

        DeliveryOption kurier = new DeliveryOption();
        kurier.setName("Allegro Kurier InPost");
        kurier.setFirstItemPriceId(SellFormCfg.ALLEGRO_KURIER_INPOST_FIRST_FIELD.getFieldId());
        kurier.setNextItemPriceId(SellFormCfg.ALLEGRO_KURIER_INPOST_NEXT_FIELD.getFieldId());
        kurier.setQtyInPackageId(SellFormCfg.ALLEGRO_KURIER_INPOST_QTY_FIELD.getFieldId());
        kurier.setType(DELIVERY_TYPE);
        deliveryOptions.add(kurier);

    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
