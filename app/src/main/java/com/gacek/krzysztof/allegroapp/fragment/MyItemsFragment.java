package com.gacek.krzysztof.allegroapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gacek.krzysztof.allegroapp.AddAuctionActivity;
import com.gacek.krzysztof.allegroapp.AddItemActivity;
import com.gacek.krzysztof.allegroapp.R;
import com.gacek.krzysztof.allegroapp.adapter.ItemAdapter;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsAttributeRepository;
import com.gacek.krzysztof.allegroapp.data.repo.ItemsRepository;
import com.gacek.krzysztof.allegroapp.data.repo.PhotosRepository;
import com.gacek.krzysztof.allegroapp.model.ItemThumbnail;
import com.gacek.krzysztof.allegroapp.util.MessagesType;
import com.gacek.krzysztof.allegroapp.util.Utils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;


public class MyItemsFragment extends Fragment {

    public static final String TAG = "MY_ITEMS_FRAGMENT";
    public static final int REQ_ADD_ITEM = 100;
    public static final int REQ_EDIT_ITEM = 101;
    public static final int REQ_ADD_AUCTION = 102;
    public static final String REQ_TYPE = "REQ_TYPE";
    public static final String PASSING_ID = "ID";

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton addItemBtn;
    private View addAuctionBtn;
    private View removeItemBtn;
    private View editItemBtn;
    private Subscription addItemBtnSub;
    private Subscription addAuctionBtnSub;
    private Subscription removeItemBtnSub;
    private Subscription editItemBtnSub;

    private Toolbar toolbar;
    private List<ItemThumbnail> itemList;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    private ItemsRepository itemsRepository;
    private PhotosRepository photosRepository;
    private ItemsAttributeRepository itemsAttributeRepository;
    private Integer selectedItemId;

    public MyItemsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsRepository.initInstance(getActivity().getContentResolver());
        itemsRepository = ItemsRepository.getInstance();
        PhotosRepository.initInstance(getActivity().getContentResolver());
        photosRepository = PhotosRepository.getInstance();
        ItemsAttributeRepository.initInstance(getActivity().getContentResolver());
        itemsAttributeRepository = ItemsAttributeRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_items, container, false);
        addItemBtn = (FloatingActionButton) view.findViewById(R.id.myItems_addItemBtn);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.myItems_mainLayout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.my_items_menu);
        toolbar.getMenu().setGroupVisible(R.id.myItemsMenuGroup, false);

        itemList = itemsRepository.getItemThumbials();
        itemAdapter = new ItemAdapter(itemList);
        recyclerView = (RecyclerView) view.findViewById(R.id.myItems_myItemsList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);

        addItemBtnSub = RxView.clicks(addItemBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(e -> addItem());
        itemAdapter.getItemClickSubject()
                .subscribe(data -> editItem(data));
        itemAdapter.getItemLongClickSubject()
                .subscribe(data -> {
                    selectedItemId = data;
                    setToolbarItems();
                });

        return view;
    }

    private void setUpToolbarIcons() {
        addAuctionBtn = getActivity().findViewById(R.id.myItemsMenu_auction);
        removeItemBtn = getActivity().findViewById(R.id.myItemsMenu_remove);
        editItemBtn = getActivity().findViewById(R.id.myItemsMenu_edit);

        addAuctionBtnSub = RxView.clicks(addAuctionBtn)
                .subscribe(aVoid -> addAuction());
        editItemBtnSub = RxView.clicks(editItemBtn)
                .subscribe(aVoid -> editItem(selectedItemId));
        removeItemBtnSub = RxView.clicks(removeItemBtn)
                .subscribe(aVoid -> removeItem());
    }

    private void tearDownToolbarIcons() {
        addAuctionBtnSub.unsubscribe();
    }

    private void setToolbarItems() {
        if (selectedItemId > 0) {
            toolbar.getMenu().setGroupVisible(R.id.myItemsMenuGroup, true);
            setUpToolbarIcons();
        } else {
            toolbar.getMenu().setGroupVisible(R.id.myItemsMenuGroup, false);
            tearDownToolbarIcons();
        }
    }

    private void editItem(int itemId) {
        Intent intent = new Intent(getContext(), AddItemActivity.class);
        intent.putExtra(REQ_TYPE, REQ_EDIT_ITEM);
        intent.putExtra(PASSING_ID, itemId);
        startActivityForResult(intent, REQ_ADD_ITEM);
    }

    private void addItem() {
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        intent.putExtra(REQ_TYPE, REQ_ADD_ITEM);
        startActivityForResult(intent, REQ_ADD_ITEM);
    }

    private void removeItem() {
        itemsRepository.remove(selectedItemId);
        photosRepository.removeByItemId(selectedItemId);
        itemsAttributeRepository.removeByItemId(selectedItemId);
        int position = removeItemFromItemList(selectedItemId);
        this.itemAdapter.notifyItemRemoved(position);
    }

    private void addAuction() {
        Intent intent = new Intent(getActivity(), AddAuctionActivity.class);
        intent.putExtra(REQ_TYPE, REQ_ADD_AUCTION);
        intent.putExtra(PASSING_ID, selectedItemId);
        startActivityForResult(intent, REQ_ADD_AUCTION);
    }

    private int removeItemFromItemList(int itemId) {
        int position = -1;
        for(int i = 0; i < itemList.size(); i++) {
            if (itemId == itemList.get(i).getItemId()) {
                return i;
            }
        }
        if (position >= 0) {
            itemList.remove(position);
        }
        return position;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final int okResult = getActivity().RESULT_OK;
        final int cancelResult = getActivity().RESULT_CANCELED;

        if (requestCode == REQ_ADD_ITEM && resultCode == okResult) {
            Utils.showSnackBarNotification(coordinatorLayout, "Dodano nowy przedmiot",
                    MessagesType.SUCCESS);
            itemAdapter.setItemList(itemsRepository.getItemThumbials());
        } else if (requestCode == REQ_EDIT_ITEM && resultCode == okResult) {
            Utils.showSnackBarNotification(coordinatorLayout, "Zapisano zmiany", MessagesType.SUCCESS);
            itemAdapter.setItemList(itemsRepository.getItemThumbials());
        } else if (resultCode == okResult && requestCode == REQ_ADD_AUCTION) {
            String auctionId = data.getDataString();
            Utils.showSnackBarNotification(coordinatorLayout, "Dodano aukcje o id " + auctionId, MessagesType.SUCCESS);
        } else  if (resultCode == cancelResult) {
            Utils.showSnackBarNotification(coordinatorLayout, "Anulowano operacje", MessagesType.WARNING);
        }
    }

    @Override
    public void onDestroyView() {
        addItemBtnSub.unsubscribe();
        toolbar.getMenu().clear();
        super.onDestroyView();
    }

}
