package com.gacek.krzysztof.allegroapp.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gacek.krzysztof.allegroapp.CategoryActivity;
import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.model.Item;
import com.gacek.krzysztof.allegroapp.model.Photo;
import com.gacek.krzysztof.allegroapp.util.MessagesType;
import com.gacek.krzysztof.allegroapp.util.QtyType;
import com.gacek.krzysztof.allegroapp.util.Utils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;


public class ItemFormFragment extends Fragment {

    private static String DEBUG_TAG = "ItemFormFragment";

    public static final int CATEGORY_REQUEST_CODE = 10;
    public static final int PHOTO1_REQUEST_CODE = 21;
    public static final int PHOTO2_REQUEST_CODE = 22;
    public static final int PHOTO3_REQUEST_CODE = 23;
    public static final int SPEECH_INPUT_NAME_REQUEST_CODE = 31;
    public static final int SPEECH_INPUT_DESCRIPTION_REQUEST_CODE = 32;

    private TextView categoryPathTextView;
    private EditText titleEditText;
    private TextInputLayout titleInputLayout;
    private EditText descriptionEditText;
    private TextInputLayout descriptionInputLayout;
    private EditText quantityEditText;
    private TextInputLayout quantityInputLayout;
    private Spinner quantityTypeSpinner;

    private ImageView addCategoryBtn;
    private ImageView addTitleBtn;
    private ImageView addDescriptionBtn;
    private ImageView photo1ImageView;
    private ImageView photo2ImageView;
    private ImageView photo3ImageView;

    private Bitmap[] photos;

    private Subscription photo1Sub;
    private Subscription photo2Sub;
    private Subscription photo3Sub;
    private Subscription addTitleBtnSub;
    private Subscription addDescriptionBtnSub;
    private Subscription addCategoryBtnSub;
    private Subject<Integer> categorySelected = PublishSubject.create();

    private String categoryPath;
    private int categoryId;

    private Item initItem;
    private List<Photo> initPhotos;

    public ItemFormFragment() {

    }

    public static ItemFormFragment newInstance(@Nullable Item item, List<Photo> photos) {
        ItemFormFragment fragment = new ItemFormFragment();
        fragment.setItem(item);
        fragment.setPhotos(photos);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_form, container, false);

        photos = new Bitmap[]{null, null, null};

