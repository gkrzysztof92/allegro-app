package com.gacek.krzysztof.allegroapp;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsAttributeRepository;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsRepository;
import com.gacek.krzysztof.allegroapp.data.repo.PhotosRepository;
import com.gacek.krzysztof.allegroapp.dto.AuctionAttributeField;
import com.gacek.krzysztof.allegroapp.dto.DoNewAuctionExtRequestEnvelope;
import com.gacek.krzysztof.allegroapp.factory.AuctionAttributeFieldFactory;
import com.gacek.krzysztof.allegroapp.fragment.MyItemsFragment;
import com.gacek.krzysztof.allegroapp.model.Item;
import com.gacek.krzysztof.allegroapp.model.ItemAttribute;
import com.gacek.krzysztof.allegroapp.model.Photo;
import com.gacek.krzysztof.allegroapp.service.AllegroNewAuctionService;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;
import com.gacek.krzysztof.allegroapp.util.SellFormCfg;
import com.gacek.krzysztof.allegroapp.util.Utils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.jakewharton.rxbinding.widget.RxToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;

public class AddAuctionActivity extends AppCompatActivity {

    private ItemsRepository itemsRepository;
    private PhotosRepository photosRepository;
    private ItemsAttributeRepository itemsAttributeRepository;
    private PreferencesManager preferencesManager;
    private AllegroNewAuctionService allegroNewAuctionService;

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private TextView auctionTitleTextView;
    private TextView categoryTextView;
    private TextView quantityTextVew;
    private RadioGroup auctionTypeRadioGroup;
    private RadioButton buyNowRadionButton;
    private RadioButton auctionRadionButton;
    private TextInputLayout buyNowInputLayout;
    private EditText buyNowPriceEditText;
    private TextInputLayout startPriceInputLayout;
    private EditText startPriceEditText;
    private TextInputLayout quantityInputLayout;
    private EditText quantityEditText;

    private Subscription auctionTypesubscription;

