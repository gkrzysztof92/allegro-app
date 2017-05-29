package com.gacek.krzysztof.allegroapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.gacek.krzysztof.allegroapp.dto.DoLoginRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoLoginResponseEnvelope;
import com.gacek.krzysztof.allegroapp.fragment.AccountDataFormFragment;
import com.gacek.krzysztof.allegroapp.model.AccountData;
import com.gacek.krzysztof.allegroapp.service.AllegroLoginService;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;
import com.gacek.krzysztof.allegroapp.util.MessagesType;
import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {


    private Button singUpBtn;
    private CoordinatorLayout coordinatorLayout;

    private AllegroLoginService allegroLoginService;
    private PreferencesManager preferencesManager;

    private @Nullable AccountData accountData;
    private AccountDataFormFragment accountDataFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mockAccountData();
        allegroLoginService = AllegroServiceFactory.createService(AllegroLoginService.class, getApplicationContext());
        preferencesManager = PreferencesManager.getInstance();

        setContentView(R.layout.activity_login);
        singUpBtn = (Button) findViewById(R.id.login_loginBtn);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_coordinatorLayout);

        accountDataFormFragment = AccountDataFormFragment.newInstance(accountData);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_accountData, accountDataFormFragment, AccountDataFormFragment.TAG)
                .commit();

        singUpBtn.setOnClickListener(l -> {
            if (accountDataFormFragment.isValidForm()) {
                accountData = accountDataFormFragment.getAccountData();
                login();
            }
        });
    }

    public void login() {
        DoLoginRequestEnvelope env = new DoLoginRequestEnvelope();
        env.setT1UserLogin(accountData.getEmail());
        env.setT2UserPassword(accountData.getPassword());
        env.setT3CountryCode(1);
        env.setT4webapiKey(accountData.getWebApiKey());
        env.setT5localVersion(accountData.getLocaleVersion());
        singUpBtn.setEnabled(false);
        allegroLoginService.requestCall(env)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Utils.showSnackBarNotification(coordinatorLayout, "Zalogowano",
                                    MessagesType.SUCCESS);
                            saveLoginData(data);
                            backToMainActivity();
                        },
                        error -> Utils.handleError(error, coordinatorLayout)
                );
    }

    public void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void mockAccountData() {
        accountData = new AccountData();
        accountData.setEmail("gkrzysztof92@gmail.com");
        accountData.setWebApiKey("s00b86fc");
        accountData.setLocaleVersion(1488971865);
        accountData.setPassword("00b86fce72B60823");
    }

    public void saveLoginData(DoLoginResponseEnvelope loginData) {
        preferencesManager.putStringValue(PreferencesManager.USER_LOGIN_KEY, accountData.getEmail());
        preferencesManager.putStringValue(PreferencesManager.USER_PASSWORD_KEY, accountData.getPassword());
        preferencesManager.putStringValue(PreferencesManager.WEB_API_KEY, accountData.getWebApiKey());
        preferencesManager.putStringValue(PreferencesManager.LOCAL_VERSION_KEY,
                String.valueOf(accountData.getLocaleVersion()));
        preferencesManager.putStringValue(PreferencesManager.COUNTRY_CODE_KEY, "1");
        preferencesManager.putStringValue(PreferencesManager.SESSION_ID_KEY, loginData.getSessionHandlePart());
    }

}
