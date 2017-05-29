package com.gacek.krzysztof.allegroapp;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.gacek.krzysztof.allegroapp.data.PreferencesManager;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsAttributeRepository;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsRepository;
import com.gacek.krzysztof.allegroapp.data.repo.PhotosRepository;
import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryRequestEnvelope;
import com.gacek.krzysztof.allegroapp.dto.DoGetSellFormFieldsForCategoryResponseEnvelope;
import com.gacek.krzysztof.allegroapp.dto.SellFormField;
import com.gacek.krzysztof.allegroapp.fragment.CategoryFormFragment;
import com.gacek.krzysztof.allegroapp.fragment.ItemFormFragment;
import com.gacek.krzysztof.allegroapp.fragment.MyItemsFragment;
import com.gacek.krzysztof.allegroapp.model.Item;
import com.gacek.krzysztof.allegroapp.model.ItemAttribute;
import com.gacek.krzysztof.allegroapp.model.Photo;
import com.gacek.krzysztof.allegroapp.service.AllegroSellFormFieldsService;
import com.gacek.krzysztof.allegroapp.service.AllegroServiceFactory;
import com.gacek.krzysztof.allegroapp.util.MessagesType;
import com.gacek.krzysztof.allegroapp.util.Utils;
import com.jakewharton.rxbinding.view.RxView;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;



public class AddItemActivity extends AppCompatActivity {


    private final static String TAG = "ADD_ITEM_ACTIVITY";

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private View saveItemBtn;
    private Switch categoryAttrSwitch;

    private Subscription saveItemBtnSub;
    private Subscription categotyAttrSwitchSub;

    private FragmentManager fragmentManager;
    private ItemFormFragment itemFormFragment;
    private CategoryFormFragment categoryFormFragment;

    private ItemsRepository itemsRepository;
    private PhotosRepository photosRepository;
    private ItemsAttributeRepository itemsAttributeRepository;
    private PreferencesManager preferencesManager;

