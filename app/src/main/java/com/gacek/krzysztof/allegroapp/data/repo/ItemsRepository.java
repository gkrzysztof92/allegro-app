package com.gacek.krzysztof.allegroapp.data.repo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.gacek.krzysztof.allegroapp.data.DataContract;
import com.gacek.krzysztof.allegroapp.data.DataContract.PhotosEntry;
import com.gacek.krzysztof.allegroapp.model.Item;
import com.gacek.krzysztof.allegroapp.model.ItemThumbnail;
import com.gacek.krzysztof.allegroapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.gacek.krzysztof.allegroapp.data.DataContract.ItemsEntry.*;


public class ItemsRepository {

    private static ItemsRepository sItemsRepository;
    private ContentResolver contentResolver;

    private ItemsRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public static synchronized void initInstance(ContentResolver contentResolver) {
        if (sItemsRepository == null) {
            sItemsRepository = new ItemsRepository(contentResolver);
        }
    }

    public static synchronized ItemsRepository getInstance() {
        if (sItemsRepository == null) {
            throw new IllegalStateException("CategoriesRepository is not initialized");
        }
        return sItemsRepository;
    }

    public Item findOne(int id) {
        String selection = _ID + " = ?";
        String[] selectionArg = { String.valueOf(id) };
        Cursor c = contentResolver.query(CONTENT_URI, PROJECTION, selection, selectionArg, null);
        List<Item> items = mapCursorToItems(c);
        if (items.isEmpty() || items.size() > 1) {
            return null;
        } else {
            return items.get(0);
        }
    }

    public List<Item> findAll() {
        Cursor c = contentResolver.query(CONTENT_URI, PROJECTION, null, null, null);
        return mapCursorToItems(c);
    }

    public List<ItemThumbnail> getItemThumbials() {
        String[] projection = {
                Utils.concat(TABLE_NAME, _ID, "."),
                Utils.concat(TABLE_NAME, COLUMN_NAME, "." ),
                Utils.concat(PhotosEntry.TABLE_NAME, PhotosEntry.COLUMN_PHOTO_DATA, "." )
        };
        String selection = "ifnull(" + Utils.concat(PhotosEntry.TABLE_NAME, PhotosEntry.COLUMN_PRIMARY_IMAGE, ".") +", 'Y') = ? ";
        String[] selectionArg = { "Y" };
        Cursor c = contentResolver.query(DataContract.ThumbView.CONTENT_URI, projection, selection, selectionArg, null, null);
        List<ItemThumbnail> list = new ArrayList<>();
        while (c.moveToNext()) {
            ItemThumbnail itemThumbnail= new ItemThumbnail();
            itemThumbnail.setItemId(c.getInt(c.getColumnIndex(_ID)));
            itemThumbnail.setItemName(c.getString(c.getColumnIndex(COLUMN_NAME)));
            itemThumbnail.setPhoto(c.getBlob(c.getColumnIndex(PhotosEntry.COLUMN_PHOTO_DATA)));
            list.add(itemThumbnail);
        }
        return list;
    }

    public Item save(Item item) {
        Uri uri = contentResolver.insert(CONTENT_URI, mapItemToContentValues(item));
        item.setId((int) ContentUris.parseId(uri));
        return item;
    }

    public int update(Item item) {
        String selection = _ID + " = ?" ;
        String[] selectionArgs = { String.valueOf(item.getId()) };
        int updateStatus = contentResolver.update(CONTENT_URI, mapItemToContentValues(item),
                selection, selectionArgs);
        return updateStatus;
    }

    public int remove(int itemId) {
        String selection = _ID + " = ?";
        String[] selectionArgs = { String.valueOf(itemId) };
        int result = contentResolver.delete(CONTENT_URI, selection, selectionArgs);
        return result;
    }

    private ContentValues mapItemToContentValues(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, item.getName());
        contentValues.put(COLUMN_CATEGORY_ID, item.getCategoryId());
        contentValues.put(COLUMN_CATEGORY_PATH, item.getCategoryPath());
        contentValues.put(COLUMN_DESCRIPTION, item.getDescription());
        contentValues.put(COLUMN_QUANTITY, item.getQuantity());
        contentValues.put(COLUMN_QUANTITY_TYPE, item.getQuantityType());
        contentValues.put(COLUMN_QUANTITY_TYPE_ID, item.getQuantityTypeId());
        contentValues.put(COLUMN_CREATION_DATE, item.getCreationDate());
        return contentValues;
    }

    private List<Item> mapCursorToItems(Cursor c) {
        List<Item> itemList = new ArrayList<>();
        while (c.moveToNext()) {
            itemList.add(new Item(
                    c.getInt(c.getColumnIndex(_ID)),
                    c.getString(c.getColumnIndex(COLUMN_NAME)),
                    c.getInt(c.getColumnIndex(COLUMN_CATEGORY_ID)),
                    c.getString(c.getColumnIndex(COLUMN_CATEGORY_PATH)),
                    c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)),
                    c.getInt(c.getColumnIndex(COLUMN_QUANTITY)),
                    c.getString(c.getColumnIndex(COLUMN_QUANTITY_TYPE)),
                    c.getInt(c.getColumnIndex(COLUMN_QUANTITY_TYPE_ID)),
                    c.getString(c.getColumnIndex(COLUMN_CREATION_DATE))
            ));
        }
        return itemList;
    }

}
