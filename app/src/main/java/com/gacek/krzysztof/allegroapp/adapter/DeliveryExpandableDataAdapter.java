package com.gacek.krzysztof.allegroapp.adapter;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.model.DeliveryOption;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;


import java.util.List;

import rx.Subscription;


public class DeliveryExpandableDataAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<DeliveryOption> deliveryOptions;

    private Subscription firstItemPriceSubscription;
    private Subscription nextItemPriceSubscription;
    private Subscription qtyInPackageSubscription;
    private Subscription nameCheckBoxSubscription;

    public DeliveryExpandableDataAdapter(List<DeliveryOption> deliveryOptions, Context context) {
        this.deliveryOptions = deliveryOptions;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return deliveryOptions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deliveryOptions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return deliveryOptions.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition + 100;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        DeliveryOption option = (DeliveryOption) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.delivery_option_header, null);
        }

        CheckBox nameCheckBox = (CheckBox) convertView.findViewById(R.id.deliveryOptionHeader);
        nameCheckBox.setText(option.getName());
        nameCheckBox.setChecked(option.isEnabled());


        Subscription nameCheckBoxSubscription2 = RxView.clicks(nameCheckBox)
                    .subscribe(e -> {deliveryOptions.get(groupPosition).setEnabled(nameCheckBox.isChecked());
                        Log.d("DDD", "Set tttt " + nameCheckBox.isEnabled());
                    });

        Log.d("DDD", "Crate " + groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        DeliveryOption option = (DeliveryOption) getGroup(groupPosition);

        if(firstItemPriceSubscription != null) {
            firstItemPriceSubscription.unsubscribe();
            nextItemPriceSubscription.unsubscribe();
            qtyInPackageSubscription.unsubscribe();
        }

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.delivery_option_data, null);

        }

        EditText firstItemPriceEditText = (EditText) convertView.findViewById(R.id.delivery_firstItemPrice);
        firstItemPriceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        firstItemPriceEditText.setText(String.valueOf(option.getFirstItemPriceValue()));

        EditText nextItemPriceEditText = (EditText) convertView.findViewById(R.id.delivery_nextItemPrice);
        nextItemPriceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        nextItemPriceEditText.setText(String.valueOf(option.getNextItemPriceValue()));

        EditText qtyInPackageEditText = (EditText) convertView.findViewById(R.id.delivery_qtyInPackage) ;
        qtyInPackageEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        qtyInPackageEditText.setText(String.valueOf(option.getQtyInPackageValue()));


        firstItemPriceSubscription = RxTextView.textChanges(firstItemPriceEditText)
                .subscribe(text -> option.setFirstItemPriceValue(Float.parseFloat(text.toString())));

        nextItemPriceSubscription = RxTextView.textChanges(nextItemPriceEditText)
                .subscribe(text -> option.setNextItemPriceValue(Float.parseFloat(text.toString())));

        qtyInPackageSubscription = RxTextView.textChanges(qtyInPackageEditText)
                .subscribe(text -> option.setQtyInPackageValue(Integer.parseInt(text.toString())));

        Log.d("DDD", String.valueOf(groupPosition));
        Log.d("DDD", option.toString());

        return convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