    private int reqType;
    private int itemIdToUpdate;
    private Item itemToUpdate;
    private List<Photo> photosToUpdate;
    private List<ItemAttribute> itemAttributesToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        fragmentManager = getSupportFragmentManager();
        ItemsRepository.initInstance(getContentResolver());
        itemsRepository = ItemsRepository.getInstance();
        PhotosRepository.initInstance(getContentResolver());
        photosRepository = PhotosRepository.getInstance();
        ItemsAttributeRepository.initInstance(getContentResolver());
        itemsAttributeRepository = ItemsAttributeRepository.getInstance();
        PreferencesManager.initializeInstance(getApplicationContext());
        preferencesManager = PreferencesManager.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.reqType = extras.getInt(MyItemsFragment.REQ_TYPE);
            if(reqType == MyItemsFragment.REQ_EDIT_ITEM) {
                Log.d(TAG, "Update item action");
                itemIdToUpdate = extras.getInt(MyItemsFragment.PASSING_ID);
                Log.d(TAG, String.valueOf(itemIdToUpdate));
                itemToUpdate = itemsRepository.findOne(itemIdToUpdate);
                Log.d(TAG, itemToUpdate.toString());
                photosToUpdate = photosRepository.findByItemId(itemIdToUpdate);
                itemAttributesToUpdate = itemsAttributeRepository.findByItemId(itemIdToUpdate);
                Log.d(TAG, itemAttributesToUpdate.toString());
            }
        }

        setUpToolbar();
        setUpItemFragment();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.addItem_mainLayout);
        progressBar = (ProgressBar) findViewById(R.id.category_fragment_progressBar);
        categoryAttrSwitch = (Switch) findViewById(R.id.switch1);
        categotyAttrSwitchSub = RxView.clicks(categoryAttrSwitch)
                .subscribe(e -> {
                    if ( categoryAttrSwitch.isChecked()) {
                        Log.d(TAG, "Show category form");
                        showCategoryForm();
                    } else {
                        Log.d(TAG, "Hide category form");
                        hideCategoryForm();
                    }
                });

        saveItemBtn = findViewById(R.id.addItemMenu_save);
        saveItemBtnSub = RxView.clicks(saveItemBtn)
                .subscribe(e -> {
                    if (itemFormFragment.isFormValid()) {
                        saveItemBtn.setEnabled(false);
                        saveForm();
                        finishSuccesfullyActivity();
                    }
                });
    }

    public void saveForm() {
        Item  item = itemFormFragment.getItem();

        if (reqType == MyItemsFragment.REQ_ADD_ITEM) {
            item = itemsRepository.save(item);
        } else {
            item.setId(itemIdToUpdate);
            int status = itemsRepository.update(item);
        }

        List<Photo> photos = itemFormFragment.getPhotos();

        if (reqType == MyItemsFragment.REQ_ADD_ITEM) {
            for (Photo photo: photos) {
                photo.setItemId(item.getId());
                photo = photosRepository.save(photo);
            }
        } else {
            int photosToUpdateSize = photosToUpdate.size();
            int photosSize = photos.size();
            if (photosToUpdateSize == photosSize) {
                for (int i = 0; i < photos.size(); i++) {
                    photosToUpdate.get(i).setPhotoData(photos.get(i).getPhotoData());
                    photosRepository.update(photosToUpdate.get(i));
                }
            } else if (photosSize > photosToUpdateSize) {
                for (int i = 0; i < photosSize; i++) {
                    if(photosToUpdateSize > i) {
                        photosToUpdate.get(i).setPhotoData(photos.get(i).getPhotoData());
                        photosRepository.update(photosToUpdate.get(i));
                    } else {
                        photos.get(i).setItemId(itemIdToUpdate);
                        photosRepository.save(photos.get(i));
                    }
                }
            }
        }

        if (categoryAttrSwitch.isChecked()) {
            List<ItemAttribute> itemAttributeList =
                    categoryFormFragment.getSelectedItemsAttributes();
            if (reqType == MyItemsFragment.REQ_ADD_ITEM || itemAttributesToUpdate.isEmpty()) {
                for (ItemAttribute attribute:  itemAttributeList) {
                    attribute.setItemId(item.getId());
                    attribute = itemsAttributeRepository.save(attribute);
                }
            } else {
                if (item.getCategoryId() == itemToUpdate.getCategoryId()) {
                    for (ItemAttribute itemAttributeToUpdate : itemAttributesToUpdate) {
                        ItemAttribute itemAttribute = getItemAttributeByAttrId(itemAttributeList,
                                itemAttributeToUpdate.getAttributeId());
                        if (itemAttribute != null) {
                            itemAttributeToUpdate.setAttributeValue(itemAttribute.getAttributeValue());
                            itemAttributeToUpdate.setAttributeValueStr(itemAttribute.getAttributeValueStr());
                            itemsAttributeRepository.update(itemAttributeToUpdate);
                        }
                    }
                } else {
                    itemsAttributeRepository.removeByItemId(itemIdToUpdate);
                    for (ItemAttribute attribute:  itemAttributeList) {
                        attribute.setItemId(itemIdToUpdate);
                        attribute = itemsAttributeRepository.save(attribute);
                    }
                }
            }
        }
    }

    @Nullable
    private ItemAttribute getItemAttributeByAttrId(List<ItemAttribute> itemAttributes, int attrId) {
        for (ItemAttribute itemAttribute : itemAttributes) {
            if (itemAttribute.getAttributeId() == attrId) {
                return itemAttribute;
            }
        }
        return null;
    }

    public void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.addItem_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Dodaj Przedmiot");
        if(reqType == MyItemsFragment.REQ_EDIT_ITEM) {
            toolbar.setTitle("Edytuj Przedmiot");
        } else {
            toolbar.setTitle("Dodaj Przedmiot");
        }
        toolbar.inflateMenu(R.menu.add_item_menu);
        toolbar.setNavigationOnClickListener( l -> cancelActivity());
    }

    public void setUpItemFragment() {
        itemFormFragment = ItemFormFragment.newInstance(itemToUpdate, photosToUpdate);
        itemFormFragment.getCategorySelected()
                .subscribe(catId -> {
                    if(categoryAttrSwitch.isChecked()) {
                        showCategoryForm();
                    }
                });
        if (fragmentManager.findFragmentById(R.id.item_fragment) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.item_fragment, itemFormFragment)
                    .commit();
        }
    }

    private void hideCategoryForm() {
        if(fragmentManager.findFragmentById(R.id.category_fragment) != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(categoryFormFragment)
                    .commit();
        }
    }

    private void showCategoryForm() {
        int categoryId = itemFormFragment.getCategoryId();

        if (categoryId > 0) {

            if (categoryFormFragment != null) {
                Log.d(TAG, "Remove Fragment");
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(categoryFormFragment)
                        .commit();
            }

            DoGetSellFormFieldsForCategoryRequestEnvelope request
                    = new DoGetSellFormFieldsForCategoryRequestEnvelope();
            request.setCountryId(1);
            request.setCategoryId(categoryId);
            request.setWebapiKey(preferencesManager.getStringValue(PreferencesManager.WEB_API_KEY));

            AllegroSellFormFieldsService service =
                    AllegroServiceFactory.createService(AllegroSellFormFieldsService.class, getApplicationContext());

            progressBar.setEnabled(true);

            service.requestCall(request)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(formData -> {
                        progressBar.setEnabled(false);
                        createCategoryFormFragment(formData.getSellFormsFields());
                    }, error -> {
                        progressBar.setEnabled(false);
                        Utils.handleError(error, coordinatorLayout);
                        categoryAttrSwitch.setChecked(false);
                    });
        } else {
            Utils.showSnackBarNotification(findViewById(R.id.addItem_mainLayout),
                    "Nie wybrano kategorii!", MessagesType.WARNING);
            categoryAttrSwitch.setChecked(false);
        }
    }

    private void createCategoryFormFragment(List<SellFormField> sellFormFields) {
        categoryFormFragment =
                CategoryFormFragment.newInstance(sellFormFields, itemAttributesToUpdate);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.category_fragment, categoryFormFragment)
                .commit();
    }

    public void finishSuccesfullyActivity() {
        setResult(RESULT_OK);
//        if(reqType == MyItemsFragment.REQ_EDIT_ITEM) {
//            finishActivity(MyItemsFragment.REQ_EDIT_ITEM);
//        } else {
//            finishActivity(MyItemsFragment.REQ_ADD_ITEM);
//        }
        finish();
    }

    public void cancelActivity() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
