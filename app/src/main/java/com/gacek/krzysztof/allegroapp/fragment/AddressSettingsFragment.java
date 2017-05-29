package com.gacek.krzysztof.allegroapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.dto.DoGetStatesInfoRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoLoginResponseEnvelope;
import com.gacek.krzysztof.allegroapp.dto.State;
import com.gacek.krzysztof.allegroapp.model.AccountData;
import com.gacek.krzysztof.allegroapp.model.AddressData;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;
import com.gacek.krzysztof.allegroapp.service.AllegroStatesService;
import com.gacek.krzysztof.allegroapp.util.MessagesType;
import com.gacek.krzysztof.allegroapp.util.Utils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AddressSettingsFragment extends Fragment {

    public static final String TAG = "ADDRESS_SETTINGS_FRAGMENT";

    private Toolbar toolbar;

    private PreferencesManager preferencesManager;
    private AllegroStatesService allegroStatesService;
    private FragmentManager fragmentManager;

    private List<State> states;
    private AccountData accountData;
    private AddressData addressData;
    private AccountDataFormFragment accountDataFormFragment;
    private AddressDataFormFragment addressDataFormFragment;

    public AddressSettingsFragment() {
    }

    public static AddressSettingsFragment newInstance() {
        AddressSettingsFragment fragment = new AddressSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesManager.initializeInstance(getContext());
        preferencesManager = PreferencesManager.getInstance();
        allegroStatesService = AllegroServiceFactory.createService(AllegroStatesService.class, getContext());
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_settings, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.user_data_menu);

        View saveButton = getActivity().findViewById(R.id.userDataMenu_save);
        RxView.clicks(saveButton)
                .subscribe(aVoid -> processForm());

        DoGetStatesInfoRequestEnvelope request = new DoGetStatesInfoRequestEnvelope();
        request.setCountryCode(1);
        request.setWebapiKey(preferencesManager.getStringValue(PreferencesManager.WEB_API_KEY));

        allegroStatesService.requestCall(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    addressData = loadAddressData();
                    addressDataFormFragment = AddressDataFormFragment.newInstance(addressData,
                            data.getStatesInfoArray());
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.userData_addressData, addressDataFormFragment)
                            .commit();
                }, error -> Utils.handleError(error, getActivity().findViewById(R.id.drawer_layout)));

        accountData = loadAccountData();
        accountDataFormFragment = AccountDataFormFragment.newInstance(accountData);
        fragmentManager
                .beginTransaction()
                .add(R.id.userData_accountData, accountDataFormFragment)
                .commit();

        return view;
    }

    public void saveUserData(DoLoginResponseEnvelope loginData) {
        preferencesManager.putStringValue(PreferencesManager.USER_LOGIN_KEY, accountData.getEmail());
        preferencesManager.putStringValue(PreferencesManager.USER_PASSWORD_KEY, accountData.getPassword());
        preferencesManager.putStringValue(PreferencesManager.WEB_API_KEY, accountData.getWebApiKey());
        preferencesManager.putStringValue(PreferencesManager.LOCAL_VERSION_KEY,
                String.valueOf(accountData.getLocaleVersion()));
        preferencesManager.putStringValue(PreferencesManager.COUNTRY_CODE_KEY, "1");
        preferencesManager.putStringValue(PreferencesManager.SESSION_ID_KEY, loginData.getSessionHandlePart());
    }

    public AccountData loadAccountData() {
        AccountData accountData = new AccountData();
        accountData.setEmail(preferencesManager.getStringValue(PreferencesManager.USER_LOGIN_KEY));
        accountData.setPassword(preferencesManager.getStringValue(PreferencesManager.USER_PASSWORD_KEY));
        accountData.setWebApiKey(preferencesManager.getStringValue(PreferencesManager.WEB_API_KEY));
        accountData.setLocaleVersion(preferencesManager.getIntValue(PreferencesManager.LOCAL_VERSION_KEY));
        return accountData;
    }

    public void saveAddressData(AddressData addressData) {
        preferencesManager.putStringValue(PreferencesManager.CITY_KEY, addressData.getCity());
        preferencesManager.putStringValue(PreferencesManager.POST_CODE_KEY, addressData.getPostCode());
        preferencesManager.putStringValue(PreferencesManager.STATE_KEY, String.valueOf(addressData.getState()));
    }

    public AddressData loadAddressData() {
        AddressData addressData = new AddressData();
        addressData.setCity(preferencesManager.getStringValue(PreferencesManager.CITY_KEY));
        addressData.setPostCode(preferencesManager.getStringValue(PreferencesManager.POST_CODE_KEY));
        addressData.setState(preferencesManager.getIntValue(PreferencesManager.STATE_KEY));
        return addressData;
    }

    public void processForm() {
        boolean isValidAccountDataForm = accountDataFormFragment.isValidForm();
        boolean isValidAddressForm = addressDataFormFragment.isValidForm();
        if (isValidAccountDataForm && isValidAddressForm) {
            saveAddressData(addressDataFormFragment.getAddressData());
            Utils.showSnackBarNotification(getActivity().findViewById(R.id.drawer_layout),
                    "Dane użytkownika zostaly zapisane!", MessagesType.SUCCESS);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar.getMenu().clear();
    }

}
