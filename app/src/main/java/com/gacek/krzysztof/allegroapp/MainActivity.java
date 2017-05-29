package com.gacek.krzysztof.allegroapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.asynctask.CategoryPersistTask;
import com.gacek.krzysztof.allegroapp.data.DatabaseHelper;
import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.dto.DoGetCatsDataRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetMySellItemsRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoLoginRequestEnvelope;
import com.gacek.krzysztof.allegroapp.fragment.AddressSettingsFragment;
import com.gacek.krzysztof.allegroapp.fragment.DeliveryOptionsFragment;
import com.gacek.krzysztof.allegroapp.fragment.MyAuctionsItemsFragment;
import com.gacek.krzysztof.allegroapp.fragment.MyItemsFragment;
import com.gacek.krzysztof.allegroapp.service.AllegroCategoryService;
import com.gacek.krzysztof.allegroapp.service.AllegroLoginService;
import com.gacek.krzysztof.allegroapp.service.AllegroSellItemsService;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;
import com.gacek.krzysztof.allegroapp.util.Utils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_navView)
    NavigationView navigationView;
    @BindView(R.id.header_userEmail)
    TextView userEmailDrawerLayout;
    private Toolbar toolbar;

    private FragmentManager fragmentManager;
    private PreferencesManager preferencesManager;

    private AllegroCategoryService allegroCategoryService;
    private AllegroLoginService allegroLoginService;
    private AllegroSellItemsService allegroSellItemsService;

    private MyAuctionsItemsFragment myAuctionsItemsFragment;
    private MyItemsFragment myItemsFragment;
    private AddressSettingsFragment addressSettingsFragment;
    private DeliveryOptionsFragment deliveryOptionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Moje Aukcje");

        PreferencesManager.initializeInstance(getBaseContext());
        preferencesManager = PreferencesManager.getInstance();
        fragmentManager = getSupportFragmentManager();

        allegroCategoryService = AllegroServiceFactory
                .createService(AllegroCategoryService.class, getApplicationContext());
        allegroLoginService = AllegroServiceFactory
                .createService(AllegroLoginService.class, getApplicationContext());
        allegroSellItemsService = AllegroServiceFactory
                .createService(AllegroSellItemsService.class, getApplicationContext());

        myAuctionsItemsFragment = new MyAuctionsItemsFragment();
        createFragment(R.id.fragmentContainer, myAuctionsItemsFragment,
                MyAuctionsItemsFragment.TAG);

        setUpNavigation();
        if (!isSetUpUserData()) {
            goToLoginActivity();
        } else {
            login();
            loadCategories();
        }
    }

    public void setUpNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.main_navView);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void login() {
            DoLoginRequestEnvelope doLoginRequestEnvelope = new DoLoginRequestEnvelope();
            doLoginRequestEnvelope.setT1UserLogin(preferencesManager.
                    getStringValue(PreferencesManager.USER_LOGIN_KEY));
            doLoginRequestEnvelope.setT2UserPassword(preferencesManager
                    .getStringValue(PreferencesManager.USER_PASSWORD_KEY));
            doLoginRequestEnvelope.setT3CountryCode(1);
            doLoginRequestEnvelope.setT4webapiKey(preferencesManager
                    .getStringValue(PreferencesManager.WEB_API_KEY));
            doLoginRequestEnvelope.setT5localVersion(preferencesManager
                    .getIntValue(PreferencesManager.LOCAL_VERSION_KEY));

            allegroLoginService.requestCall(doLoginRequestEnvelope)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {
                                preferencesManager.putStringValue(PreferencesManager.SESSION_ID_KEY,
                                        data.getSessionHandlePart());
                                loadUserAuctions();
                            },
                            error -> {
                                Utils.handleError(error, drawerLayout);
                            }
                    );
    }

    private void loadUserAuctions() {

        DoGetMySellItemsRequestEnvelope req =
                new DoGetMySellItemsRequestEnvelope();
        req.setSessionId(preferencesManager.getStringValue(
                PreferencesManager.SESSION_ID_KEY));

        allegroSellItemsService.requestCall(req)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (myAuctionsItemsFragment == null) {
                        myAuctionsItemsFragment = new MyAuctionsItemsFragment();
                    }
                    myAuctionsItemsFragment.setSellItems(data.getSellItemsList());
                }, error -> Utils.handleError(error, drawerLayout));
    }

    private void loadCategories() {
        if (!areDownloadedCategories()) {
            DoGetCatsDataRequestEnvelope env = new DoGetCatsDataRequestEnvelope();
            env.setCountryId(Integer
                    .parseInt(preferencesManager.getStringValue(PreferencesManager.COUNTRY_CODE_KEY)));
            env.setLocalVersion(Integer
                    .parseInt(preferencesManager.getStringValue(PreferencesManager.LOCAL_VERSION_KEY)));
            env.setWebapiKey(preferencesManager.getStringValue(PreferencesManager.WEB_API_KEY));
            allegroCategoryService.requestCall(env)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        String key = data.getVerKey();
                        String str = data.getVerStr();
                        new CategoryPersistTask(new DatabaseHelper(getBaseContext()), data).execute();
                        preferencesManager.putStringValue(PreferencesManager.CATEGORY_VER_KEY,
                                key);
                        preferencesManager.putStringValue(PreferencesManager.CATEGORY_VER_STR,
                                str);
                    }, error -> Utils.handleError(error, drawerLayout));
        }
    }



    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean isSetUpUserData() {
        if (preferencesManager.getStringValue(PreferencesManager.USER_LOGIN_KEY) == null ||
                preferencesManager.getStringValue(PreferencesManager.USER_PASSWORD_KEY) == null ||
                preferencesManager.getStringValue(PreferencesManager.COUNTRY_CODE_KEY) == null ||
                preferencesManager.getStringValue(PreferencesManager.WEB_API_KEY) == null ||
                preferencesManager.getStringValue(PreferencesManager.LOCAL_VERSION_KEY) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean areDownloadedCategories() {
        if (preferencesManager.getStringValue(PreferencesManager.CATEGORY_VER_STR) == null ||
                preferencesManager.getStringValue(PreferencesManager.CATEGORY_VER_KEY) == null) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        hideDrawer();
        switch (item.getItemId()) {
            case R.id.item_my_auction:
                if(myAuctionsItemsFragment == null) {
                    myAuctionsItemsFragment = new MyAuctionsItemsFragment();
                }
                loadUserAuctions();
                toolbar.setTitle("Moje Aukcje");
                replaceFragment(R.id.fragmentContainer, myAuctionsItemsFragment,
                        MyAuctionsItemsFragment.TAG);
                break;
            case R.id.item_my_items:
                replaceFragment(R.id.fragmentContainer, new MyItemsFragment(),
                        MyItemsFragment.TAG);
                toolbar.setTitle("Moje Przedmioty");
                break;
            case R.id.item_address:
                toolbar.setTitle("Dane Użytkownika");
                addressSettingsFragment = AddressSettingsFragment.newInstance();
                replaceFragment(R.id.fragmentContainer, addressSettingsFragment,
                        AddressSettingsFragment.TAG);
                break;
            case R.id.item_delivery:
                toolbar.setTitle("Ustawienia Dostawy");
                if (deliveryOptionsFragment == null) {
                    deliveryOptionsFragment = DeliveryOptionsFragment.newInstance();
                }
                replaceFragment(R.id.fragmentContainer, deliveryOptionsFragment,
                        DeliveryOptionsFragment.TAG);
                break;
        }
        return true;
    }

    public void createFragment(int containerId, Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment, tag);
        transaction.commit();
    }

    public void removeFragment(String tag) {
        Fragment fragment = (Fragment) fragmentManager.findFragmentByTag(tag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public void replaceFragment(int containerId, Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.commit();
    }

    public void showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void hideDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            hideDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