        addCategoryBtn = (ImageView) view.findViewById(R.id.itemForm_addCategoryBtn);
        addCategoryBtnSub = RxView.clicks(addCategoryBtn)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> goToCategoryActivity());
        addTitleBtn = (ImageView) view.findViewById(R.id.itemForm_speechInputName);
        addTitleBtnSub = RxView.clicks(addTitleBtn)
                .subscribe(aVoid -> promptSpeechInput(SPEECH_INPUT_NAME_REQUEST_CODE));
        addDescriptionBtn = (ImageView) view.findViewById(R.id.itemForm_speechInputDescription);
        addDescriptionBtnSub = RxView.clicks(addDescriptionBtn)
                .subscribe(aVoid -> promptSpeechInput(SPEECH_INPUT_DESCRIPTION_REQUEST_CODE));

        photo1ImageView = (ImageView) view.findViewById(R.id.itemForm_photo1);
        photo1Sub = RxView.clicks(photo1ImageView)
                .subscribe(e -> takePhoto(PHOTO1_REQUEST_CODE));
        photo2ImageView = (ImageView) view.findViewById(R.id.itemForm_photo2);
        photo2Sub = RxView.clicks(photo2ImageView)
                .subscribe(e -> takePhoto(PHOTO2_REQUEST_CODE));
        photo3ImageView = (ImageView) view.findViewById(R.id.itemForm_photo3);
        photo3Sub = RxView.clicks(photo3ImageView)
                .subscribe(e -> takePhoto(PHOTO3_REQUEST_CODE));

        categoryPathTextView = (TextView) view.findViewById(R.id.itemForm_categoryPath);
        quantityEditText = (EditText) view.findViewById(R.id.itemForm_quantity);
        quantityInputLayout = (TextInputLayout) view.findViewById(R.id.itemForm_quantityLayout);
        descriptionEditText = (EditText) view.findViewById(R.id.itemForm_description);
        descriptionInputLayout = (TextInputLayout) view.findViewById(R.id.itemForm_descriptionLayout);
        titleEditText = (EditText) view.findViewById(R.id.itemForm_title);
        titleInputLayout = (TextInputLayout) view.findViewById(R.id.itemForm_titleLayout);
        quantityTypeSpinner = (Spinner) view.findViewById(R.id.itemForm_quantityType);
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, QtyType.getTypeNamesList());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityTypeSpinner.setAdapter(spinnerArrayAdapter);

        setUpInitItems();
        return view;
    }

    public void setItem(Item item) {
        this.initItem = item;
    }

    public void setPhotos(List<Photo> photos) {
        this.initPhotos = photos;
    }

    public void setUpInitItems() {
        if (initItem != null) {
            titleEditText.setText(initItem.getName());
            descriptionEditText.setText(initItem.getDescription());
            categoryId = initItem.getCategoryId();
            categoryPath = initItem.getCategoryPath();
            categoryPathTextView.setText(categoryPath);
            quantityEditText.setText(String.valueOf(initItem.getQuantity()));
            int position = ((ArrayAdapter<String>) quantityTypeSpinner.getAdapter())
                    .getPosition(initItem.getQuantityType());
            quantityTypeSpinner.setSelection(position);
        }

        if (initPhotos != null && !initPhotos.isEmpty()) {
            for (int i = 0; initPhotos.size() > i; i++) {
                photos[i] = Utils.convertBlobToBitmap(initPhotos.get(i).getPhotoData());
            }
            if (photos[0] != null) {
                photo1ImageView.setImageBitmap(photos[0]);
            }
            if (photos[1] != null) {
                photo2ImageView.setImageBitmap(photos[1]);
            }
            if (photos[2] != null) {
                photo3ImageView.setImageBitmap(photos[2]);
            }
        }

    }

    private void goToCategoryActivity() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        startActivityForResult(intent, CATEGORY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CATEGORY_REQUEST_CODE) {
            categoryPath = data.getExtras().getString(CategoryActivity.CATEGORY_PATH_TAG);
            categoryId = data.getExtras().getInt(CategoryActivity.CATEGORY_ID_TAG);
            categoryPathTextView.setText(categoryPath);
            categorySelected.onNext(categoryId);
            Log.d(DEBUG_TAG, "CATEGORY SELECTED  " + categoryId);
        } else if (resultCode == RESULT_OK && requestCode > 20 && requestCode < 30) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            switch (requestCode) {
                case PHOTO1_REQUEST_CODE:
                    photo1ImageView.setImageBitmap(imageBitmap);
                    photos[0] = imageBitmap;
                    break;
                case PHOTO2_REQUEST_CODE:
                    photo2ImageView.setImageBitmap(imageBitmap);
                    photos[1] = imageBitmap;
                    break;
                case PHOTO3_REQUEST_CODE:
                    photo3ImageView.setImageBitmap(imageBitmap);
                    photos[2] = imageBitmap;
                    break;
            }
            Log.d(DEBUG_TAG, "PHOTO RECEIVED");
        } else if (resultCode == RESULT_OK && requestCode > 30 && requestCode < 40 && data != null) {
            List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            switch (requestCode) {
                case SPEECH_INPUT_NAME_REQUEST_CODE:
                    titleEditText.setText(result.get(0));
                    break;
                case SPEECH_INPUT_DESCRIPTION_REQUEST_CODE:
                    descriptionEditText.setText(result.get(0));
                    break;
            }
        }
    }

    public boolean isCameraAvailable() {
        return getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void takePhoto(int requestCode) {
        if (isCameraAvailable()) {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePhotoIntent.resolveActivity(getActivity()
                    .getPackageManager()) != null) {
                startActivityForResult(takePhotoIntent, requestCode);
            }
        }
    }

    private void promptSpeechInput(int requestCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Wprowadz dane golosowo");

        try {
            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
         Utils.showSnackBarNotification(getActivity().findViewById(R.id.addItem_mainLayout),
                 "Wprowadzanie glosowe nie obslugiwane", MessagesType.WARNING);
        }
    }

    public boolean isFormValid() {
        boolean isValid = true;
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String quantity = quantityEditText.getText().toString();

        if (title.isEmpty()) {
            isValid = false;
            Utils.showErrorMessage(titleInputLayout, "Nazwa przedmiotu jest wymagana");
        } else {
            Utils.hideErrorMessage(titleInputLayout);
        }

        if (description.isEmpty()) {
            isValid = false;
            Utils.showErrorMessage(descriptionInputLayout, "Opis jest wymagany");
        } else {
            Utils.hideErrorMessage(descriptionInputLayout);
        }

        if (quantity.isEmpty()) {
            isValid = false;
            Utils.showErrorMessage(quantityInputLayout, "Ilosc jest wymagana");
        } else {
            try {
                Integer.parseInt(quantityEditText.getText().toString());
                Utils.hideErrorMessage(quantityInputLayout);
            } catch (NumberFormatException e) {
                isValid = false;
                Utils.showErrorMessage(quantityInputLayout, "Wpisana wartosc nie jest liczba");
            }
        }

        if (categoryId == 0) {
            isValid = false;
            Utils.showSnackBarNotification(getActivity().findViewById(R.id.addItem_mainLayout),
                    "Kategoria nie zostala wybrana!", MessagesType.ERROR);
        }
        return isValid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Item getItem() {
        Item item = new Item();
        item.setName(titleEditText.getText().toString());
        item.setCategoryId(categoryId);
        item.setCategoryPath(categoryPath);
        item.setDescription(descriptionEditText.getText().toString());
        item.setQuantity(Integer.parseInt(quantityEditText.getText().toString()));
        String quantityType = quantityTypeSpinner.getSelectedItem().toString();
        item.setQuantityType(quantityType);
        item.setQuantityTypeId(QtyType.getIdFromName(quantityType));
        item.setCreationDate(new Date().toString());
        return item;
    }

    public List<Photo> getPhotos() {
        List<Photo> photosList = new ArrayList<>();
        if (photos[0] != null) {
            photosList.add(createPhoto(photos[0], "Y"));
        }
        if (photos[1] != null) {
            photosList.add(createPhoto(photos[0], "N"));
        }
        if (photos[2] != null) {
            photosList.add(createPhoto(photos[0], "N"));
        }
        return photosList;
    }

    public Photo createPhoto(Bitmap bitmap, String primaryFlag) {
        Photo photo = new Photo();
        photo.setPhotoData(Utils.convertBitMapToBlob(bitmap));
        photo.setPrimaryImage(primaryFlag);
        return photo;
    }

    public Subject<Integer> getCategorySelected() {
        return categorySelected;
    }
}
