package com.gacek.krzysztof.allegroapp.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.dto.State;
import com.gacek.krzysztof.allegroapp.model.AddressData;
import com.gacek.krzysztof.allegroapp.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddressDataFormFragment extends Fragment {

    private EditText cityEditText;
    private TextInputLayout cityInputLayout;
    private EditText postCodeEditText;
    private TextInputLayout postCodeInputLayout;
    private Spinner stateSpinner;

    private AddressData addressData;
    private List<State> states;

    public AddressDataFormFragment() {
    }

    public static AddressDataFormFragment newInstance(AddressData addressData, List<State> states) {
        AddressDataFormFragment fragment = new AddressDataFormFragment();
        fragment.setAddressData(addressData);
        fragment.setStates(states);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_data_form, container, false);
        cityEditText = (EditText) view.findViewById(R.id.addressData_city);
        cityInputLayout = (TextInputLayout) view.findViewById(R.id.addressData_cityLayout);
        postCodeEditText = (EditText) view.findViewById(R.id.addressData_postCode);
        postCodeInputLayout = (TextInputLayout) view.findViewById(R.id.addressData_postCodeLayout);
        stateSpinner = (Spinner) view.findViewById(R.id.addressData_state);
        populateAddresData();
        populateStateSpinner(states);
        return view;
    }

    public boolean isValidForm() {
        boolean isValid = true;
        String city = cityEditText.getText().toString();
        String postCode = postCodeEditText.getText().toString();

        if(city.isEmpty()) {
            Utils.showErrorMessage(cityInputLayout, "Miasto jest wymagane");
            isValid = false;
        } else {
            Utils.hideErrorMessage(cityInputLayout);
        }

        if(postCode.isEmpty()) {
            Utils.showErrorMessage(postCodeInputLayout, "Kod pocztowy jest wymagany");
            isValid = false;
        } else {
            Pattern postCodePattern = Pattern.compile("\\d\\d-\\d\\d\\d");
            Matcher postCodeMatcher = postCodePattern.matcher(postCodeEditText.getText().toString());
            if (!postCodeMatcher.matches()) {
                Utils.showErrorMessage(postCodeInputLayout, "Nieprawidlowy format kodu pocztowego");
                isValid = false;
            } else {
                Utils.hideErrorMessage(postCodeInputLayout);
            }
        }
        return isValid;
    }

    private void populateStateSpinner(List<State> statesInfoArray) {
        List<String> statesNames = new ArrayList<>();
        for (State state : statesInfoArray) {
            statesNames.add(state.getStateName());
        }
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, statesNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void populateAddresData() {
        if (addressData != null) {
            cityEditText.setText(addressData.getCity());
            postCodeEditText.setText(addressData.getPostCode());
        }
    }

    public AddressData getAddressData() {
        addressData.setCity(cityEditText.getText().toString());
        addressData.setPostCode(postCodeEditText.getText().toString());
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

}