    private boolean isBuyNowAuction = true;
    private int auctionType = 0;
    private int itemId;
    private Item item;
    private List<Photo> photos;
    private List<ItemAttribute> itemAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        ItemsRepository.initInstance(getContentResolver());
        itemsRepository = ItemsRepository.getInstance();
        PhotosRepository.initInstance(getContentResolver());
        photosRepository = PhotosRepository.getInstance();
        ItemsAttributeRepository.initInstance(getContentResolver());
        itemsAttributeRepository = ItemsAttributeRepository.getInstance();
        PreferencesManager.initializeInstance(this);
        preferencesManager = PreferencesManager.getInstance();
        allegroNewAuctionService = AllegroServiceFactory.createService(AllegroNewAuctionService.class, this);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.addAuction_layout);
        auctionTitleTextView = (TextView) findViewById(R.id.addAuction_title);
        categoryTextView = (TextView) findViewById(R.id.addAuction_category);
        quantityTextVew = (TextView) findViewById(R.id.addAuction_avaiableQty);
        auctionTypeRadioGroup = (RadioGroup) findViewById(R.id.addAuction_auctionType);
        buyNowRadionButton = (RadioButton) findViewById(R.id.addAuction_buyNow);
        buyNowRadionButton.setChecked(true);
        auctionRadionButton = (RadioButton) findViewById(R.id.addAuction_auction);
        buyNowInputLayout = (TextInputLayout) findViewById(R.id.addAuction_buyNowPriceLayout);
        buyNowPriceEditText = (EditText) findViewById(R.id.addAuction_buyNowPrice);
        startPriceInputLayout = (TextInputLayout) findViewById(R.id.addAuction_startPriceLayout);
        startPriceInputLayout.setEnabled(false);
        startPriceEditText = (EditText) findViewById(R.id.addAuction_startPrice);
        quantityInputLayout = (TextInputLayout) findViewById(R.id.addAuction_quantityLayout) ;
        quantityEditText = (EditText) findViewById(R.id.addAuction_quantity);

        RxRadioGroup.checkedChanges(auctionTypeRadioGroup)
                .subscribe(integer ->  {
                    if (integer == R.id.addAuction_buyNow) {
                        Utils.hideErrorMessage(startPriceInputLayout);
                        startPriceInputLayout.setEnabled(false);
                        isBuyNowAuction = true;
                    } else {
                        startPriceInputLayout.setEnabled(true);
                        isBuyNowAuction = false;
                    }
                });

        toolbar = (Toolbar) findViewById(R.id.addAuction_toolbar);
        toolbar.setTitle("Wystaw Aukcje");
        toolbar.inflateMenu(R.menu.add_auction_menu);
        toolbar.setNavigationOnClickListener(l -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        View addAuctionBtn = findViewById(R.id.addAuctionMenu_save);
        RxView.clicks(addAuctionBtn)
                .subscribe(aVoid -> {
                    if (validAuctionForm()) {
                        createAuction();
                    }
                });

        Bundle extras = getIntent().getExtras();
        itemId = extras.getInt(MyItemsFragment.PASSING_ID, -1);

        if (itemId > 0) {
            item = itemsRepository.findOne(itemId);
            auctionTitleTextView.setText(item.getName());
            categoryTextView.setText(item.getCategoryPath());
            photos = photosRepository.findByItemId(itemId);
            itemAttributes = itemsAttributeRepository.findByItemId(itemId);
            Log.d("DDD", itemAttributes.toString());
        }

    }

    private boolean validAuctionForm() {
        boolean isValid = true;
        String buyNowPrice = buyNowPriceEditText.getText().toString();
        String qty = quantityEditText.getText().toString();

        if (buyNowPrice.isEmpty()) {
            isValid = false;
            Utils.showErrorMessage(buyNowInputLayout, "Cena Kup Teraz jest wymagana!");
        } else {
            if (Integer.parseInt(buyNowPrice) <= 0) {
                Utils.showErrorMessage(buyNowInputLayout, "Cena Kup Teraz musi byc wieksza od 0!");
                isValid = false;
            } else {
                Utils.hideErrorMessage(buyNowInputLayout);
            }
        }

        if (!isBuyNowAuction) {
            String startPrice = startPriceEditText.getText().toString();
            if (startPrice.isEmpty()) {
                Utils.showErrorMessage(startPriceInputLayout, "Cena Minimalan jest wymagana!");
                isValid = false;
            } else {
                if (Integer.parseInt(startPrice) <= 0) {
                    Utils.showErrorMessage(startPriceInputLayout, "Cena Minimalna musi byc wieksza od 0!");
                    isValid = false;
                } else {
                    Utils.hideErrorMessage(startPriceInputLayout);
                }
            }
        }

        if (qty.isEmpty()) {
            isValid = false;
            Utils.showErrorMessage(quantityInputLayout, "Ilosc jest wymagana!");
        } else {
            if (Integer.parseInt(qty) <= 0) {
                Utils.showErrorMessage(quantityInputLayout, "Ilosc musi byc wieksza od 0!");
                isValid = false;
            } else {
                Utils.hideErrorMessage(quantityInputLayout);
            }
        }
        return isValid;
    }


    private void createAuction() {

        String sessionId = preferencesManager.getStringValue(PreferencesManager.SESSION_ID_KEY);
        ArrayList<AuctionAttributeField> fields = new ArrayList<>();
        fields.addAll(mapItemToAuctionAttributeFields());
        fields.addAll(mapItemAttributeToAuctionAttributeFields());
        fields.addAll(mapPhotosToAuctionAttributeFields());
        fields.addAll(mockDelivery());
        fields.addAll(mapAddresToAuctionAttributeFields());
        fields.addAll(mockOtherFields());

        DoNewAuctionExtRequestEnvelope req = new DoNewAuctionExtRequestEnvelope();
        req.setSessionHandle(sessionId);
        req.setFields(fields);

        allegroNewAuctionService.requestCall(req)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Intent intentData = new Intent();
                    intentData.setData(Uri.parse(String.valueOf(data.getItemId())));
                    setResult(RESULT_OK, intentData);
                    finish();
                }, error -> {
                    Utils.handleError(error, coordinatorLayout);
                });
    }


    private List<AuctionAttributeField> mapItemToAuctionAttributeFields() {
        List<AuctionAttributeField> fields = new ArrayList<>();
        AuctionAttributeField titleField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.TITLE_FIELD.getFieldId(), SellFormCfg.TITLE_FIELD.getFieldType(), item.getName());
        AuctionAttributeField descriptionField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.DESCRIPTION_FIELD.getFieldId(), SellFormCfg.DESCRIPTION_FIELD.getFieldType(),item.getDescription());
        AuctionAttributeField categoryField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.CATEGORY_FIELD.getFieldId(), SellFormCfg.CATEGORY_FIELD.getFieldType(), String.valueOf(item.getCategoryId()));
        fields.addAll(Arrays.asList(titleField, descriptionField, categoryField));
        return fields;
    }

    private List<AuctionAttributeField> mapPhotosToAuctionAttributeFields() {
        List<AuctionAttributeField> fields = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            AuctionAttributeField field = AuctionAttributeFieldFactory.getInstance(
                    SellFormCfg.PHOTO_1_FIELD.getFieldId() + i, SellFormCfg.PHOTO_1_FIELD.getFieldType(),
                    Base64.encodeToString(photos.get(i).getPhotoData(), Base64.DEFAULT));
            fields.add(field);
        }
        return fields;
    }

    private List<AuctionAttributeField> mapItemAttributeToAuctionAttributeFields() {
        List<AuctionAttributeField> fields = new ArrayList<>();
        for (ItemAttribute attribute : itemAttributes) {
            AuctionAttributeField field = AuctionAttributeFieldFactory.getInstance(
                    attribute.getAttributeId(), attribute.getAttributeType(), attribute.getAttributeValue());
            fields.add(field);
        }
        return fields;
    }

    private List<AuctionAttributeField> mockDelivery() {
        List<AuctionAttributeField> fields = new ArrayList<>();
        AuctionAttributeField field1 = AuctionAttributeFieldFactory.getInstance(SellFormCfg.ALLEGRO_KURIER_INPOST_FIRST_FIELD.getFieldId(),
                SellFormCfg.ALLEGRO_KURIER_INPOST_FIRST_FIELD.getFieldType(), "7.60");
        //AuctionAttributeField field2 = AuctionAttributeFieldFactory.getInstance(SellFormCfg.ALLEGRO_KURIER_INPOST_NEXT_FIELD.getFieldId(),
        //        SellFormCfg.ALLEGRO_KURIER_INPOST_NEXT_FIELD.getFieldType(), "7.60");
        //AuctionAttributeField field3 = AuctionAttributeFieldFactory.getInstance(SellFormCfg.ALLEGRO_KURIER_INPOST_QTY_FIELD.getFieldId(),
        //        SellFormCfg.ALLEGRO_KURIER_INPOST_QTY_FIELD.getFieldType(), "4");
        //fields.addAll(Arrays.asList(field1, field2, field3));
        fields.addAll(Arrays.asList(field1));
        return fields;
    }

    private List<AuctionAttributeField> mapAddresToAuctionAttributeFields() {
        String city = preferencesManager.getStringValue(PreferencesManager.CITY_KEY);
        String postCode = preferencesManager.getStringValue(PreferencesManager.POST_CODE_KEY);
        String stateId = preferencesManager.getStringValue(PreferencesManager.STATE_KEY);
        List<AuctionAttributeField> fields = new ArrayList<>();
        AuctionAttributeField countryField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.COUNTRY_FIELD.getFieldId(), SellFormCfg.COUNTRY_FIELD.getFieldType(), "1");
        AuctionAttributeField cityField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.TOWN_FIELD.getFieldId(), SellFormCfg.TOWN_FIELD.getFieldType(), city);
        AuctionAttributeField postCodeField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.POST_CODE_FIELD.getFieldId(), SellFormCfg.POST_CODE_FIELD.getFieldType(), postCode);
        AuctionAttributeField stateField = AuctionAttributeFieldFactory.getInstance(
                SellFormCfg.DISTRICT_FIELD.getFieldId(), SellFormCfg.DISTRICT_FIELD.getFieldType(), "10");
        fields.addAll(Arrays.asList(countryField, cityField, postCodeField, stateField));
        return fields;
    }

    private List<AuctionAttributeField> mockOtherFields() {
        List<AuctionAttributeField> fields = new ArrayList<>();

        AuctionAttributeField quantity = AuctionAttributeFieldFactory.getInstance(SellFormCfg.QUANTITY_FIELD.getFieldId(),
                SellFormCfg.QUANTITY_FIELD.getFieldType(), quantityEditText.getText().toString());
        AuctionAttributeField quantityType = AuctionAttributeFieldFactory.getInstance(SellFormCfg.QUANTITY_TYPE_FIELD.getFieldId(),
                SellFormCfg.QUANTITY_TYPE_FIELD.getFieldType(), String.valueOf(item.getQuantityTypeId()));

        AuctionAttributeField auctionType = AuctionAttributeFieldFactory.getInstance(SellFormCfg.AUCTION_TYPE_FIELD.getFieldId(),
                SellFormCfg.AUCTION_TYPE_FIELD.getFieldType(), String.valueOf(this.auctionType));
        AuctionAttributeField auctionTime = AuctionAttributeFieldFactory.getInstance(SellFormCfg.AUCTION_TIME_FIELD.getFieldId(),
                SellFormCfg.AUCTION_TIME_FIELD.getFieldType(), "2");
        AuctionAttributeField buyNowPrice = AuctionAttributeFieldFactory.getInstance(SellFormCfg.BUY_NOW_PRICE_FIELD.getFieldId(),
                SellFormCfg.BUY_NOW_PRICE_FIELD.getFieldType(), buyNowPriceEditText.getText().toString());

        AuctionAttributeField buyerPaid = AuctionAttributeFieldFactory.getInstance(SellFormCfg.BUYER_PAID_FIELD.getFieldId(),
                SellFormCfg.BUYER_PAID_FIELD.getFieldType(), "1");
        AuctionAttributeField invoiceVat = AuctionAttributeFieldFactory.getInstance(SellFormCfg.VAT_INVOICE_FIELD.getFieldId(),
                SellFormCfg.VAT_INVOICE_FIELD.getFieldType(), "1");

        fields.addAll(Arrays.asList(quantity, quantityType, auctionType,auctionTime, buyNowPrice, buyerPaid, invoiceVat));
        return fields;
    }

}
