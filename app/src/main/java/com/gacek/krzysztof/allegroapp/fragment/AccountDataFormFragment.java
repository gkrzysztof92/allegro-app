package com.gacek.krzysztof.allegroapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.model.AccountData;
import com.gacek.krzysztof.allegroapp.util.Utils;

public class AccountDataFormFragment extends Fragment {

    public static final String TAG = "AccountDataFormFragment";

    private EditText emailEditText;
    private TextInputLayout emailTextInputLayout;
    private EditText webApiKeyEditText;
    private TextInputLayout webApiKeyTextInputLayout;
    private EditText localeVersionEditText;
    private TextInputLayout localeVersionTextInputLayout;
    private EditText passwordEditText;
    private TextInputLayout passwordTextInputLayout;

    private @Nullable AccountData accountData;

    public AccountDataFormFragment() {
    }

    public static AccountDataFormFragment newInstance(AccountData accountData) {
        AccountDataFormFragment fragment = new AccountDataFormFragment();
        fragment.setAccountData(accountData);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_data_form, container, false);
        emailEditText = (EditText) view.findViewById(R.id.accountData_email);
        emailTextInputLayout = (TextInputLayout) view.findViewById(R.id.accountData_emailLayout);
        webApiKeyEditText = (EditText) view.findViewById(R.id.accountData_webApiKey);
        webApiKeyTextInputLayout = (TextInputLayout) view.findViewById(R.id.accountData_webApiKeyLayout);
        localeVersionEditText = (EditText) view.findViewById(R.id.accountData_localVersion);
        localeVersionTextInputLayout = (TextInputLayout) view.findViewById(R.id.accountData_localVersionLayout);
        passwordEditText = (EditText) view.findViewById(R.id.accountData_password);
        passwordTextInputLayout = (TextInputLayout) view.findViewById(R.id.accountData_passwordLayout);
        initializeForm();
        return view;
    }

    public void initializeForm() {
        if (accountData != null) {
            emailEditText.setText(accountData.getEmail());
            webApiKeyEditText.setText(accountData.getWebApiKey());
            localeVersionEditText.setText(String.valueOf(accountData.getLocaleVersion()));
            passwordEditText.setText(accountData.getPassword());
        }
    }

    public boolean isValidForm() {
        boolean isValid = true;
        String email = emailEditText.getText().toString();
        String webApiKey = webApiKeyEditText.getText().toString();
        String localeVersion = localeVersionEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty()) {
            Utils.showErrorMessage(emailTextInputLayout, "Adres email jest wymagane");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.showErrorMessage(emailTextInputLayout, "Adres email nie jest poprawny");
            isValid = false;
        } else {
            Utils.hideErrorMessage(emailTextInputLayout);
        }

        if (webApiKey.isEmpty()) {
            Utils.showErrorMessage(webApiKeyTextInputLayout, "Klucz WebApi jest wymagany");
            isValid = false;
        } else {
            Utils.hideErrorMessage(webApiKeyTextInputLayout);
        }

        if (localeVersion.isEmpty()) {
            Utils.showErrorMessage(localeVersionTextInputLayout, "Locale Version jest wymagana");
            isValid = false;
        } else {
            Utils.hideErrorMessage(localeVersionTextInputLayout);
        }

        if (password.isEmpty()) {
            Utils.showErrorMessage(passwordTextInputLayout, "Haslo jest wymagane");
            isValid = false;
        } else {
            Utils.hideErrorMessage(passwordTextInputLayout);
        }

        return isValid;
    }

    public AccountData getAccountData() {
        accountData.setEmail(emailEditText.getText().toString());
        accountData.setPassword(passwordEditText.getText().toString());
        accountData.setLocaleVersion(Integer.valueOf(localeVersionEditText.getText().toString()));
        accountData.setWebApiKey(webApiKeyEditText.getText().toString());
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

}
